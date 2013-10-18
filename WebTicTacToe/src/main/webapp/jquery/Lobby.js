/* 
 * @Author emil eriksson
 * Reveal Pattern for Lobby static class
 */

var Lobby = function () {
    var baseuri = document.location.toString() + 'lobby';
    var playerListRequest = { url: baseuri + '/playerlist',
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'websocket' ,
                    trackMessageLength : true,
                    fallbackTransport: 'long-polling'};
    var subSocket;
    
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
    }
    
    function publicLogout (done, fail) {
        var request = $.ajax({url: baseuri + '/logout',
                type: 'POST',
                dataType: 'json'
        });
        
        request.done(function(data) {
            subSocket.push();
            $.atmosphere.unsubscribe(playerListRequest);
            done(data);
        });
        
        request.fail(function(jqXHR, textStatus) {
           fail(jqXHR, textStatus);
        });
    }
    
    function publicLogin(name, password, done, fail){
        var request = $.ajax({url: baseuri + '/login',
                type: 'POST',
                data: $.stringifyJSON({ name: name, password: password }),
                contentType: 'application/json',
                dataType: 'json'
        });
        
        request.done(function(data) {
            subSocket = $.atmosphere.subscribe(playerListRequest);
            // This push here is to update the playerlist for all connected players.
            // Seems to be a bug with atmosphere, so have to set timeout for it... :/
            setTimeout(subSocket.push, 1000);
            //subSocket.push();
            done(data);
        });
        
        request.fail(function(jqXHR, textStatus) {
           fail(jqXHR, textStatus);
        });
    }
    
    function publicFindGame(size, done){
        var gameSession;
        
        //Called when game is found
        done(gameSession);
        return true;
    }
    
    playerListRequest.onOpen = function (response) {
        console.log('connected');
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
        
        lobbyController.updatePlayerList(json.names);
    };
    
    return {
        login : publicLogin,
        logout : publicLogout,
        register : publicRegister,
        findGame : publicFindGame
        
    };
}();


