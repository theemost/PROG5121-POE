/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author RC_Student_lab
 */

import java.util.regex.*;
public class Login {
    private String firstName;
    private String lastName;
    private String registerUsername;
    private String registerPassword;
    private String loginUsername;
    private String loginPassword;
        
    //set user information
    public void setUserCredentials(String username, String password) {
        this.loginUsername = username;
        this.loginPassword = password;
    }
    
    public boolean checkUserName(String username) {
        int length = username.length();
        return username.contains("_" ) && length <= 5;
    }
    
    public boolean checkPasswordComplexity(String password) {
        int len = password.length();
        char character;
        int specialCount = 0;
        int digitCount = 0;
        int letterCount = 0;
        if (len >= 8){
            for (int i = 0; i < len; i++) {
                character = password.charAt(i);
                if (Character.isLetter(character) && Character.isUpperCase(character)) {
                    letterCount += 1;
                }
                else if (Character.isDigit(character)) {
                    digitCount += 1;
                }
                else {
                    specialCount += 1;
                }
            }
            return specialCount != 0 && digitCount != 0 && letterCount != 0;
        }
        return false;
    } 
    
    public boolean checkCellPhoneNumber(String cellNum) {
        // Define the pattern (XXX) XXX-XXXX, where X is a digit
        String regex = "^\\+27\\d{2}\\d{3}\\d{4}$";
        // Create a pattern object
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher object to match the input string against the pattern
        Matcher matcher = pattern.matcher(cellNum);
        // Return true if the phone number matches the pattern, otherwise false
        return matcher.matches();
    }
    
    //register user
    public String registerUser(String firstName, String lastName,
           String username ,String password, String cellNum) {
        if (!checkUserName(username)) {
            return "\"Username is not correctly formatted; please ensure that "
                    + "username contains an underscore and is no more than "
                    + "five characters in length";
        }
        
        else if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please "
                    + "ensure that the password contains at least eight characters, "
                    + "a capital letter, a number and a special character.";
        }
        
        else if (!checkCellPhoneNumber(cellNum)) {
            return "Cell phone number incorrectly formatted or"
                    + " does not contain internation code";
        }
        
        else{
            //set registration user credentials
            this.firstName = firstName;
            this.lastName = lastName;
            this.registerUsername = username;
            this.registerPassword = password;
            return "User successfuly registered.";
        }
    }
    
    public boolean loginUser() {
        if (registerUsername == null && registerPassword == null) {
            return false;
        }
        else {
            return registerUsername.equals(loginUsername) &&
                    this.registerPassword.equals(loginPassword);
        }
    }
    
    public String returnLoginStatus() {
        if (loginUser()) {
            return "Welcome, "  +  this.firstName + " " + this.lastName +
                    ", it's great to see you again.";
        }
        else {
            return "Username or password incorrect, please try again.";
        }
    } 
}
