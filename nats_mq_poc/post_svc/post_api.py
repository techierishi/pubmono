#!/usr/bin/python
import asyncio
from nats.aio.client import Client as NATS
import sqlite3
from flask import Flask, request, jsonify
from flask_cors import CORS
import logging
import sys
import json

logging.basicConfig(stream=sys.stdout, level=logging.DEBUG)


def connect_to_db():
    conn = sqlite3.connect('database.db')
    return conn

def create_db_table():
    try:
        conn = connect_to_db()
        conn.execute('''DROP TABLE IF EXISTS posts''')
        conn.execute('''DROP TABLE IF EXISTS post_tag''')
        conn.execute('''
            CREATE TABLE posts (
                id INTEGER PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                user TEXT,
                description TEXT NOT NULL
            );
        ''')
        conn.execute('''
            CREATE TABLE post_tag (
                id INTEGER PRIMARY KEY NOT NULL,
                tag_id INTEGER NOT NULL,
                post_id INTEGER NOT NULL
            );
        ''')

        conn.commit()
        logging.info("post table created successfully")
    except Exception as e:
        logging.info("post table creation failed - Maybe table"+ str(e))
    finally:
        conn.close()

def insert_post(post):
    logging.info('Adding post ' + json.dumps(post))
    inserted_post = {}
    try:
        conn = connect_to_db()
        cur = conn.cursor()
        cur.execute("INSERT INTO posts (title, description) VALUES (?, ?)", (post['title'], post['description']) )
        conn.commit()
        inserted_post = get_post_by_id(cur.lastrowid)
    except Exception as e:
        logging.error('insert_post error '+ str(e))
        conn().rollback()

    finally:
        conn.close()

    return inserted_post

def get_posts():
    posts = []
    try:
        conn = connect_to_db()
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()
        cur.execute("SELECT * FROM posts")
        rows = cur.fetchall()

        for i in rows:
            post = {}
            post["id"] = i["id"]
            post["title"] = i["title"]
            post["description"] = i["description"]
            posts.append(post)

    except:
        posts = []

    return posts

def get_post_by_id(id):
    post = {}
    try:
        conn = connect_to_db()
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()
        cur.execute("SELECT * FROM posts WHERE id = ?", (id,))
        row = cur.fetchone()

        # convert row object to dictionary
        post["id"] = row["id"]
        post["title"] = row["title"]
        post["description"] = row["description"]
    except:
        post = {}

    return post

def update_post(post):
    updated_post = {}
    try:
        conn = connect_to_db()
        cur = conn.cursor()
        cur.execute("UPDATE posts SET title = ?, description = ? WHERE id =?", (post["title"], post["description"],))
        conn.commit()
        #return the post
        updated_post = get_post_by_id(post["id"])

    except:
        conn.rollback()
        updated_post = {}
    finally:
        conn.close()

    return updated_post

def delete_post(id):
    message = {}
    try:
        conn = connect_to_db()
        conn.execute("DELETE from posts WHERE id = ?", (id,))
        conn.commit()
        message["status"] = "post deleted successfully"
    except:
        conn.rollback()
        message["status"] = "Cannot delete post"
    finally:
        conn.close()

    return message

async def send_mq_event(event_data):
    nc = NATS()
    async def disconnected_cb():
        print("Got disconnected...")

    async def reconnected_cb():
        print("Got reconnected...")

    await nc.connect("127.0.0.1",
                     reconnected_cb=reconnected_cb,
                     disconnected_cb=disconnected_cb,
                     max_reconnect_attempts=-1,)
    await nc.publish("tag_event", bytes(json.dumps(event_data), 'utf-8'))


async def attach_post_tag(post_tag):
    message = {}
    try:
        conn = connect_to_db()
        cur = conn.cursor()
        cur.execute("INSERT INTO post_tag (tag_id, post_id) VALUES (?, ?)", (post_tag['tag_id'], post_tag['post_id'],) )
        conn.commit()
        message["status"] = "post attached successfully"
        post_tag['content_id'] = post_tag['post_id']
        post_tag['content_type'] = 'post'
        post_tag['event'] = 'attach_tag'
        await send_mq_event(post_tag)
    except:
        conn().rollback()
        message["status"] = "Error while attaching post"

    finally:
        conn.close()
    return message

async def detach_post_tag(post_tag):
    message = {}
    try:
        conn = connect_to_db()
        conn.execute("DELETE from post_tag WHERE tag_id = ? AND post_id = ?", (post_tag['tag_id'], post_tag['post_id'],))
        conn.commit()
        message["status"] = "post detached successfully"
        post_tag['content_id'] = post_tag['post_id']
        post_tag['content_type'] = 'post'
        post_tag['event'] = 'detach_tag'
        await send_mq_event(post_tag)
    except:
        conn.rollback()
        message["status"] = "Error while detaching post"
    finally:
        conn.close()

    return message

def init_db():
    posts = []
    create_db_table()
    logging.info('DB Created...')
    posts.append({
        "title": "First post",
        "description": "This is first post"
    })
    posts.append({
        "title": "Second post",
        "description": "This is second post"
    })
    for i in posts:
        logging.info(insert_post(i))

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})

@app.route('/api/posts', methods=['GET'])
def api_get_posts():
    return jsonify(get_posts())

@app.route('/api/posts/<id>', methods=['GET'])
def api_get_post(id):
    return jsonify(get_post_by_id(id))

@app.route('/api/posts/add',  methods = ['POST'])
def api_add_post():
    post = request.get_json()
    logging.info('api/insert_post '+ str(post))
    return jsonify(insert_post(post))

@app.route('/api/posts/update',  methods = ['PUT'])
def api_update_post():
    post = request.get_json()
    return jsonify(update_post(post))

@app.route('/api/posts/delete/<id>',  methods = ['DELETE'])
def api_delete_post(id):
    return jsonify(delete_post(id))

@app.route('/api/post_tag/add',  methods = ['POST'])
async def api_attach_post():
    post_tag = request.get_json()
    return jsonify(await attach_post_tag(post_tag))

@app.route('/api/post_tag/delete',  methods = ['DELETE'])
async def api_detach_post():
    post_tag = request.get_json()
    return jsonify(await detach_post_tag(post_tag))

if __name__ == '__main__':
    app.debug = True
    logging.info('Post app started...')
    init_db()
    app.run(host='0.0.0.0', port=5002, debug=True)