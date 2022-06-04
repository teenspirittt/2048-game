package TwentyFortyEight.game;

import TwentyFortyEight.Server.HighScorePackage;

import java.io.*;
import java.net.Socket;

public class EchoClient {
    private Socket clientSocket;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);

            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendHighScore(HighScorePackage highScorePackage) {
        try{
            oos.writeUTF("SEND.PACKAGE");
            oos.reset();

          //  oos.writeObject();
            oos.reset();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getClientsList() {
        String tmp = null;
        try {
            oos.writeUTF("GET.CLIENTS");
            oos.reset();
            tmp = ois.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public void stopConnection() {
        try {
            ois.close();
            oos.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

