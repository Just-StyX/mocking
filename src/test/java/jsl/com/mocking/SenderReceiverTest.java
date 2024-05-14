package jsl.com.mocking;

import jsl.com.mocking.subpub.Receiver;
import jsl.com.mocking.subpub.Sender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SenderReceiverTest {
    private final Sender sender = new Sender();
    private final Receiver subscriber1 = mock(Receiver.class);
    private final Receiver subscriber2 = mock(Receiver.class);

    @BeforeEach
    void setSender() {
        sender.addReceiver(subscriber1);
        sender.addReceiver(subscriber2);
    }

    @Test
    @DisplayName("Testing void return methods")
    void testSendOfSender() {
        sender.send("Article one sending ...");

        verify(subscriber1).received("Article one sending ...");
        verify(subscriber2).received("Article one sending ...");
    }

    @Test
    @DisplayName("Using matches in testing void return methods")
    void testMultipleSendOfSender() {
        sender.send("Article 1 sending ...");
        sender.send("Article 2 sending ...");

        verify(subscriber1, times(2)).received(anyString());
        verify(subscriber2, times(2)).received(argThat(s -> s.matches("Article \\d sending ...")));
    }
}
