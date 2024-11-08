package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new client and run it
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            // Display welcome message from the server
            System.out.println("Connected to server.");
            String serverMessage = in.readLine();
            System.out.println("Server: " + serverMessage);

            // Continuously read user commands and send to the server
            while (true) {
                System.out.print("Enter command (e.g., ADD 1 2): ");
                String userInput = scanner.nextLine();

                // Send the command to the server
                out.println(userInput);

                // Read and display the server's response
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                System.out.println("Server response: " + response);

                // Process server errors as per protocol
                if (response.startsWith("ERROR INVALID_OPERATION")) {
                    System.out.println("Error: Unrecognized operation.");
                } else if (response.startsWith("ERROR NON_NUMERIC_VALUES")) {
                    System.out.println("Error: Non-numeric values entered.");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
