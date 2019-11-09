package ant.soft.demo.java.ee.beans.jms;

import java.io.Serializable;

public interface CommandSender {

    <P extends Serializable> void sendCommand(CommandDto<P> command);
}
