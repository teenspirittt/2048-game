package TwentyFortyEight.game;



import java.io.*;
import java.net.Socket;

public class EchoClient {
    private Socket clientSocket;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;


    public void startConnection(String username,String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);

            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendInfo() {
        try{
            oos.writeUTF("SEND.PACKAGE");
            oos.reset();

           // oos.writeObject();
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

