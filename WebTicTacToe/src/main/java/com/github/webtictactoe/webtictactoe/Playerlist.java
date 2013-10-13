package com.github.webtictactoe.webtictactoe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Playerlist {

    public List<String> names;

    public Playerlist() {
        this.names = new ArrayList<String>();
    }
    
    public void addUsername(String name) {
        this.names.add(name);
    }

}