package com.github.webtictactoe.webtictactoe.game;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CIRCLE;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CROSS;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.EMPTY;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the response the server broadcasts to all clients connected to the matching UUID.
 * It contains the next active player, the state of the gameboard and the name of the winner, if
 * there is a winner yet.
 * @author pigmassacre
 */
@XmlRootElement
public class GameResponse {

    public String activePlayer;
    public Integer[][] gameBoard;
    public String winner;

    public GameResponse(String activePlayer, Mark[][] gameBoard, String winner) {
        this.activePlayer = activePlayer;
        this.gameBoard = convertGameBoard(gameBoard);
        this.winner = winner;
    }
    
    public GameResponse(String activePlayer, Mark[][] gameBoard) {
        this.activePlayer = activePlayer;
        this.gameBoard = convertGameBoard(gameBoard);
    }
    
    private Integer[][] convertGameBoard(Mark[][] gameBoard) {
        // The gameboard that we are to return in the response.
        Integer[][] convertedGameBoard = new Integer[gameBoard.length][gameBoard[0].length];

        // We convert the gameboard from Marks to Integers.
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                switch (gameBoard[x][y]) {
                    case EMPTY:
                        convertedGameBoard[x][y] = 0; // As expected by GameCanvas.js
                        break;
                    case CIRCLE:
                        convertedGameBoard[x][y] = 1; // As expected by GameCanvas.js
                        break;
                    case CROSS:
                        convertedGameBoard[x][y] = -1; // As expected by GameCanvas.js
                        break;
                }
            }
        }
        
        return convertedGameBoard;
    }

    public GameResponse() {
        
    }
    
}