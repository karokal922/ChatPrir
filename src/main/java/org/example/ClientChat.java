package org.example;
//import java.io.*;
//import java.net.Socket;
//
//public class ClientChat {
//    private static final String SERVER_HOST = "localhost";
//    private static final int SERVER_PORT = 8080;
//
//    public static void main(String[] args) {
//        try {
//            Socket serverSocket = new Socket(SERVER_HOST, SERVER_PORT);
//            System.out.println("Connected to chat server at " + SERVER_HOST + ":" + SERVER_PORT);
//
//            BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
//            PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);
//
//            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
//
//            System.out.print("Enter your username: ");
//            String username = consoleIn.readLine();
//            serverOut.println(username);
//
//            Thread serverThread = new Thread(() -> {
//                try {
//                    String serverMessage;
//                    while ((serverMessage = serverIn.readLine()) != null) {
//                        if (!serverMessage.startsWith("[" + username + "]:")) {
//                            System.out.println(serverMessage);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            serverThread.start();
//
//            String clientMessage;
//            while ((clientMessage = consoleIn.readLine()) != null) {
//                if (clientMessage.startsWith("/upload")) {
//                    String filePath = clientMessage.substring("/upload ".length());
//                    uploadFile(filePath, serverOut);
//                } else {
//                    serverOut.println(clientMessage);
//                }
//            }
//
//            serverThread.join();
//            serverSocket.close();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void uploadFile(String filePath, PrintWriter serverOut) {
//        try {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                System.out.println("File not found: " + filePath);
//                return;
//            }
//
//            serverOut.println("/upload " + file.getName());
//
//            BufferedReader fileReader = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = fileReader.readLine()) != null) {
//                serverOut.println(line);
//            }
//            serverOut.println("/endfile");
//
//            fileReader.close();
//            System.out.println("File uploaded: " + file.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}//works
//import java.io.*;
//import java.net.Socket;
//
//public class ClientChat {
//    private static final String SERVER_HOST = "localhost";
//    private static final int SERVER_PORT = 8080;
//
//    public static void main(String[] args) {
//        try {
//            Socket serverSocket = new Socket(SERVER_HOST, SERVER_PORT);
//            System.out.println("Connected to chat server at " + SERVER_HOST + ":" + SERVER_PORT);
//
//            BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
//            PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);
//
//            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
//
//            System.out.print("Enter your username: ");
//            String username = consoleIn.readLine();
//            serverOut.println(username);
//
//            Thread serverThread = new Thread(() -> {
//                try {
//                    String serverMessage;
//                    while ((serverMessage = serverIn.readLine()) != null) {
//                        if (!serverMessage.startsWith("[" + username + "]:")) {
//                            System.out.println(serverMessage);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            serverThread.start();
//
//            String clientMessage;
//            while ((clientMessage = consoleIn.readLine()) != null) {
//                if (clientMessage.startsWith("/upload")) {
//                    String filePath = clientMessage.substring("/upload ".length());
//                    uploadFile(filePath, serverOut);
//                } else {
//                    serverOut.println(clientMessage);
//                }
//            }
//
//            serverThread.join();
//            serverSocket.close();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void uploadFile(String filePath, PrintWriter serverOut) {
//        try {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                System.out.println("File not found: " + filePath);
//                return;
//            }
//
//            serverOut.println("/upload " + file.getName());
//
//            BufferedReader fileReader = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = fileReader.readLine()) != null) {
//                serverOut.println(line);
//            }
//            serverOut.println("/endfile");
//
//            fileReader.close();
//            System.out.println("File uploaded: " + file.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}//works
//import java.io.*;
//import java.net.Socket;
//
//public class ClientChat {
//    private static final String SERVER_HOST = "localhost";
//    private static final int SERVER_PORT = 8080;
//
//    public static void main(String[] args) {
//        try {
//            Socket serverSocket = new Socket(SERVER_HOST, SERVER_PORT);
//            System.out.println("Connected to chat server at " + SERVER_HOST + ":" + SERVER_PORT);
//
//            BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
//            PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);
//
//            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
//
//            System.out.print("Enter your username: ");
//            String username = consoleIn.readLine();
//            serverOut.println(username);
//
//            Thread serverThread = new Thread(() -> {
//                try {
//                    String serverMessage;
//                    while ((serverMessage = serverIn.readLine()) != null) {
//                        if (!serverMessage.startsWith("[" + username + "]:")) {
//                            System.out.println(serverMessage);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            serverThread.start();
//
//            String clientMessage;
//            while ((clientMessage = consoleIn.readLine()) != null) {
//                if (clientMessage.startsWith("/upload")) {
//                    String filePath = clientMessage.substring("/upload ".length());
//                    uploadFile(filePath, serverOut);
//                } else if (clientMessage.startsWith("/download")) {
//                    String fileName = clientMessage.substring("/download ".length());
//                    serverOut.println(clientMessage);
//                    downloadFile(fileName, username, serverIn);
//                } else if (clientMessage.startsWith("/logout")) {
//                    serverOut.println("/logout");
//                    break;
//                }
//                else {
//                    serverOut.println(clientMessage);
//                }
//            }
//
//            serverThread.join();
//            serverSocket.close();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//        private static void uploadFile(String filePath, PrintWriter serverOut) {
//        try {
//            File file = new File(filePath);
//            if (!file.exists()) {
//                System.out.println("File not found: " + filePath);
//                return;
//            }
//
//            serverOut.println("/upload " + file.getName());
//
//            BufferedReader fileReader = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = fileReader.readLine()) != null) {
//                serverOut.println(line);
//            }
//            serverOut.println("/endfile");
//
//            fileReader.close();
//            System.out.println("File uploaded: " + file.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void downloadFile(String fileName, String username, BufferedReader serverIn) {
//        try {
//            String response = serverIn.readLine();
//            if (response.equals("FILE_FOUND")) {
//                String clientFolder = "ClientFiles/" + username;
//                File clientDir = new File(clientFolder);
//                if (!clientDir.exists()) {
//                    clientDir.mkdirs();
//                }
//
//                String filePath = clientFolder + "/" + fileName;
//                FileWriter fileWriter = new FileWriter(filePath);
//
//                String line;
//                while ((line = serverIn.readLine()) != null) {
//                    if (line.equals("/endfile")) {
//                        break;
//                    }
//                    fileWriter.write(line);
//                    fileWriter.write(System.lineSeparator());
//                }
//
//                fileWriter.close();
//                System.out.println("File downloaded: " + fileName);
//            } else if (response.equals("FILE_NOT_FOUND")) {
//                System.out.println("File not found: " + fileName);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
import java.io.*;
import java.net.Socket;

public class ClientChat {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            Socket serverSocket = new Socket(SERVER_HOST, SERVER_PORT);
            System.out.println("Connected to chat server at " + SERVER_HOST + ":" + SERVER_PORT);

            BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);

            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your username: ");
            String username = consoleIn.readLine();
            serverOut.println(username);

            Thread serverThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = serverIn.readLine()) != null) {
                        if (!serverMessage.startsWith("[" + username + "]:")) {
                            System.out.println(serverMessage);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverThread.start();

            String clientMessage;
            while ((clientMessage = consoleIn.readLine()) != null) {
                if (clientMessage.startsWith("/upload")) {
                    String filePath = clientMessage.substring("/upload ".length());
                    uploadFile(filePath, serverSocket.getOutputStream());
                } else if (clientMessage.startsWith("/download")) {
                    String fileName = clientMessage.substring("/download ".length());
                    serverOut.println(clientMessage);
                    downloadFile(fileName, username, serverIn);
                } else if (clientMessage.equals("/logout")) {
                    serverOut.println("/logout");
                    break;
                } else {
                    serverOut.println(clientMessage);
                }
            }

            serverThread.join();
            serverSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void uploadFile(String filePath, OutputStream serverOut) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found: " + filePath);
                return;
            }

            PrintWriter printWriter = new PrintWriter(serverOut, true);
            printWriter.println("/upload " + file.getName());

            FileInputStream fileInput = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fileInput.read(buffer)) != -1) {
                serverOut.write(buffer, 0, bytesRead);
            }

            serverOut.flush();
            fileInput.close();

            System.out.println("File uploaded: " + file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileName, String username, BufferedReader serverIn) {
        try {
            String response = serverIn.readLine();
            if (response.equals("FILE_FOUND")) {
                String clientFolder = "ClientFiles/" + username;
                File clientDir = new File(clientFolder);
                if (!clientDir.exists()) {
                    clientDir.mkdirs();
                }

                String filePath = clientFolder + "/" + fileName;
                FileWriter fileWriter = new FileWriter(filePath);

                String line;
                while ((line = serverIn.readLine()) != null) {
                    if (line.equals("/endfile")) {
                        break;
                    }
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }

                fileWriter.close();
                System.out.println("File downloaded: " + fileName);
            } else if (response.equals("FILE_NOT_FOUND")) {
                System.out.println("File not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

