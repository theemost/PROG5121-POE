package com.mycompany.quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();
    }

    @Test
    public void testCheckRecipientCell_validNumber() {
        assertTrue(message.checkRecipientCell("+27712345678"));
    }

    @Test
    public void testCheckRecipientCell_invalidNumber() {
        assertFalse(message.checkRecipientCell("0712345678")); // invalid format
    }

    @Test
    public void testCheckMessageId_generatesValidId() {
        assertTrue(message.checkMessageId());
        assertNotNull(message.messageId);
        assertEquals(10, message.messageId.length());
        assertTrue(Pattern.matches("\\d{10}", message.messageId));
    }

    @Test
    public void testCreateMessageHash_shortMessage() {
        message.checkMessageId();
        message.saveMessage("Hi", "+27712345678");
        String hash = message.createMessagehash();
        assertNotNull(hash);
        assertTrue(hash.matches("\\d{2}:\\d+:HI"));
    }

    @Test
    public void testCreateMessageHash_longMessage() {
        message.checkMessageId();
        message.saveMessage("Hello again", "+27712345678");
        String hash = message.createMessagehash();
        assertNotNull(hash);
        assertTrue(hash.matches("\\d{2}:\\d+:HELLOAGAIN"));
    }

    @Test
    public void testSentMessages_send() {
        message.saveMessage("Hello World", "+27712345678");
        message.checkMessageId();
        message.createMessagehash();
        String response = message.sentMessages(1); // send
        assertEquals("Message sent", response);
    }

    @Test
    public void testSentMessages_disregard() {
        message.saveMessage("Skip this", "+27712345678");
        message.checkMessageId();
        message.createMessagehash();
        String response = message.sentMessages(2); // disregard
        assertEquals("Message disregared", response);
    }

    @Test
    public void testSentMessages_store() {
        message.saveMessage("Hold this", "+27712345678");
        message.checkMessageId();
        message.createMessagehash();
        String response = message.sentMessages(3); // store
        assertEquals("Message stored ", response);
    }

    @Test
    public void testReturnTotalMessages_incrementsCorrectly() {
        message.saveMessage("Message 1", "+27712345678");
        message.sentMessages(1);
        message.saveMessage("Message 2", "+27712345678");
        message.sentMessages(1);
        assertEquals(2, message.returnTotalMesssages());
    }

    @Test
    public void testPrintMessages_whenEmpty() {
        assertEquals("No messages sent yet.", message.printMessages());
    }
}
