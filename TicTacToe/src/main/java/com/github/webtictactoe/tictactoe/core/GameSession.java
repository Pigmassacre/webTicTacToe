/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.tictactoe.core;

/**
 *
 * @author emileriksson
 */
public class GameSession {
    private Player p1;
    private Player p2;
    
    private Player activePlayer;
    
    private IGame game;
    
    public GameSession(IGame g, Player p1, Player p2){
        game = g;
        this.p1 = activePlayer = p1;
        p1.setMark(Game.Mark.CROSS);
        this.p2 = p2;
        p2.setMark(Game.Mark.CIRCLE);
    }
    
    public boolean move(int x,int y,Player p){
        if(p.equals(activePlayer)){
            if(game.isFree(x, y)){
                game.move(x, y, p.getMark());
                if(activePlayer == p1)
                    activePlayer = p2;
                else
                    activePlayer = p1;
                return game.gameWon();
            }
        }
        return false;
    }
    
    public Game.Mark[][] getBoard(){
        return game.getBoard();
    }
    
    public Boolean gameWon() {
        return game.gameWon();
    }
}
