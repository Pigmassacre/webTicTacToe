package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Since we only need the name to logout, there's no point in reusing LoginMessage for logout purposes.
 * 
 * @author pigmassacre
 */
@XmlRootElement
public class LogoutMessage {

    public String name;

    public LogoutMessage(String name) {
        this.name = name;
    }

    public LogoutMessage() {
    }
}