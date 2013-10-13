package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LogoutResponse {

    public String message;
    public Boolean success;

    public LogoutResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public LogoutResponse() {
        
    }
    
}