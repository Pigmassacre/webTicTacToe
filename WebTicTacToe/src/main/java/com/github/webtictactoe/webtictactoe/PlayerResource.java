package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import com.github.webtictactoe.tictactoe.core.GameSession;
import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import java.util.HashMap;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/player")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class PlayerResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    // We know the name of the player of the request by the use of cookies!
    //private @CookieParam(value = "name") String name;
    
    @GET
    @Suspend(contentType = "application/json")
    @Path("/{name}")
    public String suspend(@PathParam(value = "name") String name) {
        System.out.println("Suspending response for " + name);
        return "";
    }

}
