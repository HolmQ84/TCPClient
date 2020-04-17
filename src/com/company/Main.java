package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main implements Runnable {


    public static void main(String[] args) {
        boolean stillrunning = true;
	    // Vi skal oprette en forbindelse til serveren.
        // Vi skal kende dens IP adresse, samt port nummer.
        try {
            Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 6780);
            System.out.println("Har oprettet forbindelse til serveren - "+socket);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(isr);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            // Autoflush betyder at den automatisk sender data - ellers skal man bruge printWriter.flush
            Scanner scan = new Scanner(System.in);
            while (stillrunning) {
                // Sender besked til klienten.
                System.out.print("Skriv din besked -> ");
                printWriter.println(scan.nextLine());
                // Modtager en besked fra klienten.
                String currentMsg = bufferedReader.readLine();
                System.out.println("Modtaget besked: "+currentMsg);
                if (currentMsg.equalsIgnoreCase("quit")) {
                    stillrunning = false;
                }
            }
            socket.close(); // For at afslutte denne socket.
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }

    @Override
    public void run() {

    }
}