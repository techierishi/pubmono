var ws;

function connect(username) {
    //var username = document.getElementById("username").value;
    ws = new WebSocket("ws://" + document.location.host + "/chat_websocket/chat/" + username);


    ws.onmessage = function(event) {
    var log = document.getElementById("log");
        console.log(event.data);
        var message = JSON.parse(event.data);
        $('.chat').append('<div class="bubble you">'+message.content+'</div>');

    };
}

function send() {
    var content = document.getElementById("msg").value;
    var to = document.getElementById("to").value;
    var json = JSON.stringify({
        "to":to,
        "content":content
    });

    ws.send(json);
    $('.chat').append('<div class="bubble me">'+content+'</div>');
    $('#msg').val('');
}