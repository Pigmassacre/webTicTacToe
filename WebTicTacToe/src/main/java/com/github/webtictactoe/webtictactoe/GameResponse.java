package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CIRCLE;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CROSS;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.EMPTY;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameResponse {

    public String name;
    // Yes we use an enum in the model, but here a simple X for cross and O for circle will suffice, no?
    // X as in big x, O as in big o.
    public Character[][] gameBoard;
    public Boolean isGameWon;

    public GameResponse(String name, Mark[][] gameBoard, Boolean isGameWon) {
        this.name = name;
        this.isGameWon = isGameWon;
        
        // The gameboard that we are to return in the response.
        Character[][] responseGameBoard = new Character[gameBoard.length][gameBoard[0].length];

        // We loop through the gameboard and converts all Mark enums to string representations.
        // Could possibly do this in a smarter way...
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                switch (gameBoard[x][y]) {
                    case EMPTY:
                        responseGameBoard[x][y] = '\0'; // the null character
                        break;
                    case CIRCLE:
                        responseGameBoard[x][y] = 'O'; // a big o (not a 0 or anything silly like that!)
                        break;
                    case CROSS:
                        responseGameBoard[x][y] = 'X'; // a big x
                        break;
                }
            }
        }
        
        this.gameBoard = responseGameBoard;
    }

    public GameResponse() {
        
    }
    
}