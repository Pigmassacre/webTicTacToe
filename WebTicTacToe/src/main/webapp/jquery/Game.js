/* 
 * @Author emileriksson
 * @Author pigmassacre (olof karlsson)
 * Reveal pattern for static class/module game
 */

var Game = function () {
    
    var gameRequest;
    var gameSocket;
    
    function publicStartGame(baseuri, uuid, size) {
        gameRequest = { url: baseuri + '/game/' + uuid,
                contentType : "application/json",
                logLevel : 'debug',
                transport : 'long-polling' ,
                trackMessageLength : true};

        gameRequest.onOpen = function (response) {
            console.log('connected to game with UUID: ' + uuid);
        };

        gameRequest.onMessage = function (response) {
            console.log('got a gameresponse');

            var message = response.responseBody;

            try {
                var json = $.parseJSON(message);
            } catch (e) {
                console.log('This doesn\'t look like a valid JSON: ', message);
                return;
            }

            $('#playerTurn').html(json.activePlayer);

            gameController.updateGameBoard(json.gameBoard);

            if (json.winner !== "Undecided") {
                if ($.cookie('name') === json.winner) {
                    console.log('you won!!! :)');
                } else {
                    console.log('you lost! :(');
                }
            }
        };
        
        gameRequest.onClose = function (response) {
            if (response.state === 'unsubscribe') {
                // Connection lost.
                console.log('Connection to ' + uuid + 'lost, going back to lobby.');
                gameController.buttonToLobby();
            }
        };

        console.log('subscribing to gamerequest');
        gameSocket = $.atmosphere.subscribe(gameRequest);

        console.log('showing gameboard of size ' + size);
        lobbyController.showGame(size);
    }
    
    function publicStopGame() {
        if (typeof(gameSocket.unsubscribe) === 'function') {
            gameSocket.unsubscribe();
        }
    }
    
    function publicMove(xPos, yPos){
        console.log('sending gamemove to: ' + gameRequest.url);
        var deferred = $.ajax({url: gameRequest.url,
                type: 'POST',
                data: $.stringifyJSON({ xPos: xPos, yPos: yPos }),
                contentType: 'application/json',
                async: false
        });
        deferred.fail(function (jqXHR, textStatus, errorThrown) {
            console.log('failed for some reason');
            console.log(textStatus);
        });
        deferred.always(function () {
            console.log('finished with gamemove post'); 
        });
    }
    
    
    return {
        startGame : publicStartGame,
        stopGame : publicStopGame,
        move : publicMove
        
    };
}();
