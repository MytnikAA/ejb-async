package ant.soft.demo.java.ee.web.socket;

import ant.soft.demo.java.ee.beans.websocket.WebSocketDispatcher;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.*;
import java.util.logging.Logger;

@ServerEndpoint("/websocket/{uid}")
public class ReverseCommandSocket {

    private static final Logger LOGGER = Logger.getLogger(ReverseCommandSocket.class.getName());

    @Inject
    private WebSocketDispatcher dispatcher;

    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String userId) {
        LOGGER.info(String.format("open ws %s", session.getId()));
        doIfUserId(userId, () -> dispatcher.connect(userId, session));
    }

    @OnClose
    public void onClose(Session session, @PathParam("uid") String userId) {
        LOGGER.info(String.format("close ws %s", session.getId()));
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
