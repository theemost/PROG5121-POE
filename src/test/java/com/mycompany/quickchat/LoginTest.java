/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
 */
public class LoginTest {
    
    private String firstName;
    private String lastName;
    
    /**
    * Test of setUserCredentials method, of class Login.
    */
    @Test
    public void testSetUserCredentials() {
        System.out.println("setUserCredentials");
        String loginUsername = "kyl_1";
        String loginPassword = "Ch&&sec@ke99";
        Login instance = new Login();
        instance.setUserCredentials(loginUsername, loginPassword);
    }

    /**
     * Test of registerUser method, of class Login.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String firstName = "Esethu";
        String lastName = "Busakwe";
        String username = "kyl_1";
        String password = "Ch&&sec@ke99";
        String cellNum = "+27838968976";
        Login instance = new Login();
        String expResult = "User successfuly registered.";
        String result = instance.registerUser(firstName, lastName, username, password, cellNum);
        assertEquals(expResult, result);
    }
    /**
     * Test of loginUser method, of class Login.
     */
    @Test
    public void testLoginUser() {
        System.out.println("loginUser");
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.loginUser();
        assertEquals(expResult, result);
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        Login instance = new Login();
        String expResult = "Username or password incorrect, please try again.";
        String result = instance.returnLoginStatus();
        assertEquals(expResult, result);
    }
}