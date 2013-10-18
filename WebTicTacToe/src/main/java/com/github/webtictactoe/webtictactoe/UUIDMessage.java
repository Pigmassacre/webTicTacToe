package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UUIDMessage {

    public String uuid;
    public Integer size;

    public UUIDMessage(String uuid, Integer size) {
        this.uuid = uuid;
        this.size = size;
    }

    public UUIDMessage() {
        
    }
    
}