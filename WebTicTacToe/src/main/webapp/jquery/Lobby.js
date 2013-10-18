/* 
 * @Author emil eriksson
 * Reveal Pattern for Lobby static class
 */

var Lobby = function () {
    var playerList = ['Player 1','Player 2'];
    var player = {
        name : 'discoKungen',
        score : 123
    };
    
    function publicRegister () {
        
        return true;
    }
    
    function publicLogout () {
        
        return true;
    }
    
    function publicLogin(name, password){
        
        return true;
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
    
    function getPublicPlayerData(){
        return player;
    }
    
    return {
        login : publicLogin,
        logout : publicLogout,
        register : publicRegister,
        findGame : publicFindGame,
        getPlayerList : getPublicPlayerList,
        getPlayerData : getPublicPlayerData
        
    };
}();


