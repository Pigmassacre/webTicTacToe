package com.github.webtictactoe.webtictactoe.game;

import com.github.webtictactoe.webtictactoe.lobby.Lobby;
import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;

/**
 * This is the resource where players receive their notifications that
 * a game has been found for them. Players subscribe to this resource
 * and then GameResource.findGame() broadcasts to this resource when
 * a game has been found.
 * @author pigmassacre
 */
@Path("/player")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class PlayerResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    
    @GET
    @Suspend(contentType = "application/json")
    @Path("/{name}")
    public Broadcastable suspend(@PathParam(value = "name") Broadcaster name) {
        System.out.println("Suspending response for /player/" + name.getID());
        return new Broadcastable("", name);
    }
    
    /**
     * A simple REST-method to receive the current score of the player.
     * 
     * NOTE: Not currently used client side... Guess I could remove it, but
     * we did plan on using it, we just didn't have time. It should work just fine!
     * 
     * @param name the name of the player
     * @return information about the player, if there is any
     */
    @GET
    @Produces("application/json")
    @Path("/{name}/info")
    public Response getPlayerScore(@PathParam(value = "name") String name) {
        for (Player player: lobby.getPlayerRegistry().getByName(name)) {
            Integer score = player.getScore();
            return Response
                    .ok()
                    .entity(new PlayerResponse(name, score))
                    .build();
        }
        return Response
                .status(400)
                .build();
    }

    
}
