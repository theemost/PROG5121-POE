/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

/**
 *
 * @author wicke
 */
import java.util.Scanner;

public class QuickChat {

    public static void main(String[] args) {
        //Objects
        Login signIn = new Login();
        Message messageObj = new Message();
        Scanner input = new Scanner(System.in);
        
        System.out.println("""
                           Welcome to QuickChat
                           Please register below
                           """);
        
        //Register new user  
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        String username;
        String password;
        String phoneNumber;
        do {
            System.out.print("Enter username: ");
            username = input.nextLine();
        }
        while (!signIn.checkUserName(username));
        do {
            System.out.print("Enter password: ");
            password = input.nextLine();
        }
        while (!signIn.checkPasswordComplexity(password));
        do {
            System.out.print("Enter cellphone number: ");
            phoneNumber = input.nextLine();
        }
        while (!signIn.checkCellPhoneNumber(phoneNumber));
        String registration;
        registration = signIn.registerUser(firstName, lastName, username,
                password, phoneNumber);
        System.out.println(registration);
        
        //Login user
        String loginUsername;
        String loginPassword;
        do {
            System.out.println("Now please login to confirm registration");
        
            System.out.print("Enter username to login: ");
            loginUsername = input.nextLine();
            System.out.print("Enter password to login: ");
            loginPassword = input.nextLine();
            //Set login credentials
            signIn.setUserCredentials(loginUsername, loginPassword);
            System.out.println(signIn.returnLoginStatus());
        }
        while (!signIn.loginUser());
        
        //log user in
        while (signIn.loginUser()) {
            int choice;
            do {System.out.println("Welcome to QuickChat.");
            System.out.println("""
                               1. Send Messages
                               2. Show recently sent messages
                               3. Quit""");
            System.out.print("Choice: ");
            choice = input.nextInt();
                switch (choice) {
                    case 1:
                        //Message feature
                        int numMessages;
                        String message;
                        String recipientCell;
                        String messageHash;
                        String messageId;
                        
                        System.out.print("How many messages do you want to send? ");
                        numMessages = input.nextInt();
                        input.nextLine();
                        int messageNum = 1;
                        while (numMessages>0){
                            System.out.println("Message: " + messageNum );
                            do {
                                System.out.print("Enter recipient cell number:");
                                recipientCell = input.nextLine();
                                if (!messageObj.checkRecipientCell(recipientCell)){
                                    System.out.println("Cell phone number incorrectly"
                                            + " formatted or does not contain"
                                            + " international code");
                                }
                                else {
                                    System.out.println("Number correct");
                                }
                            }
                            while(!messageObj.checkRecipientCell(recipientCell));
                            do {
                                System.out.println("Please enter your message:");
                                message = input.nextLine();
                                if (message.length()>=250) {
                                    System.out.println("Please enter a message "
                                            + "of less than 250 characters.");
                                }
                                else {
                                    System.out.println("Message ready to send");
                                }
                            }
                            while(message.length()>250);
                            //Save message
                            messageObj.saveMessage(message, signIn.cellNum);
                            //Generate message ID
                            messageObj.checkMessageId();
                            messageId = messageObj.messageId;
                            System.out.println("Message ID generated: " + messageId);
                            //Generate message hash
                            messageHash = messageObj.createMessagehash();
                            System.out.println("Message hash generated: " + messageHash);
                            System.out.println("""
                                                What do you want to do?
                                                1. Send Message
                                                2. Disregard Message
                                                3. Store Message to send later""");
                            
                            int sendMessage = input.nextInt();
                            String messageFeedback = messageObj.sentMessages(
                                    sendMessage);
                            input.nextLine();
                            System.out.println(messageFeedback);
                            messageNum ++;
                            numMessages --;
                        }       
                        int totalMessagesSent = messageObj.returnTotalMesssages();                
                        System.out.println("Total messages sent: " + totalMessagesSent);
                        break;
                    //View saved messages
                    case 2:    
                        System.out.println("""
                                           What do you want to do
                                           1. Search for a message using message id
                                           2. Display longest message
                                           3. Display Messages for specific recipient
                                           4. Delete Message using message hash
                                           5. Display Report
                                           6. Get all senders and recipients
                                           """);
                        System.out.print("Choice: ");
                        choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            messageObj.searchMessages();
                            break;
                        case 2:
                            System.out.println(messageObj.longestMessage());
                            break;
                        case 3:
                            messageObj.findMessagesForRecipient();
                            break;
                        case 4:
                            messageObj.deleteMessageByHash();
                            break;
                        case 5:
                            messageObj.generateReport();
                            break;
                        case 6:
                            messageObj.sendersAndRecipients();
                            break;
                        default:
                            break;
                    }
                    break;

                    case 3:
                        signIn.setUserCredentials("exit", "exit");
                        System.out.println("Logging out. Goodbye");
                        break;
                    default:
                        break;
                }
            }
            while (choice != 3);
       }
    }
}
