package ant.soft.demo.java.ee.web.socket;

import ant.soft.demo.java.ee.beans.websocket.WebSocketDispatcher;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.CookieParam;
import java.util.logging.Logger;

@ServerEndpoint("/")
public class ReverseCommandSocket {

    private static final Logger LOGGER = Logger.getLogger(ReverseCommandSocket.class.getName());

    @Inject
    private WebSocketDispatcher dispatcher;

    @OnOpen
    public void onOpen(Session session, @CookieParam("uid") String userId) {
        doIfUserId(userId, () -> dispatcher.connect(userId, session));
    }

    @OnClose
    public void onClose(@CookieParam("uid") String userId) {
        doIfUserId(userId, () -> dispatcher.findUserById(userId).disconnect());
    }


    private void doIfUserId(String userId, Runnable action) {
        if (userId != null && !userId.isBlank()) {
            action.run();
        } else {
            LOGGER.info("userId is blank");
        }
    }
}
