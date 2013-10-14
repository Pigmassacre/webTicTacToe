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

    // If a cookie exists, populate the name field with the given name.
    name.val($.cookie("name"));
    password.focus();

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
            
            tryToLogin(givenName, givenPassword);
            
            name.val('');
            password.val('');
            
            name.attr('disabled', 'disabled');
            password.attr('disabled', 'disabled');
        }
    });
    
    function tryToLogin(givenName, givenPassword) {
        // POSTs the name and password to the server.
        $.ajax({url: baseuri + '/login',
                type: 'POST',
                data: $.stringifyJSON({ name: givenName, password: givenPassword }),
                contentType: 'application/json',
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                      name.removeAttr('disabled');
                      password.removeAttr('disabled');
                      var json = $.parseJSON(jqXHR.responseText);
                      switch(jqXHR.status) {
                          case 400:
                              content.html($('<p>', { text: json.message }));
                              name.val($.cookie("name"));
                              break;
                          }
                      },
                success: onLoginResponse
        });
    }
    
    function onLoginResponse(data, textStatus, jqXHR) {
        // Successfully logged in!
        $('#header').html("<h3>Welcome to the WebTicTacToe lobby, " + $.cookie("name") + "!");
        $('#login-name').hide();
        $('#login-password').hide();

        // We show the user controls.
        $('#user-controls').show();

        // Now, since we're logged in, we subscribe to the request.
        subSocket = socket.subscribe(request);

        // This push here is to update the playerlist for all connected players.
        // Seems to be a bug with atmosphere... :/
        setTimeout(subSocket.push, 1100);
        
        content.html($('<p>', { text: data.message }));
    };

    logout.click(function(event) {
        // POSTs the name and password to the server.
        $.ajax({url: baseuri + '/logout',
                type: 'POST',
                dataType: 'json',
                error: function(jqXHR, textStatus, errorThrown) {
                      var json = $.parseJSON(jqXHR.responseText);
                      switch(jqXHR.status) {
                          case 400:
                              content.html($('<p>', { text: json.message }));
                              break;
                          }
                      },
                success: onLogoutResponse
        });
    });

    function onLogoutResponse(data) {
        // Logout worked, cookie has been removed.
        $('#header').html("<h3>Welcome to the WebTicTacToe lobby!");
        userlist.html($(''));
        content.html($('<p>', { text: "Enter a username and password to login." }));
        $('#login-name').show();
        $('#login-password').show();
        $('#user-controls').hide();
        
        // Tell all connected players to update their playerlists.
        subSocket.push();
        
        // Unsubscribe from the socket.
        socket.unsubscribe();
    }

});