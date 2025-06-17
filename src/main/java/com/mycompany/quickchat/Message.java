/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author wicke
 */
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.regex.*;
import java.util.concurrent.ThreadLocalRandom; //for random long number
import java.util.ArrayList;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Message {
    private String message;
    private String recipientCell;
    public String messageId;
    public String messageHash;
    private String senderCell;
    private final ArrayList<String> messages = new ArrayList<>();
    private int numMessagesSent;
    private int messageNum;
    private final ArrayList<String> messageIds = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> disregardedMessages  = new ArrayList<>();
    private final ArrayList<String> sentMessages = new ArrayList<>();
    private final ArrayList<String> storedMessages = new ArrayList<>();
    private final ArrayList<String> recipients = new ArrayList<>();
    private final ArrayList<String> senders = new ArrayList<>();
    
    public void saveMessage(String message, String cellNum){
    messageNum ++;
    this.senderCell = cellNum;
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
                storeMessage(choice);
                return "Message sent";
            case 2:
                storeMessage(choice);
                return "Message disregared";
            case 3:
                storeMessage(choice);
                return"Message stored ";
        }
        return "";
    }
    public String printMessages() {
        int length = this.sentMessages.size();
        if (length == 0) {
            return "No messages sent yet.";
        }
        else {
            return Arrays.toString(this.sentMessages.toArray());
        } 
    }
    
    public int returnTotalMesssages() {
        return this.messages.size();
    }
    
    public void storeMessage(int choice){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        switch (choice) {
            case 1:
                {
                    File file = new File("Sent Messages.json");
                    List<Map<String, Map<String, Object>>> dataList;
                    try {
                        if (file.exists() && file.length() > 0) {
                            JsonNode root = mapper.readTree(file);
                            
                            if (root.isArray()) {
                                dataList = mapper.convertValue(root, new TypeReference<List<Map<String, Map<String, Object>>>>() {});
                            } else if (root.isObject()) {
                                Map<String, Map<String, Object>> singleEntry =
                                        mapper.convertValue(root, new TypeReference<Map<String, Map<String, Object>>>() {});
                                dataList = new ArrayList<>();
                                dataList.add(singleEntry);
                            } else {
                                dataList = new ArrayList<>();
                            }
                        } else {
                            dataList = new ArrayList<>();
                        }
                        
                        // Create message data
                        Map<String, Object> messageDetails = new HashMap<>();
                        messageDetails.put("Sender", this.senderCell);
                        messageDetails.put("Recipient", this.recipientCell);
                        messageDetails.put("Message", this.message);
                        messageDetails.put("Message Hash", this.messageHash);
                                                
                        Map<String, Map<String, Object>> messageData = new HashMap<>();
                        messageData.put(this.messageId, messageDetails);
                        
                        dataList.add(messageData);
                        
                        mapper.writeValue(file, dataList);
                        System.out.println("Data appended to JSON file.");
                    }
                    catch (IOException e) {
                        System.err.println("Error appending to JSON file: " + e.getMessage());
                    }       break;
                }
            case 2:
                {
                    File file = new File("Disregarded Messages.json");
                    List<Map<String, Map<String, Object>>> dataList;
                    try {
                        if (file.exists() && file.length() > 0) {
                            JsonNode root = mapper.readTree(file);
                            
                            if (root.isArray()) {
                                dataList = mapper.convertValue(root, new TypeReference<List<Map<String, Map<String, Object>>>>() {});
                            } else if (root.isObject()) {
                                Map<String, Map<String, Object>> singleEntry =
                                        mapper.convertValue(root, new TypeReference<Map<String, Map<String, Object>>>() {});
                                dataList = new ArrayList<>();
                                dataList.add(singleEntry);
                            } else {
                                dataList = new ArrayList<>();
                            }
                        } else {
                            dataList = new ArrayList<>();
                        }
                        
                        // Create message data
                        Map<String, Object> messageDetails = new HashMap<>();
                        messageDetails.put("Sender", this.senderCell);
                        messageDetails.put("Recipient", this.recipientCell);
                        messageDetails.put("Message", this.message);
                        messageDetails.put("Message Hash", this.messageHash);
                        
                        Map<String, Map<String, Object>> messageData = new HashMap<>();
                        messageData.put(this.messageId, messageDetails);
                        
                        dataList.add(messageData);
                        
                        mapper.writeValue(file, dataList);
                        System.out.println("Data appended to JSON file.");
                    }
                    catch (IOException e) {
                        System.err.println("Error appending to JSON file: " + e.getMessage());
                    }           break;
                }
            case 3:
                {
                    File file = new File("Stored Messages.json");
                    List<Map<String, Map<String, Object>>> dataList;
                    try {
                        if (file.exists() && file.length() > 0) {
                            JsonNode root = mapper.readTree(file);
                            
                            if (root.isArray()) {
                                dataList = mapper.convertValue(root, new TypeReference<List<Map<String, Map<String, Object>>>>() {});
                            } else if (root.isObject()) {
                                Map<String, Map<String, Object>> singleEntry =
                                        mapper.convertValue(root, new TypeReference<Map<String, Map<String, Object>>>() {});
                                dataList = new ArrayList<>();
                                dataList.add(singleEntry);
                            } else {
                                dataList = new ArrayList<>();
                            }
                        } else {
                            dataList = new ArrayList<>();
                        }
                        
                        // Create message data
                        Map<String, Object> messageDetails = new HashMap<>();
                        messageDetails.put("Sender", this.senderCell);
                        messageDetails.put("Recipient", this.recipientCell);
                        messageDetails.put("Message", this.message);
                        messageDetails.put("Message Hash", this.messageHash);
                        
                        Map<String, Map<String, Object>> messageData = new HashMap<>();
                        messageData.put(this.messageId, messageDetails);
                        
                        dataList.add(messageData);
                        
                        mapper.writeValue(file, dataList);
                        System.out.println("Data appended to JSON file.");
                    }
                    catch (IOException e) {
                        System.err.println("Error appending to JSON file: " + e.getMessage());
                    }           break;
                }
            default:
                break;
        }
    }
    
    //read json files and saves data to arrays
    private void returnMessages(int choice){
        //read json and use key-value pairs
        ObjectMapper mapper = new ObjectMapper();
        switch (choice) {
            case 1:
                try {
                    // Read as a list of maps
                    if (new File("Sent Messages.json").exists()){
                        List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                                new File("Sent Messages.json"),
                                new TypeReference<List<Map<String, Map<String, Object>>>>() {}
                        );
                        // Loop over the list
                        for (Map<String, Map<String, Object>> messageEntry : messageList) {
                            for (String messageId : messageEntry.keySet()) {
                                this.messageIds.add(messageId);
                                Map<String, Object> details = messageEntry.get(messageId);
                                for (String key : details.keySet()) {
                                    switch (key) {
                                        case "Message Hash":
                                            this.messageHashes.add((String) details.get(key));
                                            break;
                                        case "Message":
                                            this.sentMessages.add((String) details.get(key));
                                            break;
                                        case "Recipient":
                                            this.recipients.add((String) details.get(key));
                                            break;
                                        case "Sender":
                                            this.senders.add((String) details.get(key));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("");
                    }
                }
                catch (IOException e) {
                    System.err.println("Error reading JSON list: " + e.getMessage());
                }   break;
            case 2:
                try {
                    //Check if file exists
                    if (new File("Disregarded Messages.json").exists()){
                        // Read as a list of maps
                        List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                                new File("Disregarded Messages.json"),
                                new TypeReference<List<Map<String, Map<String, Object>>>>() {}
                        );
                        // Loop over the list
                        for (Map<String, Map<String, Object>> messageEntry : messageList) {
                            for (String messageId : messageEntry.keySet()) {
                                this.messageIds.add(messageId);
                                Map<String, Object> details = messageEntry.get(messageId);
                                for (String key : details.keySet()) {
                                    switch (key) {
                                        case "Message Hash":
                                            this.messageHashes.add(key);
                                            break;
                                        case "Message":
                                            this.disregardedMessages.add((String) details.get(key));
                                            break;
                                        case "Recipient":
                                            this.recipients.add((String) details.get(key));
                                            break;
                                        case "Sender":
                                            this.senders.add((String) details.get(key));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("");
                    }
                }
                catch (IOException e) {
                    System.err.println("Error reading JSON list: " + e.getMessage());
                }   break;
            case 3:
                try {
                    // Read as a list of maps
                    if (new File("Stored Messages.json").exists()){
                        List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                                new File("Stored Messages.json"),
                                new TypeReference<List<Map<String, Map<String, Object>>>>() {}
                        );
                        // Loop over the list
                        for (Map<String, Map<String, Object>> messageEntry : messageList) {
                            for (String messageId : messageEntry.keySet()) {
                                this.messageIds.add(messageId);
                                Map<String, Object> details = messageEntry.get(messageId);
                                for (String key : details.keySet()) {
                                    switch (key) {
                                        case "Message Hash":
                                            this.messageHashes.add(key);
                                            break;
                                        case "Message":
                                            this.storedMessages.add((String) details.get(key));
                                            break;
                                        case "Recipient":
                                            this.recipients.add((String) details.get(key));
                                            break;
                                        case "Sender":
                                            this.senders.add((String) details.get(key));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("");
                    }
                }
                catch (IOException e) {
                    System.err.println("Error reading JSON list: " + e.getMessage());
                }   break;
            default:
                break;
        }
    }
    public String searchMessages() {
        Scanner input = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();
        System.out.print("Enter message ID: ");
        String search = input.nextLine();
        try {
            //Check if file exists
            if (new File("Sent Messages.json").exists()){
                // Read as a list of maps
                List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                        new File("Sent Messages.json"),
                        new TypeReference<List<Map<String, Map<String, Object>>>>() {}
                );
                // Loop over the list
                for (Map<String, Map<String, Object>> messageEntry : messageList) {
                    for (String messageId : messageEntry.keySet()) {
                        if (messageId.equals(search)) {
                            Map<String, Object> details = messageEntry.get(messageId);
                            System.out.println("Recipient: " + details.get("Recipient"));
                            System.out.println("Message:");
                            System.out.println(details.get("Message"));
                        }
                    }
                    }
                }
            }
            catch (IOException e){}
        return "";
    }
    public String longestMessage(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Check if file exists
            File file = new File("Sent Messages.json");
            if (file.exists()) {
                // Read as a list of maps
                List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                        file,
                        new TypeReference<List<Map<String, Map<String, Object>>>>() {}
                );
                String longestMessage = "";
                // Loop over the list
                //ChatGpt
                for (Map<String, Map<String, Object>> entry : messageList) {
                    for (Map<String, Object> details : entry.values()) {
                        Object msgObj = details.get("Message");
                        if (msgObj instanceof String messageText) {
                            if (messageText.length() > longestMessage.length()) {
                                longestMessage = messageText;
                            }
                        }
                    }
                }
                return longestMessage;
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void findMessagesForRecipient() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter recipient number: ");
        String recipientPhoneNumber = input.nextLine();
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("Sent Messages.json");
            if (file.exists()) {
                List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                        file,
                        new TypeReference<List<Map<String, Map<String, Object>>>>() {}
                );

                System.out.println("Messages for recipient: " + recipientPhoneNumber);
                for (Map<String, Map<String, Object>> entry : messageList) {
                    for (Map<String, Object> messageData : entry.values()) {
                        Object recipientObj = messageData.get("Recipient");
                        if (recipientObj != null && recipientObj.equals(recipientPhoneNumber)) {
                            Object messageObj = messageData.get("Message");
                            System.out.println("- " + messageObj);
                        }
                    }
                }
            } else {
                System.out.println("File not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteMessageByHash() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter message hash: ");
        String search = input.nextLine();
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("Sent Messages.json");
            if (!file.exists()) {
                System.out.println("File not found.");
            }

            // Load the JSON file into a list of maps
            List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                    file,
                    new TypeReference<List<Map<String, Map<String, Object>>>>() {}
            );

            // Use an iterator to safely remove items while looping
            Iterator<Map<String, Map<String, Object>>> iterator = messageList.iterator();
            boolean deleted = false;

            while (iterator.hasNext()) {
                Map<String, Map<String, Object>> entry = iterator.next();
                for (Map<String, Object> messageData : entry.values()) {
                    Object hash = messageData.get("Message Hash");
                    if (hash != null && hash.equals(search)) {
                        iterator.remove();  // Remove from list
                        deleted = true;
                        break;
                    }
                }
            }

            // If a message was deleted, write the updated list back to the file
            if (deleted) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, messageList);
                System.out.println("Message deleted and file updated successfully.");
            } else {
                System.out.println("Message hash not found.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void generateReport() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File("Sent Messages.json");
            if (!file.exists()) {
                System.out.println("File not found.");
                return;
            }

            List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                    file,
                    new TypeReference<List<Map<String, Map<String, Object>>>>() {}
            );

            System.out.println("====== Sent Messages Report ======\n");

            for (Map<String, Map<String, Object>> entry : messageList) {
                for (Map.Entry<String, Map<String, Object>> innerEntry : entry.entrySet()) {
                    String id = innerEntry.getKey();
                    Map<String, Object> message = innerEntry.getValue();

                    String sender = (String) message.get("Sender");
                    String recipient = (String) message.get("Recipient");
                    String hash = (String) message.get("Message Hash");
                    String text = (String) message.get("Message");

                    System.out.println("ID: " + id);
                    System.out.println("Sender: " + sender);
                    System.out.println("Recipient: " + recipient);
                    System.out.println("Message Hash: " + hash);
                    System.out.println("Message: " + text);
                    System.out.println("----------------------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public void sendersAndRecipients() {
        ObjectMapper mapper = new ObjectMapper();
        Set<String> senders = new HashSet<>();
        Set<String> recipients = new HashSet<>();

        try {
            File file = new File("Sent Messages.json");
            if (!file.exists()) {
                System.out.println("File not found.");
                return;
            }

            List<Map<String, Map<String, Object>>> messageList = mapper.readValue(
                    file,
                    new TypeReference<List<Map<String, Map<String, Object>>>>() {}
            );

            for (Map<String, Map<String, Object>> entry : messageList) {
                for (Map<String, Object> message : entry.values()) {
                    Object sender = message.get("Sender");
                    Object recipient = message.get("Recipient");

                    if (sender instanceof String) senders.add((String) sender);
                    if (recipient instanceof String) recipients.add((String) recipient);
                }
            }

            // Convert sets to lists (or arrays if needed)
            List<String> senderList = new ArrayList<>(senders);
            List<String> recipientList = new ArrayList<>(recipients);

            System.out.println("Senders: " + senderList);
            System.out.println("Recipients: " + recipientList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}