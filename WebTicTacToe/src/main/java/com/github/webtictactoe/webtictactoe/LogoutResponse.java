package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogoutResponse {

    public String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public LogoutResponse() {
        
    }
    
}