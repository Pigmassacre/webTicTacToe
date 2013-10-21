package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the message that the server gets from a client that tries to make a game move.
 * @author pigmassacre
 */
@XmlRootElement
public class GameMessage {

    public Integer xPos;
    public Integer yPos;

    public GameMessage(Integer xPos, Integer yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public GameMessage() {
        
    }
    
}