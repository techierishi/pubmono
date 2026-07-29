var http = require('http');
var querystring = require('querystring');
var fs = require('fs');
var path = require('path');

function processPost(request, response, callback) {
    var queryData = "";
    if(typeof callback !== 'function') return null;

    if(request.method == 'POST') {
        request.on('data', function(data) {
            queryData += data;
            if(queryData.length > 1e6) {
                queryData = "";
                response.writeHead(413, {'Content-Type': 'text/plain'}).end();
                request.connection.destroy();
            }
        });

        request.on('end', function() {
            request.post = querystring.parse(queryData);
            callback();
        });

    } else {
        response.writeHead(405, {'Content-Type': 'text/plain'});
        response.end();
    }
}

http.createServer(function(request, response) {
    if(request.method == 'POST') {
        processPost(request, response, function() {
            console.log(request.post);
                // Use request.post here
                var unix = Math.round(+new Date()/1000);
                //var file = path.join(__dirname, 'output', unix+'_dbgg.html');
                //fs.writeFileSync(file, JSON.stringify(request.post));
fs.writeFile('output/'+unix+'_dbgg.html', JSON.stringify(request.post), function (err) {
  if (err) return console.log(err);
  console.log('Hello World > helloworld.txt');
});
                response.writeHead(200, "OK", {'Content-Type': 'text/plain'});
                response.end();
        });
    } else {
        response.writeHead(200, "OK", {'Content-Type': 'text/plain'});
        response.end();
    }
