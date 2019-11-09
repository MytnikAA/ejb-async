package ant.soft.demo.java.ee.beans;

import java.util.concurrent.Future;

public interface Ping {

    String ping();

    void asyncVoidPing();

    Future<String> asyncPing();
}
