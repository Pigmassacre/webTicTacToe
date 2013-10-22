package com.github.webtictactoe.webtictactoe.lobby;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the response the server sends to a client that tries to log out.
 * @author pigmassacre
 */
@XmlRootElement
public class LogoutResponse {

    public String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public LogoutResponse() {
        
    }
    
}