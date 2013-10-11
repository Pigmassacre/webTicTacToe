package com.github.webtictactoe.tictactoe.core;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestGame {

    static IGame game;
    final static String GAME_PU = "game_pu";
    final static String GAME_TEST_PU = "game_test_pu";
    
    static Lobby lobby;

    @Before // Run before each test to reset
    public void before() {
        lobby = new Lobby(GAME_PU);
    }
    
    @Test
    public void testLogin(){
        
        // No players should be online initially
        assertTrue(lobby.getOnlinePlayers().size() == 0);
        
        Player p1 = new Player("p1", "123");
        lobby.register("p1","123");
        lobby.login("p1","123");
        
        // One player should be online
        assertTrue(lobby.getOnlinePlayers().size() == 1);
        
        Player p2 = new Player("p2", "123");
        lobby.register("p2","123");
        lobby.login("p2","123");
        
        // Two player should be online
        assertTrue(lobby.getOnlinePlayers().size() == 2);
        
        lobby.logout("p2");
        
        // One player should be online
        assertTrue(lobby.getOnlinePlayers().size() == 1);
    }
/*
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
*/
}
