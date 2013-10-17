/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var debug = true;



$(function () {
    "use strict";
    /*
     * EVENT LISTENERS
     */
    $('#buttonLogin').click(loginController.login);
    
    $('#buttonRegister').click(loginController.register);
    
    $('#buttonReset').click(loginController.reset);
    
    $('#buttonRegister').click(loginController.register);
    
    $('#buttonFindgame').click(lobbyController.findGame);
    
    $('#buttonLogout').click(lobbyController.logout);
    
    $('#buttonToLobby').click(gameController.buttonToLobby);
    

    lobbyController.updatePlayerList();
    /*
     * Fade in page
     */
    $("#pageLogin").fadeIn(100);
    
    $("#pageGame").click(function () {
        
    });
    
});

