package com.github.webtictactoe.webtictactoe.game;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the response that the server broadcasts to the players that have been
 * matched together to play a game of tictactoe.
 * @author pigmassacre
 */
@XmlRootElement
public class UUIDResponse {

    public String uuid;
    public Integer size;
    public String startingPlayerName;

    public UUIDResponse(String uuid, Integer size, String startingPlayerName) {
        this.uuid = uuid;
        this.size = size;
        this.startingPlayerName = startingPlayerName;
    }

    public UUIDResponse() {
        
    }
    
}