

/*
 * 
 *  LOGIN Controller
 *  @author emileriksson
 */

var loginController  = (function () {
    return {
        login : function () {
            console.log('logging in');
            var username = $("#textUsername").val();
            var password = $("#textPassword").val();
            Lobby.login(username, password, 
            function(data) {
                console.log('login successful');
                $("#pageLogin").fadeOut(500, function () {
                    $("#pageLobby").fadeIn(500);
                });
                lobbyController.showPlayer(data);
            }, 
            function(jqXHR, textStatus) {
                console.log($.parseJSON(jqXHR.responseText).message);
            });
        },
        reset : function () {
            $('#textUsername').val('');
            $('#textPassword').val('');
        },
        register : function (event) {
            console.log('in loginController.register');
            var username = $("#textUsername").val();
            var password = $("#textPassword").val();
            Lobby.register(username, password, function(data) {
                console.log('register successful, logging in');
                loginController.login();
            },
            function(jqXHR, textStatus) {
                console.log($.parseJSON(jqXHR.responseText).message);
            });
            event.stopImmediatePropagation(); // This is to stop register being called multiple times for some reason.
        }
    };
})();

/*
 * 
 *  LOBBY Controller
 *  @author emileriksson
 */

var lobbyController = (function () {
    return {
        findGame : function () {
            var size = $('#sizeVal').val();
            Lobby.findGame(size, function () {
                console.log('found game succeded in controller');
            }, function() {
                console.log('failed in controller');
            });
        },
        logout : function () {
            Lobby.logout(function(data) {
               $("#pageLobby").fadeOut(300, function () {
                    $("#pageLogin").fadeIn(300);
                });
            }, function(jqXHR, textStatus) {
                console.log($.parseJSON(jqXHR.responseText).message);
            });
        },
        updatePlayerList : function (playerList) {
            var list = $('#playList').html('');
            for(var i = 0; i < playerList.length; i++) {
                list.append('<li>' + playerList[i] + '</li>');
            }
            
        },
        showPlayer : function () {
            //clear box
            $('.player').html('');
            $('.player').append(
                    "<span>Logged in as: " + $.cookie('name') + "</span><br>"// +
                    //"<span>Score: " + data.score + "</span>"
        );
        },
        showGame : function (size) {
            gameCanvas.init($('#gameCanvas'),
                $('#gameCanvas')[0].getContext('2d'), size);
            $("#pageLobby").fadeOut(300, function () {
                $("#pageGame").fadeIn(300);
            });
        }
    };
})();

/*
 * 
 *  GAME Controller
 *  @author emileriksson
 *  @author pigmassacre (olof karlsson)
 */

var gameController = (function () {
    return {
        buttonToLobby : function () {
            Game.stopGame();
            // Since the server logs us out when connecting us to a game, we have to log back in again when returning to lobby.
            // Yes, server could possibly be designed better but we had to make do with the time we've got. :/
            Lobby.login($.cookie('name'), $.cookie('password'), function() {
                Lobby.forcePlayerListPush(); // This is to correctly update the playerlist once logged in again!
                console.log('relogin succeded');
            }, function() {
                console.log('relogin failed');
            });
            $("#pageGame").fadeOut(300, function () {
                $("#pageLobby").fadeIn(300);
            });
        },
        
        mouseClick: function (canvas, evt) {
            var rect = canvas[0].getBoundingClientRect(),
                xPos = evt.clientX - rect.left,
                yPos = evt.clientY - rect.top;
            xPos = parseInt(xPos / (400 / gameCanvas.getBoardSize()), 10);
            yPos = parseInt(yPos / (400 / gameCanvas.getBoardSize()), 10);
            Game.move(xPos,yPos);
            console.log('clicked, sending gamemove');
        },
                
        updateGameBoard: function(gameBoard) {
            console.log('updating gameboard');
            var xEntry;
            for (var x = 0; x < gameBoard.length; x++) {
                xEntry = gameBoard[x].item;
                for (var y = 0; y < xEntry.length; y++) {
                    gameCanvas.fill(x, y, parseInt(xEntry[y], 10));
                }
            }
        },
                
        startGame: function(baseuri, uuid, size) {
            Game.startGame(baseuri, uuid, size);
        }
    };
})();

