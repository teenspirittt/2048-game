package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EchoMultiServer {

    private static EchoMultiServer instance;
    private static int clientCount = 0;
    // private static final HashMap<String, EchoClientHandler> clientList = new HashMap<>();
    private static int id = 0;


    public static synchronized EchoMultiServer getInstance() {
        if (instance == null) {
            instance = new EchoMultiServer();
        }
        return instance;
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int port = 8000;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                EchoClientHandler ech = new EchoClientHandler(serverSocket.accept(), id++);
                // clientList.put(,ech);
                clientCount++;

                ech.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop(serverSocket);
        }
    }

    public static String getClients() {
       /* StringBuilder names = new StringBuilder();
        for (EchoClientHandler client : clientList)
            names.append("Client ").append(client.getClientId()).append("\n");
        return names.toString();*/

        return "";
    }


    public static void stop(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class EchoClientHandler extends Thread {
        private final Socket clientSocket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private final int id;
        EchoMultiServer ems = EchoMultiServer.getInstance();
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        public EchoClientHandler(Socket socket, int id) {
            this.id = id;
            this.clientSocket = socket;
        }

        public void run() {
            try {
                ois = new ObjectInputStream(clientSocket.getInputStream());
                oos = new ObjectOutputStream(clientSocket.getOutputStream());

                while (!clientSocket.isClosed() && !clientSocket.isOutputShutdown() && !clientSocket.isInputShutdown()) {
                    Object readObject = ois.readObject();

                    if (readObject instanceof String string) {
                        System.out.println(string);
                        sendLeaderboard();
                    } else {

                        UserPackage userPackage = (UserPackage) readObject;

                        if (userPackage.getMessage().equals("LOGIN")) {
                            System.out.println("LOG");
                            if (dataBaseHandler.isUsernameExist(userPackage.username)) {
                                if (dataBaseHandler.isCorrectPassword(userPackage.username, userPackage.password)) {
                                    oos.writeObject("incorrect password");
                                    oos.reset();
                                } else {
                                    oos.writeObject("correct");
                                    oos.reset();
                                }
                            } else {
                                oos.writeObject("incorrect username");
                                oos.reset();
                            }
                        } else if (userPackage.getMessage().equals("REGISTER")) {
                            System.out.println("reg");
                            System.out.println(dataBaseHandler.isUsernameExist(userPackage.username));
                            if (!dataBaseHandler.isUsernameExist(userPackage.username)) {
                                oos.writeObject("already exist");
                                System.out.println("if");
                                oos.reset();
                            } else {
                                System.out.println("else");
                                dataBaseHandler.signUpUser(userPackage.username, userPackage.password, userPackage.highScore);
                                oos.writeObject("horoshechno");
                                oos.reset();
                            }
                        }
                    }
                }
                oos.close();
                ois.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        private void sendLeaderboard() {
            ResultSet resultSet = dataBaseHandler.getTable();
            try {
                oos.writeObject(resultSet);
                oos.reset();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        private int matchCounter(ResultSet res) {
            int counter = 0;
            try {
                while (res.next())
                    counter++;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return counter;
        }
    }


}
