/* 
 * @Author emileriksson
 * Reveal pattern for static class/module game
 */

var game = function () {
    var myTurn = false;
    var type;
    
    function publicMove(){
        var array, player, isWon;
        return { 
                    board : array, 
                    lastPlayer : player,
                    isGameWon : isWon };
    }
    
    return {
        move : publicMove
        
    };
}();
