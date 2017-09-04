package com.github.wzomer.websocketjwt.auth;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;

@Path("/auth")
@Consumes(APPLICATION_JSON)
public class AuthResource {

    @Inject
    private TokenService tokenService;

    @POST
    public Response auth(final UserDto userDto) {
        if (userDto.getUserId().equals("wellington") && userDto.getPassword().equals("senhadificil")) {
            return Response.ok(tokenService.getJWT(userDto.getUserId())).build();
        }

        return Response.status(FORBIDDEN).build();
    }
}
