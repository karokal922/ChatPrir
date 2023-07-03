package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerChat {
    private static final int SERVER_PORT = 8080;
    private List<ClientHandler> clients;

    public static void main(String[] args) {
        ServerChat server = new ServerChat();
        server.start();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Chat server started on port " + SERVER_PORT);

            clients = new ArrayList<>();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader clientIn;
        private PrintWriter clientOut;
        private String username;

        public ClientHandler(Socket clientSocket) {
            try {
                this.clientSocket = clientSocket;
                clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                String clientMessage;
                while ((clientMessage = clientIn.readLine()) != null) {
                    if (clientMessage.startsWith("/upload")) {
                        String fileName = clientMessage.substring("/upload ".length());
                        receiveFile(fileName, clientSocket.getInputStream(), clientOut);
                    } else if (clientMessage.startsWith("/download")) {
                        String fileName = clientMessage.substring("/download ".length());
                        sendFile(fileName, clientOut);
                    } else if (clientMessage.equals("/logout")) {
                        System.out.println(username + " logged out from the chat.");
                        clients.remove(this);
                        break;
                    } else {
                        handleClientMessage(clientMessage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (!clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleClientMessage(String clientMessage) {
            if (username == null) {
                username = clientMessage;  // Set the username when received from the client
                System.out.println(username + " entered the chat.");
            } else {
                String serverMessage = "[" + username + "]: " + clientMessage;
                System.out.println(serverMessage);
                //broadcast(serverMessage);//moza odkomentowac w celu wyswietlania wiadomosci klientow w konsolach innych klientow
            }
        }
        private void receiveFile(String fileName, InputStream clientInput, PrintWriter clientOut) {
            File file = null;
            FileOutputStream fileOutput = null;
            BufferedOutputStream bufferedOutput = null;

            try {
                file = new File("ServerFiles/" + fileName);
                fileOutput = new FileOutputStream(file);
                bufferedOutput = new BufferedOutputStream(fileOutput);

                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = clientInput.read(buffer)) > 0) {
                    if (new String(buffer, 0, bytesRead).startsWith("/endfile")) {
                        break;
                    }
                    bufferedOutput.write(buffer, 0, bytesRead);
                }

                bufferedOutput.flush();

                System.out.println("File received: " + fileName);
                clientOut.println("File received: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedOutput != null) {
                    try {
                        bufferedOutput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileOutput != null) {
                    try {
                        fileOutput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        private void sendFile(String fileName, PrintWriter clientOut) {
            try {
                Path sourceFilePath = Paths.get("ServerFiles/" + fileName);
                if (Files.exists(sourceFilePath)) {
                    clientOut.println("FILE_FOUND");

                    String clientFolder = "ClientFiles/" + username;
                    File clientDir = new File(clientFolder);
                    if (!clientDir.exists()) {
                        clientDir.mkdirs();
                    }

                    Path destinationFilePath = Paths.get(clientFolder + "/" + fileName);
                    Files.copy(sourceFilePath, destinationFilePath);

                    clientOut.println("File downloaded: " + fileName);
                } else {
                    clientOut.println("FILE_NOT_FOUND");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void broadcast(String message) {
            for (ClientHandler client : clients) {
                client.clientOut.println(message);
            }
        }
    }
}