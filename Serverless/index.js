var http = require('http');
var fs = require('fs');
var url = require('url');
var qs = require('querystring');
var AWS = require('aws-sdk');
const uuid = require('uuid');

var app = http.createServer(function(request,response){
    var url = request.url;
    
    if(request.url == '/'){
      url = '/views/index.html';
      response.writeHead(200);
      response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/write'){
        url = '/views/write.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/manage'){
        url = '/views/manage.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/login'){
        url = '/views/login.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/join'){
        url = '/views/join.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/index'){
        url = '/views/main.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/logout'){
        url = '/views/logout.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/leave'){
        url = '/views/leave.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/findid'){
        url = '/views/findid.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/findpw'){
        url = '/views/findpw.html';
        response.writeHead(200);
        response.end(fs.readFileSync(__dirname + url));
    } else if(request.url == '/DynamoDB.js'){
        var file = fs.createReadStream('js/DynamoDB.js');
        file.pipe(response);
        response.writeHead(200);
    } else {
        response.writeHead(404);
        response.end('Not Found');
    }

    
});
app.listen(3000)