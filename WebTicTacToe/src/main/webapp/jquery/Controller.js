

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
                console.log($.parseJSON(jqXHR.responseText).message);
            });
        },
        reset : function () {
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
                gameCanvas.init($('#gameCanvas'),
                    $('#gameCanvas')[0].getContext('2d'), size);
                $("#pageLobby").fadeOut(300, function () {
                    $("#pageGame").fadeIn(300);
                });
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
        }
    };
})();

/*
 * 
 *  GAME Controller
 *  @author emileriksson
 */

var gameController = (function () {
    return {
        buttonToLobby : function () {
            //TODO 
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
            gameCanvas.fill(xPos, yPos, gameCanvas.fillType.cross);
        }
    };
})();

