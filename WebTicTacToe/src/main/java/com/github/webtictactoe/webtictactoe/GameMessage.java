package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

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