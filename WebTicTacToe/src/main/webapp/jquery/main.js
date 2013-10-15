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
    
    $('#buttonReset').click(loginController.reset);
    
    $('#buttonRegister').click(loginController.register);
    
    $('#buttonFindgame').click(lobbyController.findGame);
    
    $('#buttonLogout').click(lobbyController.logout);
    
    $('#buttonToLobby').click(lobbyController.backToLobby);
    
    /* Initializing Game Canvas  */
    gameCanvas.init($('#gameCanvas'),
        $('#gameCanvas')[0].getContext('2d'));
    /*
     * Fade in page
     */
    $("#pageLogin").fadeIn(1500);
    
    $("#pageGame").click(function () {
        
    });
    
});

