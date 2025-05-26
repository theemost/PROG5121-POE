/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RC_Student_lab
 */
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Message {
    private String message;
    private String recipientCell;
    public String messageId;
    public String messageHash;
    private final ArrayList<String> messages = new ArrayList<>();
    private int numMessagesSent;
    private int messageNum;
    
    public void saveMessage(String message){
    messageNum ++;
    this.message = message;
    }
    
    public boolean checkMessageId() {
        long number = ThreadLocalRandom.current().nextLong(
                        1_000_000_000L, 9_999_999_999L
                    );
        this.messageId = Long.toString(number);
        return messageId.length()==10;
    }
    
    public boolean checkRecipientCell(String recipientCell) {
            // Define the pattern (XXX) XXX-XXXX, where X is a digit
        String regex = "^\\+27\\d{2}\\d{3}\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher object to match the input string against the pattern
        Matcher matcher = pattern.matcher(recipientCell);
        if (matcher.matches()) {
            this.recipientCell = recipientCell;
        }
        return matcher.matches();
    }
    public String createMessagehash(){
        String[] messageIdSplit;
        messageIdSplit = this.messageId.split("");
        String[] messageSplit = message.split(" ");
        if (messageSplit.length<2) {
            this.messageHash = messageIdSplit[0] + messageIdSplit[1] +
            ":" + this.messageNum + ":" + messageSplit[0].toUpperCase();
            return messageHash;
        }
        this.messageHash = messageIdSplit[0] + messageIdSplit[1] +
                ":" + this.messageNum + ":" + messageSplit[0].toUpperCase() 
                + messageSplit[messageSplit.length-1].toUpperCase();
        return this.messageHash;
    }   
    
    public String sentMessages(int choice){
        switch (choice) {
            case 1:
                this.numMessagesSent ++;
                this.messages.add(this.numMessagesSent +". " + this.message);
                this.storeMessage();
                return "Message sent";
            case 2:
                return "Message disregared";
            case 3:
                this.storeMessage();
                return"Message stored for in your drafts.";
        }
        return "";
    }
    public String printMessages() {
        int length = this.messages.size();
        if (length == 0) {
            return "No messages sent yet."; 
        }
        else {
            return Arrays.toString(this.messages.toArray());
        }   
    }
    
    public int returnTotalMesssages() {
        return this.messages.size();
    }
    
    public void storeMessage(){
        String msg = "Recipient: " + this.recipientCell + "\n"
                    +"Message ID: " + this.messageId + "\n"
                    +"Message:\n" + this.message;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(messageId + ".json"), msg);
        }
        catch (IOException e){}
    }
}
