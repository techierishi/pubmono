#!/usr/bin/python
import asyncio
from nats.aio.client import Client as NATS
import sqlite3
from flask import Flask, request, jsonify
from flask_cors import CORS
import logging
import sys
import json
from multiprocessing import Process


logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)


def connect_to_db():
    conn = sqlite3.connect('database.db')
    return conn

def create_db_table():
    try:
        conn = connect_to_db()
        conn.execute('''DROP TABLE IF EXISTS tags''')
        conn.execute('''DROP TABLE IF EXISTS tag_content''')
        conn.execute('''
            CREATE TABLE tags (
                id INTEGER PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                description TEXT NOT NULL
            );
        ''')
        conn.execute('''
            CREATE TABLE tag_content (
                id INTEGER PRIMARY KEY NOT NULL,
                tag_id INTEGER NOT NULL,
                content_id INTEGER NOT NULL,
                content_type TEXT NOT NULL
            );
        ''')

        conn.commit()
        logging.info("Tag table created successfully")
    except Exception as e:
        logging.info("Tag table creation failed - Maybe table"+ str(e))
    finally:
        conn.close()

def insert_tag(tag):
    inserted_tag = {}
    try:
        conn = connect_to_db()
        cur = conn.cursor()
        cur.execute("INSERT INTO tags (name, description) VALUES (?, ?)", (tag['name'], tag['description']) )
        conn.commit()
        inserted_tag = get_tag_by_id(cur.lastrowid)
    except:
        conn().rollback()

    finally:
        conn.close()

    return inserted_tag

def get_tags():
    tags = []
    try:
        conn = connect_to_db()
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()
        cur.execute("SELECT * FROM tags")
        rows = cur.fetchall()

        for i in rows:
            tag = {}
            tag["id"] = i["id"]
            tag["name"] = i["name"]
            tag["description"] = i["description"]
            tags.append(tag)

    except:
        tags = []

    return tags

def get_tag_by_id(id):
    tag = {}
    try:
        conn = connect_to_db()
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()
        cur.execute("SELECT * FROM tags WHERE id = ?", (id,))
        row = cur.fetchone()

        # convert row object to dictionary
        tag["id"] = row["id"]
        tag["name"] = row["name"]
        tag["description"] = row["description"]
    except:
        tag = {}

    return tag

def update_tag(tag):
    updated_tag = {}
    try:
        conn = connect_to_db()
        cur = conn.cursor()
        cur.execute("UPDATE tags SET name = ?, description = ? WHERE id =?", (tag["name"], tag["description"],))
        conn.commit()
        #return the tag
        updated_tag = get_tag_by_id(tag["id"])

    except:
        conn.rollback()
        updated_tag = {}
    finally:
        conn.close()

    return updated_tag

def delete_tag(id):
    message = {}
    try:
        conn = connect_to_db()
        conn.execute("DELETE from tags WHERE id = ?", (id,))
        conn.commit()
        message["status"] = "tag deleted successfully"
    except:
        conn.rollback()
        message["status"] = "Cannot delete tag"
    finally:
        conn.close()

    return message

def attach_content(tag_content):
    message = {}
    try:
        conn = connect_to_db()
        cur = conn.cursor()
        cur.execute("INSERT INTO tag_content (tag_id, content_id, content_type) VALUES (?, ?, ?)", (tag_content['tag_id'], tag_content['content_id'], tag_content['content_type'],) )
        conn.commit()
        message["status"] = "Tag attached successfully"
    except:
        conn().rollback()
        message["status"] = "Error while attaching tag"

    finally:
        conn.close()
    return {}

def detach_content(tag_content):
    message = {}
    try:
        conn = connect_to_db()
        conn.execute("DELETE from tag_content WHERE tag_id = ? AND content_id = ? AND content_type = ?", (tag_content['tag_id'], tag_content['content_id'], tag_content['content_type'],))
        conn.commit()
        message["status"] = "Tag detached successfully"
    except:
        conn.rollback()
        message["status"] = "Error while detaching tag"
    finally:
        conn.close()

    return message

def init_db():
    tags = []
    create_db_table()
    logging.info('DB Created...')
    tags.append({
        "name": "issue",
        "description": "Issue tag"
    })
    tags.append({
        "name": "bug",
        "description": "Bug tag"
    })
    for i in tags:
        logging.info(insert_tag(i))

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})

@app.route('/api/tags', methods=['GET'])
def api_get_tags():
    return jsonify(get_tags())

@app.route('/api/tags/<id>', methods=['GET'])
def api_get_tag(id):
    return jsonify(get_tag_by_id(id))

@app.route('/api/tags/add',  methods = ['POST'])
def api_add_tag():
    tag = request.get_json()
    return jsonify(insert_tag(tag))

@app.route('/api/tags/update',  methods = ['PUT'])
def api_update_tag():
    tag = request.get_json()
    return jsonify(update_tag(tag))

@app.route('/api/tags/delete/<id>',  methods = ['DELETE'])
def api_delete_tag(id):
    return jsonify(delete_tag(id))

@app.route('/api/tag_content/add',  methods = ['POST'])
def api_attach_content():
    tag_content = request.get_json()
    return jsonify(attach_content(tag_content))

@app.route('/api/tag_content/delete',  methods = ['DELETE'])
def api_detach_content():
    tag_content = request.get_json()
    return jsonify(detach_content(tag_content))

async def listen_mq_event():
    nc = NATS()

    async def disconnected_cb():
        print("Got disconnected...")

    async def reconnected_cb():
        print("Got reconnected...")

    await nc.connect("127.0.0.1",
                     reconnected_cb=reconnected_cb,
                     disconnected_cb=disconnected_cb,
                     max_reconnect_attempts=-1,)

    async def help_request(msg):
        subject = msg.subject
        reply = msg.reply
        data = msg.data.decode()
        print("Received a message on '{subject} {reply}': {data}".format(
            subject=subject, reply=reply, data=data))
        tag_post_map = json.loads(data)
        if tag_post_map['event'] == 'attach_tag':
            attach_content(tag_post_map)
        if tag_post_map['event'] == 'detach_tag':
            detach_content(tag_post_map)

    # Use queue named 'workers' for distributing requests
    # among subscribers.
    await nc.subscribe("tag_event", "workers", help_request)

def loop():
    loop = asyncio.get_event_loop()
    loop.run_until_complete(listen_mq_event())
    loop.run_forever()
    loop.close()

if __name__ == '__main__':
    #app.debug = True
    #app.run(debug=True)
    logging.info('Tag app started...')
    init_db()
    
    p = Process(target=loop, args=())
    p.start()
    app.run(host='0.0.0.0', port=5001)

    # threading.Thread(target=loop.run_forever).start()

    