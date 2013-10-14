package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameMessage {

    public String xPos;
    public String yPos;

    public GameMessage(String xPos, String yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public GameMessage() {
        
    }
    
}