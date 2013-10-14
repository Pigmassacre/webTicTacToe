package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import java.util.HashMap;
import java.util.List;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;

@Path("/game/{id}")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class GameResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    private @PathParam(value = "id") Integer id;
    private @Context AtmosphereResource resource;
    // This is used to map X-Atmosphere-tracking-id's to usernames.
    //private static HashMap<String, String> idMap = new HashMap<String, String>();
    
    
    @GET
    @Suspend(contentType = "application/json")
    //@Path("/playerlist")
    public String suspend() {
        System.out.println("!");
        return "";
    }
    
    @POST
    @Broadcast(writeEntity = false)
    @Produces("application/json")
    //@Path("/playerlist")
    public String broadcastPlayerlist() {
        return "";
    }
    
}
