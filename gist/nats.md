```java
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Nats;
import io.nats.client.Options;

public class NatsExample {

    public static void main(String[] args) {
        try {
            // Connect to the NATS server
            Connection nc = Nats.connect(new Options.Builder().server("nats://localhost:4222").build());

            // Subscribe to a subject
            subscribeToSubject(nc, "example.subject");

            // Publish a message to the subject
            publishToSubject(nc, "example.subject", "Hello, NATS!");

            // Close the connection
            nc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void subscribeToSubject(Connection nc, String subject) {
        Dispatcher dispatcher = nc.createDispatcher((msg) -> {
            String receivedMessage = new String(msg.getData());
            System.out.println("Received message: " + receivedMessage);
        });

        dispatcher.subscribe(subject);
        System.out.println("Subscribed to subject: " + subject);
    }

    private static void publishToSubject(Connection nc, String subject, String message) {
        nc.publish(subject, message.getBytes());
        System.out.println("Published message: " + message + " to subject: " + subject);
    }
}
```
