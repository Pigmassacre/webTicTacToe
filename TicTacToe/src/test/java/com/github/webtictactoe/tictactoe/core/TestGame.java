package com.github.webtictactoe.tictactoe.core;

import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestGame {

    static IGame game;
    final static String GAME_PU = "game_pu";

    @Before // Run before each test to reset
    public void before() {
        game = GameFactory.getGame(GAME_PU);
    }

    @Test
    public void getAllCustomers() {
        IPlayerRegistry playerRegistry = game.getPlayerRegistry();
        
        Player one = new Player("Hej");
        Player two = new Player("Då");
        
        playerRegistry.add(one);
        playerRegistry.add(two);
        
        List<Player> listOfPlayers = playerRegistry.getAll();
        
        assertTrue(listOfPlayers.size() == 2);
    }

}
