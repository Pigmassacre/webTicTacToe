package com.github.webtictactoe.tictactoe.core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestGame {

    static IGame game;
    final static String GAME_PU = "game_pu";
    final static String GAME_TEST_PU = "game_test_pu";

    @Before // Run before each test to reset
    public void before() {
        game = GameFactory.getGame(GAME_PU);
    }

    @Test
    public void testPlayerRegistryAdd() {
        // Add
        IPlayerRegistry playerRegistry = game.getPlayerRegistry();
        
        Player one = new Player("One");
        Player two = new Player("Two");
        
        playerRegistry.add(one);
        playerRegistry.add(two);
        
        assertTrue(playerRegistry.getAll().size() == 2);
        
        // Remove
        playerRegistry.remove(one.getName());
        playerRegistry.remove(two.getName());
        
        Player three = new Player("Three");
        Player four = new Player("Four");
        
        playerRegistry.add(three);
        playerRegistry.add(four);
        
        assertTrue(playerRegistry.getAll().size() == 2);
        
        playerRegistry.remove(three.getName());
        
        assertTrue(playerRegistry.getAll().size() == 1);
        
        playerRegistry.remove(four.getName());
        
        assertTrue(playerRegistry.getAll().isEmpty());
        
        // Update
        Player five = new Player("Five");
        Player six = new Player("Six");
        
        five.setScore(100);
        six.setScore(500);
        
        playerRegistry.add(five);
        playerRegistry.add(six);
        
        five.setScore(five.getScore() * 8);
        six.setScore(six.getScore() * 2);
        
        playerRegistry.update(five);
        playerRegistry.update(six);
    }

}
