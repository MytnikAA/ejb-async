package ant.soft.demo.java.ee.beans.jms;

import ant.soft.demo.java.ee.beans.AppInstance;

import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.*;
import java.util.Objects;
import java.util.logging.*;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/AsyncRequestQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
}
)
public class CommandListener implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(CommandListener.class.getName());

    @Inject
    private AppInstance instance;

    @Inject
    private JmsSerialization serialization;

    @Override
    public void onMessage(Message message) {
        //TODO remove handling from listener
        try {
            if (message instanceof TextMessage) {
                final TextMessage textMessage = (TextMessage) message;
                LOGGER.info(instance.getId() + " received " + textMessage.getText());
                final String kind = Objects.requireNonNull(message.getStringProperty("KIND"),
                        "property KIND is required for AsyncRequestQueue");
                LOGGER.info(String.format("message kind %s", kind));
                LOGGER.info(String.format("message text: %s", textMessage.getText()));
                if (kind.equalsIgnoreCase("COMMAND")) {
                    final String commandCode = Objects.requireNonNull(message.getStringProperty("CODE"),
                            "property CODE is required for AsyncRequestQueue COMMAND kind");
                    switch (commandCode) {
                        case "ECHO":
                            final CommandEcho echo = serialization.fromMessageText(textMessage.getText(), CommandEcho.class);
                            final String echoText = echo.getParams();
                            LOGGER.info(String.format("echo %s", echoText));
                            break;
                        default:
                            throw new IllegalArgumentException(String.format("unsupported command code: %s", kind));
                    }
                }
            }
        } catch (Throwable error) {
            LOGGER.log(Level.WARNING, error.getMessage(), error);
        }
    }
}
