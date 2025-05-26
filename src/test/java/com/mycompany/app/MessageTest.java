/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.app;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author RC_Student_lab
 */
public class MessageTest {
    private String message;
    private String recipientCell;
    public String messageId;
    public String messageHash;
    private final ArrayList<String> messages = new ArrayList<>();
    private int numMessagesSent;
    private int messageNum;
    /**
     * Test of saveMessage method, of class Message.
     */
    @Test
    public void testSaveMessage() {
        System.out.println("saveMessage");
        String message = "";
        Message instance = new Message();
        instance.saveMessage(message);
    }

    /**
     * Test of checkMessageId method, of class Message.
     */
    @Test
    public void testCheckMessageId() {
        System.out.println("checkMessageId");
        Message instance = new Message();
        boolean expResult = true;
        boolean result = instance.checkMessageId();
        assertEquals(expResult, result);
        }

    /**
     * Test of checkRecipientCell method, of class Message.
     */
    @Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");
        String recipientCell = "";
        Message instance = new Message();
        boolean expResult = false;
        boolean result = instance.checkRecipientCell(recipientCell);
        assertEquals(expResult, result);
       }

    /**
     * Test of createMessagehash method, of class Message.
     */
    @Test
    public void testCreateMessagehash() {
        System.out.println("createMessagehash");
        Message instance = new Message();
        instance.checkMessageId();
        instance.saveMessage("Hello World");
        String expResult = instance.createMessagehash();
        String result = instance.createMessagehash();
        assertEquals(expResult, result);
    }

    /**
     * Test of sentMessages method, of class Message.
     */
    @Test
    public void testSentMessages() {
        System.out.println("sentMessages");
        int choice = 1;
        Message instance = new Message();
        String expResult = "Message sent";
        String result = instance.sentMessages(choice);
        assertEquals(expResult, result);
    }

    /**
     * Test of printMessages method, of class Message.
     */
    @Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        Message instance = new Message();
        String expResult = "No messages sent yet.";
        String result = instance.printMessages();
        assertEquals(expResult, result);
    }

    /**
     * Test of returnTotalMesssages method, of class Message.
     */
    @Test
    public void testReturnTotalMesssages() {
        System.out.println("returnTotalMesssages");
        Message instance = new Message();
        int expResult = 0;
        int result = instance.returnTotalMesssages();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of storeMessage method, of class Message.
     */
    @Test
    public void testStoreMessage() {
        System.out.println("storeMessage");
        Message instance = new Message();
        instance.storeMessage();
    }
    
}
