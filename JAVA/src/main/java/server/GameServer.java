package server;

import java.io.*;
import java.net.*;
public class GameServer {
    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);
            GameManeger maneger=new GameManeger();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket,maneger).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
