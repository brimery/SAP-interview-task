package server;

import java.util.ArrayList;
import java.util.List;

public class GameManeger {
    List<GameSession> activeSessions;
    GameSession nextSession;
    private final GameLogic gameLogic = new GameLogic();


    public GameManeger(){
        activeSessions=new ArrayList<>();
        nextSession=null;
    }

    public synchronized GameSession addPlayer(ClientHandler player){
        if (nextSession==null){
            nextSession=new GameSession(gameLogic);
            nextSession.addPlayer(player);
            return null;
        }
        nextSession.addPlayer(player);
        activeSessions.add(nextSession);
        GameSession fullSussion=nextSession;
        nextSession=null;
        fullSussion.setStarted(true);
        return fullSussion;
    }
}
