package com.github.webtictactoe.webtictactoe.game;

import com.github.webtictactoe.tictactoe.core.GameSession;
import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import com.github.webtictactoe.webtictactoe.lobby.Lobby;
import java.util.HashMap;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
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
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.jersey.Broadcastable;

/**
 * This is the resource class that takes care of all things GameSession related.
 * It maps UUID's to GameSession objects, and allows the client-side to basically
 * play the game.
 * 
 * The idea is basically that every client/player subscribes to an Atmosphere Broadcaster
 * with the id "/player/{name}", where {name} is the name of that player. The findGame
 * method when called will then, if successful, broadcast an UUIDResponse to the
 * relevant clients.
 * 
 * Now, when each client has the same UUID, they can use that UUID to call the makeGameMove method.
 * This method takes care of handling the GameSession matching the UUID, and then broadcasts
 * the result of the move (if the move was permitted) to the two clients in the GameSession.
 * 
 * @author pigmassacre
 */
@Path("/game")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class GameResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    
    // We use this to map UUID's to gameSessions, so we know what gameSession to handle depending on the UUID given
    // in a request.
    private static HashMap<String, GameSession> gameSessionMap = new HashMap<String, GameSession>();
    
    // We know the name of the player of the request by the use of cookies!
    private @CookieParam(value = "name") String name;
    
    /**
     * Suspends a client to the broadcaster with id 'id'.
     * @param id
     * @return 
     */
    @GET
    @Suspend(contentType = "application/json")
    @Path("/{id}")
    public Broadcastable suspend(@PathParam(value = "id") Broadcaster id) {
        if (gameSessionMap.containsKey(id.getID())) {
            System.out.println("Given UUID matches a gamesession, request is OK");
        } else {
            System.out.println("Given UUID does NOT match a gamesession!");
        }
        
        System.out.println("Anyway, suspending response for " + name);
        return new Broadcastable("", id);
    }
    
    /**
     * Takes the first player that matches the name that is in the given cookie,
     * and calls the findGame method in the Lobby class in the model to create
     * a GameSession with that player and another player.
     * 
     * A random UUID is then generated, which is mapped to the new GameSession.
     * This UUID is then broadcast to /player/{name}, where {name} is the name
     * of either player.
     * 
     * Clients must make sure to subscribe to /player/{name} in order to get the UUID which
     * can then be used to communicate with the new GameSession.
     * 
     * @param size the size of the gameboard to be created
     * @return a UUID that can be used to communicate with the matching gamesession
     */
    @POST
    @Path("/findgame/{size}")
    public Response findGame(@PathParam(value = "size") Integer size) {
        System.out.println("Trying to find a game for " + name + " of size " + size);
        
        // We get the first player matching the name (there should only ever be one anyway).
        for (Player player : lobby.getPlayerRegistry().getByName(name)) {
            Player givenPlayer = player;
            
            GameSession gameSession = lobby.findGame(givenPlayer, size);
            if (gameSession != null) {
                // Map the new gamesession to the new uuid.
                UUID uuid = UUID.randomUUID();
                gameSessionMap.put(uuid.toString(), gameSession);
                
                System.out.println("Found game for " + gameSession.getPlayerOne() + " and " + gameSession.getPlayerTwo());

                // Broadcast the UUID to the suspended requests that match both players names.
                BroadcasterFactory.getDefault()
                        .lookup(gameSession.getPlayerOne().getName())
                        .broadcast(new UUIDResponse(uuid.toString(), size, gameSession.getActivePlayer().getName()));
                BroadcasterFactory.getDefault()
                        .lookup(gameSession.getPlayerTwo().getName())
                        .broadcast(new UUIDResponse(uuid.toString(), size, gameSession.getActivePlayer().getName()));
                
                System.out.println("Game found, returning statuscode 200.");
                return Response.ok().build();
            }
        }
        // Player matching that name not found.
        System.out.println("Game not found, returning statuscode 400.");
        return Response.status(400).build();
    }
    
    /**
     * A REST method that allows a client to make a game move on a GameSession.
     * If the move is accepted, it broadcasts the state of the game using the Broadcaster
     * given by the id PathParam.
     * 
     * Returns HTTP status code 200 if the request is OK (i.e. the player name matches
     * the player who's turn it is, and the x and y-positions match an empty state of the board).
     * 
     * Otherwise, returns HTTP status code 400.
     * 
     * @param id the Broadcaster to be used to broadcast the state of the game
     * @param gameMessage a JSON object marshaled into a GameMessage object that contains
     * the data with which to make a move with
     * @return a Response object (Jersey)
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/{id}")
    public Response makeGameMove(@PathParam(value = "id") Broadcaster id, GameMessage gameMessage) {
        GameSession gameSession = gameSessionMap.get(id.getID());
        
        System.out.println("For game with UUID " + id.getID() + ", got a gameMessage: " + gameMessage);
        
        if (gameSession == null) {
            System.out.println("This UUID does not match any gameSession.");
            return Response.status(400).build();
        }
        
        if (gameSession.getWinner() != null) {
            System.out.println("A winner has already been decided.");
            return Response.status(400).build();
        }
        
        // We get the first player matching the name (there should only ever be one anyway).
        for (Player player : lobby.getPlayerRegistry().getByName(name)) {
            Player givenPlayer = player;
            
            System.out.println("Mark of " + givenPlayer.getName() + " is: " + gameSession.getMarkForPlayer(givenPlayer));
            
            // Try to make a move, if successful, we convert the new gameboard to a response-friendly format.
            Boolean successfulMove = gameSession.move(gameMessage.xPos, gameMessage.yPos, givenPlayer);
            if (successfulMove) {
                // Broadcast the name of the active player, the state of the board and, if available, the name of the winner.
                if (gameSession.getWinner() == null) {
                    id.broadcast(new GameResponse(gameSession.getActivePlayer().getName(), gameSession.getBoard()));
                } else {
                    System.out.println(gameSession.getWinner().getName() + " has won! Shutting down this gameSession and broadcaster.");
                    id.broadcast(new GameResponse(gameSession.getActivePlayer().getName(), gameSession.getBoard(), gameSession.getWinner().getName()));
                    
                    // The GameSession matching this UUID is over, so we remove it from the gameSessionMap.
                    gameSessionMap.remove(id.getID());
                }
                
                System.out.println("Game move was accepted, returning statuscode 200.");
                return Response.ok().build();
            }
        }
        
        // Move wasn't successful or player wasn't found.
        System.out.println("Game move was not accepted, returning statuscode 400.");
        return Response.status(400).build();
    }
    
}
