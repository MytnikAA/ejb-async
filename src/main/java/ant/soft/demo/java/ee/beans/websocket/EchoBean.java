package ant.soft.demo.java.ee.beans.websocket;

import ant.soft.demo.java.ee.beans.jms.CommandEcho;

import javax.ejb.*;
import javax.inject.Inject;
import java.util.logging.*;

@Stateless
public class EchoBean {

    @Inject
    private WebSocketDispatcher webSocketDispatcher;

    @Asynchronous
    public void echo(CommandEcho payload, String userId) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        webSocketDispatcher.findUserById(userId).send(payload.getParams());
    }
}
