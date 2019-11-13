package ant.soft.demo.java.ee.web.rest;

import ant.soft.demo.java.ee.beans.Ping;
import ant.soft.demo.java.ee.beans.jms.*;
import ant.soft.demo.java.ee.beans.websocket.EchoBean;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

@Path("")
public class RootRest {

    private static final Logger LOGGER = Logger.getLogger(RootRest.class.getName());

    @Inject
    private Ping pinger;

    @Inject
    private CommandSender commandSender;

    @Inject
    private EchoBean echoBean;

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        LOGGER.info("start ping");
        return pinger.ping();
    }

    @GET
    @Path("asyncVoidPing")
    public Response asyncVoidPing() {
        pinger.asyncVoidPing();
        return Response.ok().build();
    }

    @GET
    @Path("asyncPing")
    @Produces(MediaType.TEXT_PLAIN)
    public void asyncPing(@Suspended final AsyncResponse asyncResponse) throws ExecutionException, InterruptedException {
        asyncResponse.setTimeout(30, TimeUnit.SECONDS);
        final Future<String> future = pinger.asyncPing();
        asyncResponse.resume(future.get());
    }

    @POST
    @Path("log")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response log(CommandEcho payload) {
        commandSender.sendCommand(payload);
        return Response.ok().build();
    }

    @POST
    @Path("echo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response echo(CommandEcho payload, @CookieParam("uid") String userId) {
        echoBean.echo(payload, userId);
        return Response.ok().build();
    }

}
