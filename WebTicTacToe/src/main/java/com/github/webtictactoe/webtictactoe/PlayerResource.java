package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;

@Path("/player")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class PlayerResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    
    @GET
    @Suspend(contentType = "application/json")
    @Path("/{name}")
    public Broadcastable suspend(@PathParam(value = "name") Broadcaster name) {
        System.out.println("Suspending response for /player/" + name);
        System.out.println("Broadcaster ID is: " + name.getID());
        return new Broadcastable("", name);
    }
    
    @GET
    @Produces("application/json")
    @Path("/{name}/info")
    public Response getPlayerInformation(@PathParam(value = "name") String name) {
        for (Player player: lobby.getPlayerRegistry().getByName(name)) {
            Integer score = player.getScore();
            Mark mark = player.getMark();
            return Response
                    .ok()
                    .entity(new PlayerResponse(name, score, mark))
                    .build();
        }
        return Response
                .status(400)
                .build();
    }

    
}
