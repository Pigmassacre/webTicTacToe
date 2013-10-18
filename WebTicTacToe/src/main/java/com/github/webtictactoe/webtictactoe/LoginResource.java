package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/login/")
public class LoginResource {
    
    private ILobby lobby = Lobby.INSTANCE.getLobby();
    
    /**
     * A REST method that, given a loginMessage (basically a JSON object that looks like this { name: name, password: password })
     * tries to login the Player with the given credentials.
     * 
     * @param loginMessage the JSON object that contains the credentials
     * @return a Jersey response object
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginMessage loginMessage) {
        System.out.println("login() called with data: " + loginMessage.name + " / " + loginMessage.password);
        
        if (!loginMessage.name.isEmpty() && !loginMessage.password.isEmpty()) {
            // As long as the name and password aren't empty, we try to login.
            Boolean success = lobby.login(loginMessage.name, loginMessage.password);
            if(success) {
                return Response
                        .ok()
                        .entity(new LoginResponse("Login succeeded!"))
                        .cookie(new NewCookie("name", loginMessage.name, "/", "", "", -1, false))
                        .build();
            } else {
                return Response
                        .status(400)
                        .entity(new LoginResponse("That password doesn't match that username, try again!"))
                        .build();
            }
        } else {
            // If the name and / or password is empty, the user is denied to login.
            return Response
                    .status(400)
                    .entity(new LoginResponse("Name and / or password cannot be empty!"))
                    .build();
        }
    }
    
    /**
     * A REST method that, given a loginMessage (basically a JSON object that looks like this { name: name, password: password })
     * tries to register a player with the given credentials.
     * 
     * @param loginMessage the JSON object that contains the credentials
     * @return a Jersey response object
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/register")
    public Response register(LoginMessage loginMessage) {
        System.out.println("register() called with data: " + loginMessage.name + " / " + loginMessage.password);
        
        if (!loginMessage.name.isEmpty() && !loginMessage.password.isEmpty()) {
            // We try to register a new user with these credentials instead.
            Boolean success = lobby.register(loginMessage.name, loginMessage.password);

            if (success) {
                lobby.login(loginMessage.name, loginMessage.password);
                return Response
                        .ok()
                        .entity(new LoginResponse("You have been registered!"))
                        .cookie(new NewCookie("name", loginMessage.name, "/", "", "", -1, false))
                        .build();
            } else {
                return Response
                        .status(400)
                        .entity(new LoginResponse("That username is already taken."))
                        .build();
            }
        } else {
            // If the name and / or password is empty, the user is denied registration.
            return Response
                    .status(400)
                    .entity(new LoginResponse("Name and / or password cannot be empty!"))
                    .build();
        }
    }
    
}
