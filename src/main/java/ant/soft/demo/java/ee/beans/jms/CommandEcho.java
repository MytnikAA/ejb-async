package ant.soft.demo.java.ee.beans.jms;

import javax.json.bind.annotation.*;

public class CommandEcho extends CommandDto<String> {

    private static final long serialVersionUID = 5849554666331783039L;

    @JsonbCreator
    public CommandEcho(@JsonbProperty("params") String params) {
        super("ECHO", params);
    }


}
