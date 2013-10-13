$(function () {
    "use strict";
    
    var baseuri = document.location.toString() + 'lobby';
    var userlist = $('#userlist');
    var content = $('#content');
    var name = $('#name'); 
    var password = $('#password'); // Yes yes, terribly unsafe and stupid... :P
    var logout = $('#logout');
    var socket = $.atmosphere;
    var request = { url: baseuri + '/playerlist',
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'websocket' ,
                    trackMessageLength : true,
                    fallbackTransport: 'long-polling'};
    var subSocket;


    request.onOpen = function(response) {
        content.html($('<p>', { text: 'Atmosphere connected using ' + response.transport }));
        name.removeAttr('disabled');
        password.removeAttr('disabled');
    };

    request.onMessage = function (response) {
        var message = response.responseBody;
        try {
            var json = $.parseJSON(message);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message);
            return;
        }
        
        // If there is only one player in the playerlist, we do this so we can 
        // loop through json.names safely.
        if (typeof json.names === 'string') {
            json.names = [json.names];
        }
        
        // Empties the html in the userlist element.
        userlist.html('');
        
        for (var i = 0; i < json.names.length; i++) {
            userlist.append($('<p>', { text: json.names[i] }));
        }
    };

    request.onError = function(response) {
        content.html($('<p>', { text: 'Sorry, but there\'s some problem with your '
            + 'socket or the server is down' }));
    };

    password.keydown(function(e) {
        if (e.keyCode === 13) {
            var givenName = name.val();
            var givenPassword = password.val();
            
            // POSTs the name and password to the server.
            $.ajax({url: baseuri + '/login',
                                      type: 'POST',
                                      data: $.stringifyJSON({ name: givenName, password: givenPassword }),
                                      contentType: 'application/json',
                                      dataType: 'json',
                                      success: onLoginResponse
            });
            
            name.attr('disabled', 'disabled');
            password.attr('disabled', 'disabled');
        }
    });
    
    function onLoginResponse(data) {
        if (data.success === 'true') {
            $('#login-name').hide();
            $('#login-password').hide();
            
            // We show the user controls.
            $('#user-controls').show();
            
            // Now, since we're logged in, we subscribe to the request.
            subSocket = socket.subscribe(request);
            
            // This push here is to update the playerlist for all connected players.
            setTimeout(subSocket.push, 500);
        } else {
            name.removeAttr('disabled');
            password.removeAttr('disabled');
        }
        content.html($('<p>', { text: data.message }));
    };

    logout.click(function(event) {
        var logoutRequest = { url: baseuri + '/logout',
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'websocket',
                    trackMessageLength : true,
                    fallbackTransport: 'long-polling'};
                
        subSocket.push(logoutRequest);
    });

});