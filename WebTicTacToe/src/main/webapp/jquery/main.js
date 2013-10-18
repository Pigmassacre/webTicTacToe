/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var debug = false;



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
    
    
    
    
    /* FUUUULT */
    var isDown = false;
    $('canvas').on('mousedown', function (e) {
        if (isDown === false) {
            isDown = true;
            gameController.mouseClick($('canvas'),e);
            isDown = false;
        }
    });
    
    /*
     * Fade in page
     */
    $("#pageLogin").fadeIn(100);
    
    $("#pageGame").click(function () {
        
    });
    
});

