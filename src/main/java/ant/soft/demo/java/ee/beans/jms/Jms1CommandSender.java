package ant.soft.demo.java.ee.beans.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.logging.*;

/**
 * jndi name java:jboss/activemq/queue/AsyncRequestQueue
 * PhysicalName jms/queue/AsyncRequestQueue
 */
@Stateless
public class Jms1CommandSender implements CommandSender {

    private static final Logger LOGGER = Logger.getLogger(Jms1CommandSender.class.getName());

    @Resource(mappedName = "java:/JmsConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:jboss/activemq/queue/AsyncRequestQueue")
    private Queue commandsQ;

    @Inject
    private JmsSerialization serialization;

    @Override
    public <P extends Serializable> void sendCommand(CommandDto<P> command) {
        try (
                Connection connection = connectionFactory.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
        ) {
            final TextMessage message = session.createTextMessage(serialization.toMessageText(command));
            message.setStringProperty("KIND", "COMMAND");
            message.setStringProperty("CODE", command.getCode());
            session.createProducer(commandsQ)
                    .send(message);
        } catch (JMSException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
        LOGGER.info(String.format("send command: %s", command));
    }
}
