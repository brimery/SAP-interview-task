package server;

public class Result {
    private final int guess;
    private final String error;
    
    public Result(int guess, String error) {
        this.guess = guess;
        this.error = error;
    }
    
    public int guess() {
        return guess;
    }
    
    public String error() {
        return error;
    }
}
