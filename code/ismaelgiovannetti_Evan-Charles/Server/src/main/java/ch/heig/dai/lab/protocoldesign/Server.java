package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is running on port " + SERVER_PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected.");

                    // Send welcome message with supported operations
                    out.println("Supported operations are: ADD, MULT, SUB, DIV");

                    String clientMessage;
                    while ((clientMessage = in.readLine()) != null) {
                        String[] parts = clientMessage.split(" ");

                        // Validate and process client command
                        if (parts.length != 3) {
                            out.println("ERROR INVALID_OPERATION");
                            continue;
                        }

                        String operation = parts[0];
                        double result;

                        try {
                            double num1 = Double.parseDouble(parts[1]);
                            double num2 = Double.parseDouble(parts[2]);

                            switch (operation.toUpperCase()) {
                                case "ADD":
                                    result = num1 + num2;
                                    out.println("RESULT " + result);
                                    break;
                                case "MULT":
                                    result = num1 * num2;
                                    out.println("RESULT " + result);
                                    break;
                                case "SUB":
                                    result = num1 - num2;
                                    out.println("RESULT " + result);
                                    break;
                                case "DIV":
                                    if (num2 == 0) {
                                        out.println("RESULT undefined");
                                    } else {
                                        result = num1 / num2;
                                        out.println("RESULT " + result);
                                    }
                                    break;
                                default:
                                    out.println("ERROR INVALID_OPERATION");
                            }
                        } catch (NumberFormatException e) {
                            out.println("ERROR NON_NUMERIC_VALUES");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Client connection error: " + e.getMessage());
                }
                System.out.println("Client disconnected.");
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}
