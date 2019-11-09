package ant.soft.demo.java.ee.beans;

import javax.ejb.*;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@Stateless
public class PingBean implements Ping {

    private static final Logger LOGGER = Logger.getLogger(PingBean.class.getName());

    @Override
    public String ping() {
        return "pong";
    }

    @Override
    @Asynchronous
    public void asyncVoidPing() {
        final long startTime = System.currentTimeMillis();
        LOGGER.info("start asyncVoidPing");
        bruteForce();
        final var stopTime = System.currentTimeMillis() - startTime;
        LOGGER.info(String.format("done asyncVoidPing with time: %d", stopTime));
    }

    @Override
    @Asynchronous
    public Future<String> asyncPing() {
        final long startTime = System.currentTimeMillis();
        LOGGER.info("start asyncVoidPing");
        bruteForce();
        final var stopTime = System.currentTimeMillis() - startTime;
        LOGGER.info(String.format("done asyncVoidPing with time: %d", stopTime));
        return new AsyncResult(String.format("pong - %d", stopTime));
    }

    private void bruteForce() {
        final var randoms = new Random();
        while (true) {
            if (randoms.nextInt(100000000) == 500) {
                break;
            }
        }
    }
}
