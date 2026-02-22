package server;

public class GameSession {
    private server.ClientHandler player1=null;
    private ClientHandler player2=null;
    private server.ClientHandler currentTurn;
    private int secretCode;
    private final GameLogic gameLogic;
    private boolean started;


    public GameSession(GameLogic gameLogic) {
        this.currentTurn = player2;
        this.gameLogic = gameLogic;
        this.secretCode = gameLogic.generateSecretCode();
        this.started=false;
    }

    public boolean getStarted(){
        return started;
    }

    public void setStarted(boolean start){
        started=start;
    }
    public int getSecretCode(){
        return secretCode;
    }

    public ClientHandler getOtherPlayer(ClientHandler first){
        if (player1==first){
            return player2;
        } else if (player2==first) {
            return player1;
        }else{
            return null;
        }
    }
    public boolean addPlayer(ClientHandler newPlayer){
        if (player1==null){
            player1=newPlayer;
            return true;
        }
        if (player2==null){
            player2=newPlayer;
            return true;
        }
        return false;

    }

    public boolean isPlayerTurn(ClientHandler player){
        return player==currentTurn;
    }

    public synchronized void waitForTurn(ClientHandler player) {
        while (player != currentTurn) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
    }

    public synchronized void changePlayer() {
        currentTurn = (currentTurn == player1) ? player2 : player1;
        notifyAll();
    }


}
