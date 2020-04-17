package com.company;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WebAppletClient {

    public static void main(String[] args) {
        // Vi skal oprette en forbindelse til serveren.
        // Vi skal kende dens IP adresse, samt port nummer.
        try {
            Socket socket = new Socket("localhost", 6782);
            System.out.println("Har oprettet forbindelse til serveren.");

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(isr);

            printWriter.println("hi there!");
            // 1. Modtag string fra netværk.
            String modtagetString = bufferedReader.readLine();

            // 2. Lav om til JSON.
            JSONParser parser = new JSONParser();
            Object object = parser.parse(modtagetString);
            JSONObject jsonObject = (JSONObject) object;

            // 3. Lav om til tekst, ud fra nøgle.
            String besked = (String) jsonObject.get("message");
            String navn = (String) jsonObject.get("name");
            System.out.println("Modtaget med nøgle 'message' "+besked);
            System.out.println("Modtaget med nøgle 'name' "+navn);

            socket.close(); // For at afslutte denne socket.
        } catch (Exception e) {
            System.out.println("Error: "+ e.getMessage());
        }
    }
}
