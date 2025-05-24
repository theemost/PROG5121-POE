/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.app;

/**
 *
 * @author RC_Student_lab
 */
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        //Objects
        Login signIn = new Login();
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to WhatsDown" + "\nPlease register below");
               
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone number: ");
        String phoneNumber = input.nextLine();

        String registration;
        registration = signIn.registerUser(firstName, lastName, username,
                password, phoneNumber);
        System.out.println(registration);

        System.out.println("Now please login to confirm registration");
        System.out.print("Enter username to login: ");
        String loginUsername = input.nextLine();
        System.out.print("Enter password to login: ");
        String loginPassword = input.nextLine();

        //set Login information entered
        signIn.setUserCredentials(loginUsername, loginPassword);

        //log user in
        if (signIn.loginUser()) {
            System.out.println(signIn.returnLoginStatus());
        }
        else {
            System.out.println(signIn.returnLoginStatus());
        }
    }
}
