package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameMessage {

    public String name;
    public String xPos;
    public String yPos;

    public GameMessage(String name, String xPos, String yPos) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public GameMessage() {
        
    }
    
}