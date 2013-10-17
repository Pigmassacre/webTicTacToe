

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
            if(debug || Lobby.login(username, password)){
                $("#pageLogin").fadeOut(500, function () {
                    $("#pageLobby").fadeIn(500);
                });
                lobbyController.showPlayer();
            }
        },
        reset : function () {
            $('#textUsername').val('');
            $('#textPassword').val('');
        },
        register : function () {
            var username = $("#textUsername").val();
            var password = $("#textPassword").val();
            if(debug || Lobby.register(username, password)){
                $("#pageLogin").fadeOut(500, function () {
                    $("#pageLobby").fadeIn(500);
                });
            }
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
            });
        },
        logout : function () {
            if(debug || Lobby.logout()){
                $("#pageLobby").fadeOut(300, function () {
                    $("#pageLogin").fadeIn(300);
                });
            }
        },
        updatePlayerList : function () {
            var playerList = Lobby.getPlayerList();
            var list = $('#playList').html('');
            for(var i = 0; i < playerList.length; i++) {
                list.append('<li>' + playerList[i] + '</li>');
            }
            
        },
        showPlayer : function () {
            var data = Lobby.getPlayerData();
            //clear box
            $('.player').html('');
            $('.player').append(
                    "<span>Logged in as: " + data.name + "</span><br>" +
                    "<span>Score: " + data.score + "</span>"
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
        }
    };
})();

