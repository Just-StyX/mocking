package jsl.com.mocking.subpub;

import java.util.ArrayList;
import java.util.List;

public class Sender {
    private final List<Receiver> receivers = new ArrayList<>();

    public void send(String message) {
        for (Receiver receiver: receivers) {
            try {
                receiver.received(message);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public void addReceiver(Receiver receiver) {
        receivers.add(receiver);
    }
}
