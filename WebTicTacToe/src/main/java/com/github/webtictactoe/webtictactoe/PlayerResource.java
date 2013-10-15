package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/player")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class PlayerResource {
    
    @GET
    @Suspend(contentType = "application/json")
    @Path("/{name}")
    public String suspend(@PathParam(value = "name") String name) {
        System.out.println("Suspending response for /player/" + name);
        return "";
    }

}
