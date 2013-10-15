

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
            }
        },
        reset : function () {
            $('#textUsername').val('');
            $('#textPassword').val('');
        },
        register : function () {
            // TODO
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
            if(debug || Lobby.findGame(size)){
                $("#pageLobby").fadeOut(500, function () {
                    $("#pageGame").fadeIn(500);
                });
            } 
        },
        logout : function () {
            if(debug || Lobby.logout()){
                $("#pageLobby").fadeOut(500, function () {
                    $("#pageLogin").fadeIn(500);
                });
            }
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
        backToLobby : function () {
            //TODO 
            $("#pageGame").fadeOut(500, function () {
                $("#pageLogin").fadeIn(500);
            });
        }
    };
})();

