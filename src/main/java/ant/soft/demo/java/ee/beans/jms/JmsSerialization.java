package ant.soft.demo.java.ee.beans.jms;

import javax.json.bind.JsonbBuilder;
import java.io.Serializable;

/**
 * ActiveMQ нужно настраивать для обмена ObjectMessage.
 * http://activemq.apache.org/objectmessage
 * Или же пересылать текстом в формате, например, JSON.
 */
public class JmsSerialization {

    public String toMessageText(Serializable obj) {
        return JsonbBuilder.create().toJson(obj);
    }

    public <T extends Serializable> T fromMessageText(String text, Class<T> type) {
        return JsonbBuilder.create().fromJson(text, type);
    }

}
