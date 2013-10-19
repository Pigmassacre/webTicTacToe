/* 
 * @Author emil eriksson
 * @Author olof karlsson (pigmassacre)
 * Reveal Pattern for Lobby static class
 */

var Lobby = function () {
    var baseuri = document.location.toString() + 'lobby';
    var playerListRequest = { url: baseuri + '/playerlist',
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'long-polling' ,
                    trackMessageLength : true};
    var playerListSocket;
    var playerNameRequest;
    var playerNameSocket;
    
    function publicRegister (name, password, done, fail) {
        var request = $.ajax({url: baseuri + '/login/register',
                type: 'POST',
                data: $.stringifyJSON({ name: name, password: password }),
                contentType: 'application/json',
                dataType: 'json'
        });
        
        request.done(function(data) {
            done(data);
        });
        
        request.fail(function(jqXHR, textStatus) {
           fail(jqXHR, textStatus);
        });
    };
    
    function publicLogout (done, fail) {
        var request = $.ajax({url: baseuri + '/logout',
                type: 'POST',
                dataType: 'json'
        });
        
        request.done(function(data) {
            playerListSocket.push();
            $.atmosphere.unsubscribe(playerListRequest);
            done(data);
        });
        
        request.fail(function(jqXHR, textStatus) {
           fail(jqXHR, textStatus);
        });
    };
    
    function publicLogin(name, password, done, fail){
        var request = $.ajax({url: baseuri + '/login',
                type: 'POST',
                data: $.stringifyJSON({ name: name, password: password }),
                contentType: 'application/json',
                dataType: 'json'
        });
        
        request.done(function(data) {
            playerNameRequest = { url: baseuri + '/player/' + $.cookie('name'),
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'long-polling' ,
                    trackMessageLength : true};
                
            playerNameRequest.onOpen = function (response) {
                console.log('connected to playernamerequest');
            };

            playerNameRequest.onMessage = function (response) {
                var message = response.responseBody;
                
                try {
                    var json = $.parseJSON(message);
                } catch (e) {
                    console.log('This doesn\'t look like a valid JSON: ', message);
                    return;
                }
                
                gameController.startGame(baseuri, json.uuid, json.size);
            };
            
            playerNameSocket = $.atmosphere.subscribe(playerNameRequest);
            playerListSocket = $.atmosphere.subscribe(playerListRequest);
            
            done(data);
        });
        
        request.fail(function(jqXHR, textStatus) {
           fail(jqXHR, textStatus);
        });
    };
    
    function publicFindGame(size, done, fail){
        var request = $.ajax({url: baseuri + '/game/findgame/' + size,
                type: 'POST'
        });
        
        request.done(function(data) {
            console.log('found game');
            done(data);
        });
        
        request.fail(function(jqXHR, textStatus) {
            console.log('failed to find game');
            fail(jqXHR, textStatus);
        });
    };
    
    playerListRequest.onOpen = function (response) {
        console.log('connected to playerlistrequest');
        playerListSocket.push();
    };
    
    playerListRequest.onMessage = function (response) {
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
        
        console.log('updating playerlist: ' + json.names);
        lobbyController.updatePlayerList(json.names);
    };
    
    return {
        login : publicLogin,
        logout : publicLogout,
        register : publicRegister,
        findGame : publicFindGame
    };
}();


