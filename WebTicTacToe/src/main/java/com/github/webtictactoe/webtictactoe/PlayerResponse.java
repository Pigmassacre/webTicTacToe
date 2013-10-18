package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CIRCLE;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.CROSS;
import static com.github.webtictactoe.tictactoe.core.Game.Mark.EMPTY;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerResponse {

    public String name;
    public Integer score;
    public Character mark;

    public PlayerResponse(String name, Integer score, Mark mark) {
        this.name = name;
        this.score = score;
        
        Character markAsChar = '\0';
        switch (mark) {
            case CIRCLE:
                markAsChar = 'O'; // a big o (not a 0 or anything silly like that!)
            case CROSS:
                markAsChar = 'X'; // a big x
        }
        
        this.mark = markAsChar;
    }

    public PlayerResponse() {
        
    }
    
}