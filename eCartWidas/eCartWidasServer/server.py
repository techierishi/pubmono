from flask import Flask, request, jsonify
from tinydb import TinyDB, Query
from flask_cors import CORS
import random
import string
import os

app = Flask(__name__)
cors = CORS(app, resources={r"*": {"origins": "*"}})

db = TinyDB('db.json')
product_tbl = db.table('product')


# endpoint to create new product
@app.route("/add", methods=["POST"])
def add_data():
    product_name = request.json['product_name']
    price = request.json['price']
    tags = request.json['tags']
    purchased = request.json['purchased']
    random_str = ''.join([random.choice(string.ascii_letters + string.digits) for n in range(32)])

    new_product = {'_id': random_str,'product_name': product_name,'purchased':purchased, 'price': price, 'tags' : tags}
    product_tbl.insert(new_product)

    return jsonify(new_product)


# endpoint to show all products
@app.route("/all", methods=["GET"])
def get_data():
    filters = request.args.get('filter')
    if(filters):
        Product = Query()
        filtered = product_tbl.search(Product.tags.all([filters]))
        return jsonify(filtered)
    else:
        return jsonify(product_tbl.all())
    

# endpoint to show all products
@app.route("/cart", methods=["GET"])
def get_cart():
    Product = Query()
    filtered = product_tbl.search(Product.purchased == True)
    return jsonify(filtered)

# endpoint to update detail by id
@app.route("/update/<id>", methods=["GET"])
def data_update(id):
    Product = Query()
    product_tbl.update({'purchased': True}, Product._id == id)
    return jsonify(product_tbl.search(Product._id == id))



if __name__ == '__main__':
    app.run(debug=True)