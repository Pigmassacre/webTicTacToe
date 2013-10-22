

/*
 * 
 *  LOGIN Controller
 *  @author emileriksson
 */

var loginController  = (function () {
    return {
        login : function () {
            var username = $("#textUsername").val();
            var password = $("#textPassword").val();
            Lobby.login(username, password, 
            function(data) {
                $("#pageLogin").fadeOut(500, function () {
                    $("#pageLobby").fadeIn(500);
                });
                lobbyController.showPlayer(data);
            }, 
            function(jqXHR, textStatus) {
                $("#loginStatus").html("Login Failed: " + $.parseJSON(jqXHR.responseText).message);
                console.log($.parseJSON(jqXHR.responseText).message);
            });
        },
        reset : function () {
            $("#loginStatus").html('');
            $('#textUsername').val('');
            $('#textPassword').val('');
        },
        register : function () {
            var username = $("#textUsername").val();
            var password = $("#textPassword").val();
            Lobby.register(username, password, function(data) {
                loginController.login();
            },
            function(jqXHR, textStatus) {
                console.log($.parseJSON(jqXHR.responseText).message);
            });
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
                /* Initializing Game Canvas  */
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
            Lobby.login($.cookie('name'), $.cookie('password'), function() {
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

