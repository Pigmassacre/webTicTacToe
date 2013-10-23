package com.github.webtictactoe.webtictactoe.game;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the response sent by the server to whoever calls PlayerResource.getPlayerScore().
 * @author pigmassacre
 */
@XmlRootElement
public class PlayerResponse {

    public String name;
    public Integer score;
    
    public PlayerResponse(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    public PlayerResponse() {
        
    }
    
}