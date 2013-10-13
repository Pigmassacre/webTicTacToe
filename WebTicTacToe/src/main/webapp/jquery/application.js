$(function () {
    "use strict";

    var userlist = $('#userlist');
    var content = $('#content');
    var name = $('#name'); 
    var password = $('#password'); // Yes yes, terribly unsafe and stupid... :P
    var socket = $.atmosphere;
    var loginRequest = { url: document.location.toString() + 'lobby/login',
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'websocket' ,
                    trackMessageLength : true,
                    fallbackTransport: 'long-polling'};


    loginRequest.onOpen = function(response) {
        userlist.html($('<p>', { text: 'A list of users will appear here when you have logged in.' }));
        content.html($('<p>', { text: 'Atmosphere connected using ' + response.transport }));
        name.removeAttr('disabled').focus();
        password.removeAttr('disabled').focus();
    };

    loginRequest.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = jQuery.parseJSON(message);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message);
            return;
        }
        
        if (json.success === 'true') {
            $('#login-name').hide();
            $('#login-password').hide();
        }
        content.html($('<p>', { text: json.message }));
    };

    loginRequest.onError = function(response) {
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'socket or the server is down' }));
    };

    var subSocket = socket.subscribe(loginRequest);

    password.keydown(function(e) {
        if (e.keyCode === 13) {
            var givenName = name.val();
            var givenPassword = password.val();
            
            // Pushes the name and password to the server.
            subSocket.push(jQuery.stringifyJSON({ name: givenName, password: givenPassword }));
        }
    });

});