/* 
 * @Author emil eriksson
 * Reveal Pattern for Lobby static class
 */

var Lobby = function () {
    var baseuri = document.location.toString() + 'lobby';
    var playerList = ['Player 1','Player 2'];
    
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
    
    function getPublicPlayerList(){
        return playerList;
    }
    /*
    function getPublicPlayerData(){
        return player;
    }*/
    
    return {
        login : publicLogin,
        logout : publicLogout,
        register : publicRegister,
        findGame : publicFindGame,
        getPlayerList : getPublicPlayerList,
        /*getPlayerData : getPublicPlayerData*/
        
    };
}();


