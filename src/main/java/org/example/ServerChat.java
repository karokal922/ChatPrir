package org.example;

//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ServerChat {
//    private static final int SERVER_PORT = 8080;
//    private List<ClientHandler> clients;
//
//    public static void main(String[] args) {
//        ServerChat server = new ServerChat();
//        server.start();
//    }
//
//    public void start() {
//        try {
//            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
//            System.out.println("Chat server started on port " + SERVER_PORT);
//
//            clients = new ArrayList<>();
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("New client connected: " + clientSocket);
//
//                ClientHandler clientHandler = new ClientHandler(clientSocket);
//                clients.add(clientHandler);
//
//                Thread clientThread = new Thread(clientHandler);
//                clientThread.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private class ClientHandler implements Runnable {
//        private Socket clientSocket;
//        private BufferedReader clientIn;
//        private PrintWriter clientOut;
//        private String username;
//
//        public ClientHandler(Socket clientSocket) {
//            try {
//                this.clientSocket = clientSocket;
//                clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public void run() {
//            try {
//                String clientMessage;
//                while ((clientMessage = clientIn.readLine()) != null) {
//                    if (clientMessage.startsWith("/upload")) {
//                        String fileName = clientMessage.substring("/upload ".length());
//                        receiveFile(fileName, clientIn);
//                    } else if (clientMessage.startsWith("/download")) {
//                        String fileName = clientMessage.substring("/download ".length());
//                        sendFile(fileName, clientOut);
//                    } else if (clientMessage.equals("/logout")) {
//                        System.out.println(username + " logged out from the chat.");
//                        clients.remove(this);
//                        break;
//                    } else {
//                        handleClientMessage(clientMessage);
//                    }
//                }
//
//                System.out.println(username + " logged out from the chat.");
//                clients.remove(this);
//                clientSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            finally {
//                try {
//                    clientSocket.close();
//                } catch (IOException e) {
//                    // Ignore any errors that occur while closing the socket
//                }
//            }
//        }
//
//        private void handleClientMessage(String clientMessage) {
//            if (username == null) {
//                username = clientMessage;  // Set the username when received from the client
//                System.out.println(username + " entered the chat.");
//            } else {
//                String serverMessage = "[" + username + "]: " + clientMessage;
//                System.out.println(serverMessage);
//                // broadcast(serverMessage);
//            }
//        }
//
//        private void receiveFile(String fileName, BufferedReader clientIn) {
//            try {
//                FileOutputStream fileOutput = new FileOutputStream("ServerFiles/" + fileName);
//
//                BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutput));
//
//                String line;
//                while ((line = clientIn.readLine()) != null) {
//                    if (line.equals("/endfile")) {
//                        break;
//                    }
//                    fileWriter.write(line);
//                    fileWriter.newLine();
//                }
//
//                fileWriter.close();
//                System.out.println("File received: " + fileName);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void sendFile(String fileName, PrintWriter clientOut) {
//            try {
//                Path filePath = Paths.get("ServerFiles", fileName);
//                if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
//                    File fileToDownload = filePath.toFile();
//
//                    clientOut.println("File available: " + fileToDownload.getName());
//
//                    // Create the client's folder for downloaded files
//                    File clientFolder = new File(username);
//                    if (!clientFolder.exists()) {
//                        if (clientFolder.mkdir()) {
//                            System.out.println("Client folder created: " + clientFolder.getName());
//                        }
//                    }
//
//                    // Copy the file to the client's folder
//                    Path destPath = Paths.get(username, fileToDownload.getName());
//                    Files.copy(fileToDownload.toPath(), destPath);
//
//                    clientOut.println("File downloaded: " + fileToDownload.getName());
//                } else {
//                    clientOut.println("File not found: " + fileName);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
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
                        receiveFile(fileName, clientSocket.getInputStream());
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
                // broadcast(serverMessage);
            }
        }

        private void receiveFile(String fileName, InputStream clientInput) {
            try {
                File file = new File("ServerFiles/" + fileName);

                FileOutputStream fileOutput = new FileOutputStream(file);
                BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);

                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = clientInput.read(buffer)) != -1) {
                    bufferedOutput.write(buffer, 0, bytesRead);
                }

                bufferedOutput.flush();
                bufferedOutput.close();
                fileOutput.close();

                System.out.println("File received: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendFile(String fileName, PrintWriter clientOut) {
            try {
                Path filePath = Paths.get("ServerFiles/" + fileName);
                if (Files.exists(filePath)) {
                    clientOut.println("FILE_FOUND");
                    Files.lines(filePath).forEach(clientOut::println);
                    clientOut.println("/endfile");
                } else {
                    clientOut.println("FILE_NOT_FOUND");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

