package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CIRCLE;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CROSS;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.EMPTY;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameResponse {

    public String activePlayer;
    public Integer[][] gameBoard;
    public String winner;

    public GameResponse(String activePlayer, Mark[][] gameBoard, String winner) {
        this.activePlayer = activePlayer;
        this.winner = winner;
        
        // The gameboard that we are to return in the response.
        Integer[][] responseGameBoard = new Integer[gameBoard.length][gameBoard[0].length];

        // We convert the gameboard from Marks to Integers.
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                System.out.println("gameboard[" + x + "][" + y + "] is: " + gameBoard[x][y]);
                switch (gameBoard[x][y]) {
                    case EMPTY:
                        responseGameBoard[x][y] = 0; // As expected by GameCanvas.js
                        break;
                    case CIRCLE:
                        responseGameBoard[x][y] = 1; // As expected by GameCanvas.js
                        break;
                    case CROSS:
                        responseGameBoard[x][y] = -1; // As expected by GameCanvas.js
                        break;
                }
            }
        }
        
        this.gameBoard = responseGameBoard;
    }

    public GameResponse() {
        
    }
    
}