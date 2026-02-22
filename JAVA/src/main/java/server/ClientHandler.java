package server;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final GameLogic gameLogic;
    private BufferedReader in=null;
    private PrintWriter out=null;
    private GameSession session;
    private GameManeger maneger;

    public ClientHandler(Socket socket,GameManeger maneger) {
        this.clientSocket = socket;
        this.gameLogic = new GameLogic();
        this.maneger=maneger;
    }

    public void run(){
        try {
            in=new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            out=new PrintWriter(clientSocket.getOutputStream(),true);
            String input;
            while (true) {
                out.println("Choose mode: 1 = Single Player, 2 = Multiplayer");
                input = in.readLine();
                if (input == null) return;

                if (input.equals("1") || input.equals("2")) {
                    break;
                }

                out.println("Invalid input. Please enter 1 or 2.");
            }
            if (input.equals("1")) {
                runSinglePlayer();
            } else {
                runMultiPlayer();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void runMultiPlayer(){
        String input;
        out.println("Waiting for another player...");
        session=maneger.addPlayer(this);
        while (!session.getStarted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
        out.println("Game started! You are playing against another player.");
        while (true){
            while (!session.isPlayerTurn(this)){
                session.waitForTurn(this);
            }
            out.println("Your turn to play! waiting for guess...");
            try {
                String userInput=in.readLine();
                int guess = gameLogic.validateGuess(userInput);
                if (guess==session.getSecretCode()){
                    out.println("\"You win!\"");
                    ClientHandler other=session.getOtherPlayer(this);
                    other.out.println("\"You lose!\"");
                    // TODO: Implement proper game lifecycle handling:
                    // - Remove finished GameSession from GameManager
                    // - Notify both players about game completion
                    // - Allow players to restart or exit
                } else {
                    out.println("Wrong guess!");
                    session.changePlayer();
                }
            }catch (IOException e){

            }


        }
    }



    private void runSinglePlayer() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Welcome to the Code Breaker Game! Enter a code between 1000 and 9999.");
            String inputLine;
            int secretCode = gameLogic.generateSecretCode();
            while ((inputLine = in.readLine()) != null) {
                try {
                    out.println("Enter your guess (secret code) or 'exit' to quit: ");
                    int guess = gameLogic.validateGuess(inputLine);
                    String prefix = "";
//                     String prefix = gameLogic.generateTimestampPrefix();

                    if (secretCode == guess) {
                        out.println(prefix + " Congratulations! You guessed correctly!");
                        break;
                    } else {
                        out.println(prefix + " Try again!");
                    }
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
