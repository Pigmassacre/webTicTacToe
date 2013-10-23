package com.github.webtictactoe.tictactoe.core;

/*
 * @author antonwestberg
 */

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestGame {

    static IGame game;
    final static String GAME_PU = "game_pu";
    final static String GAME_TEST_PU = "game_test_pu";
    
    static Lobby lobby;
    static PlayerRegistry playerRegistry;

    @Before // Run before each test to reset
    public void before() {
        lobby = new Lobby(GAME_PU);
        playerRegistry = new PlayerRegistry(GAME_PU);
    }
    
    @Test
    public void testPlayerRegistryAdd() {
        
        Player one = new Player("One", "123");
        Player two = new Player("Two", "123");
        
        playerRegistry.add(one);
        playerRegistry.add(two);
        
        assertTrue(playerRegistry.getAll().size() == 2);
        
        // Remove
        playerRegistry.remove(one.getName());
        playerRegistry.remove(two.getName());
        
        Player three = new Player("Three", "123");
        Player four = new Player("Four", "123");
        
        playerRegistry.add(three);
        playerRegistry.add(four);
        
        assertTrue(playerRegistry.getAll().size() == 2);
        
        playerRegistry.remove(three.getName());
        
        assertTrue(playerRegistry.getAll().size() == 1);
        
        playerRegistry.remove(four.getName());
        
        assertTrue(playerRegistry.getAll().isEmpty());
        
        // Update
        Player five = new Player("Five", "123");
        Player six = new Player("Six", "123");
        
        five.setScore(100);
        six.setScore(500);
        
        playerRegistry.add(five);
        playerRegistry.add(six);
        
        five.setScore(five.getScore() * 8);
        six.setScore(six.getScore() * 2);
        
        playerRegistry.update(five);
        playerRegistry.update(six);
    }
    
    @Test
    public void testLogin() {
        // No players should be online initially
        assertTrue(lobby.getOnlinePlayers().isEmpty());
        
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
        
        //Try to login twice
        lobby.login("p1","123");
        
        // One player should be online
        assertTrue(lobby.getOnlinePlayers().size() == 1);
        
        //Wrong password
        lobby.login("p2","000");
        assertTrue(lobby.getOnlinePlayers().size() == 1);
    }
    
    @Test 
    public void testCreateGame() {
        Player p1 = new Player("p1", "123");
        lobby.register("p1","123");
        lobby.login("p1","123");
        
        Player p2 = new Player("p2", "123");
        lobby.register("p2","123");
        lobby.login("p2","123");
        
        assertTrue(lobby.getOnlinePlayers().size() == 2);
        lobby.findGame(p1, 3);
        
        assertTrue(lobby.getOnlinePlayers().isEmpty());
        assertTrue(lobby.getActiveGames().size() == 1);
        
    }
    
    @Test
    public void testMove() {
        Player p1 = new Player("p1", "123");
        Player p2 = new Player("p2", "123");
        IGame g = new Game(3);
        GameSession session = new GameSession(g, p1, p2);
        
        //Make sure board is empty
        Mark[][] gameBoard = session.getBoard();
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                assertTrue(gameBoard[x][y].equals(Mark.EMPTY));
            }
        }
        
        // Active player should be p1.
        assertEquals(p1, session.getActivePlayer());
        
        //p1 marks 0.0
        assertTrue(session.move(0, 0, p1));
        
        // Active player should be p2.
        assertEquals(p2, session.getActivePlayer());
        
        //p2 marks 0.0 - fails, already marked
        assertFalse(session.move(0, 0, p2));
        
        // Active player should be p2.
        assertEquals(p2, session.getActivePlayer());
        
        //p1 marks 0.1 - fails, still p2's turn
        assertFalse(session.move(0, 1, p1));
        
        // Active player should be p2.
        assertEquals(p2, session.getActivePlayer());
        
        //p2 marks 0.1
        assertTrue(session.move(0, 1, p2));
        
        //Check the board, should be cross at 0.0, circle at
        // 0.1, empty everywhere else
        gameBoard = session.getBoard();
        assertTrue(gameBoard[0][0].equals(Mark.CROSS));
        assertTrue(gameBoard[0][1].equals(Mark.CIRCLE));
        
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                if (x != 0 && (y != 0 || y != 1)) {
                    assertTrue(gameBoard[x][y].equals(Mark.EMPTY));
                }
            }
        }
        
    }
    
    @Test
    public  void testColumnWin() {
        Player p1 = new Player("p1", "123");
        Player p2 = new Player("p2", "123");
        IGame g = new Game(3);
        GameSession session = new GameSession( g, p1, p2);
        
        // Make moves so that player one wins.
        assertTrue(session.move(0, 0, p1));
        assertTrue(session.move(1, 0, p2));
        assertTrue(session.move(0, 1, p1));
        assertTrue(session.move(1, 1, p2));
        assertTrue(session.move(0, 2, p1));
        
        // Player one should've won.
        assertEquals(p1, session.getWinner());
    }
    
    @Test
    public  void testRowWin() {
        Player p1 = new Player("p1", "123");
        Player p2 = new Player("p2", "123");
        IGame g = new Game(3);
        GameSession session = new GameSession( g, p1, p2);
        
        // Make moves so that player one wins.
        assertTrue(session.move(0, 0, p1));
        assertTrue(session.move(0, 1, p2));
        assertTrue(session.move(1, 0, p1));
        assertTrue(session.move(1, 1, p2));
        assertTrue(session.move(2, 0, p1));
        
        // Player one should've won.
        assertEquals(p1, session.getWinner());
    }
    
    @Test
    public  void testDiagonalWin() {
        Player p1 = new Player("p1", "123");
        Player p2 = new Player("p2", "123");
        IGame g = new Game(3);
        GameSession session = new GameSession( g, p1, p2);
        
        // Make moves so that player one wins.
        assertTrue(session.move(0, 0, p1));
        assertTrue(session.move(0, 1, p2));
        assertTrue(session.move(1, 1, p1));
        assertTrue(session.move(0, 2, p2));
        assertTrue(session.move(2, 2, p1));
        
        // Player one should've won.
        assertEquals(p1, session.getWinner());
    }
    
    @Test
    public  void testAntiDiagonalWin() {
        Player p1 = new Player("p1", "123");
        Player p2 = new Player("p2", "123");
        IGame g = new Game(3);
        GameSession session = new GameSession( g, p1, p2);
        
        // Make moves so that player one wins.
        assertTrue(session.move(0, 2, p1));
        assertTrue(session.move(1, 0, p2));
        assertTrue(session.move(1, 1, p1));
        assertTrue(session.move(1, 2, p2));
        assertTrue(session.move(2, 0, p1));
        
        // Player one should've won.
        assertEquals(p1, session.getWinner());
    }

}