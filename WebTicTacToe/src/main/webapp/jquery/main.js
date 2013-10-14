/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    "use strict";
    $("#pageLogin").fadeIn(1500);
    $("#pageLogin").click(function () {
        $("#pageLogin").fadeOut(500, function () {
            $("#pageLobby").fadeIn(500);
        });
    });
    $("#pageLobby").click(function () {
        $("#pageLobby").fadeOut(500, function () {
            $("#pageGame").fadeIn(500);
        });
    });
    $("#pageGame").click(function () {
        $("#pageGame").fadeOut(500, function () {
            $("#pageLogin").fadeIn(500);
        });
    });
});