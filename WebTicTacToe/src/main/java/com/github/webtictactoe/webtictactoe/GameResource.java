package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.GameSession;
import com.github.webtictactoe.tictactoe.core.ILobby;
import java.util.HashMap;
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
import org.atmosphere.cpr.Broadcaster;

@Path("/game/{id}")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class GameResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    private static HashMap<String, GameSession> gameSessionMap = new HashMap<String, GameSession>();
    private @PathParam(value = "id") String id;
    private @CookieParam(value = "name") String name;
    
    @GET
    @Suspend(contentType = "application/json")
    public String suspend() {
        if (gameSessionMap.containsKey(id)) {
            
        }
        System.out.println("Suspending response for " + name);
        return "";
    }
    
    @POST
    @Broadcast(writeEntity = false)
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/move")
    public GameResponse broadcastGamestate(GameMessage gameMessage) {
        return new GameResponse();
    }
    
}
