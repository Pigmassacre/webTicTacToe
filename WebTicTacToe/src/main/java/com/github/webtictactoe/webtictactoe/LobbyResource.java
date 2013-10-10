/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.webtictactoe;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.SuspendResponse;

/**
 * Testing...
 * 
 * @author pigmassacre
 */

@Path("/{topic}")
@Produces("application/json")
public class LobbyResource {
    
    private @PathParam("topic") Broadcaster topic;
    
    @GET
    public SuspendResponse<String> subscribe() {
        return new SuspendResponse
                .SuspendResponseBuilder<String>()
                .broadcaster(topic)
                .outputComments(true)
                .build();
    }
    
    @POST
    @Broadcast
    @Produces("text/html;charset=ISO-8859-1")
    public Broadcastable publish(@FormParam("message") String message) {
        return new Broadcastable(message, "", topic);
    }
    
}
