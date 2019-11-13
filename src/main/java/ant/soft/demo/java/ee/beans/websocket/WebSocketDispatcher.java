package ant.soft.demo.java.ee.beans.websocket;

import javax.inject.Singleton;
import javax.websocket.Session;
import java.util.*;

@Singleton
public class WebSocketDispatcher {

    private Map<User, Session> userSessions = new java.util.concurrent.ConcurrentHashMap<>();
    private Map<String, User> users = new java.util.concurrent.ConcurrentHashMap<>();

    public class User {

        private final String id;

        private final WebSocketDispatcher dispatcher;

        User(String id) {
            this.id = id;
            this.dispatcher = WebSocketDispatcher.this;
        }

        public void disconnect() {
            this.dispatcher.disconnect(this);
        }

        public void send(String message) {
            this.dispatcher.send(this, message);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    public void connect(String userId, Session session) {
        final User user = new User(userId);
        userSessions.put(user, session);
        users.put(userId, user);
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public void send(User to, String message) {
        final Session session = userSessions.get(to);
        if (session != null) {
            if (!session.isOpen()) {
                userSessions.remove(to);
                users.remove(to.id);
            } else {
                session.getAsyncRemote()
                        .sendText(message);
            }
        }
    }

    public void disconnect(User user) {
        users.remove(user.id);
        userSessions.remove(user);
    }

}
