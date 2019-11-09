package ant.soft.demo.java.ee.beans;

import javax.annotation.*;
import javax.ejb.Startup;
import javax.inject.Singleton;
import java.util.UUID;

@Startup
@Singleton
public class AppInstance {

    private String id;

    @PostConstruct
    public void init() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
