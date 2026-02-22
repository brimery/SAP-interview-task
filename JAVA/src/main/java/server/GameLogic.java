package server;

import java.util.Random;

public class GameLogic {
    private static final int SECRET_CODE = 1111;
    private final Random random ;

    public GameLogic(){
        random=new Random();
    }
    public GameLogic(Random random){
        this.random=random;
    }

    public static int validateGuess(String input) throws IllegalArgumentException{
        if (input==null || input.length()!=4){
            throw new IllegalArgumentException("Input must be exactly 4 digits.");
        }
        try {
            int guess=Integer.parseInt(input);
            if (guess<1000 || guess>9999 ){
                throw new IllegalArgumentException("Input must be in range 1000-9999.");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Input must contain digits only.");
        }
        int validGuess=Integer.parseInt(input);
        return validGuess;
    }

    public int generateSecretCode() {
        int secret;
        int generated=random.nextInt(1000,10000);
        if(generated%2==0){
            secret = reverseNum(generated);
        }else{
            secret= incrementDigits(generated);
        }
        if (checkPalindrom(secret)){
            secret=7777;
        }
        return secret;
    }
    private boolean checkPalindrom(int num){
        String s = String.valueOf(num);
        return s.equals(new StringBuilder(s).reverse().toString());

    }

    private int reverseNum(int num){
        int reversed=0;
        while (num>0){
            reversed = reversed * 10 + (num % 10);
            num /= 10;
        }
        return reversed;
    }

    private int incrementDigits(int num) {
        String s = String.valueOf(num);
        StringBuilder result = new StringBuilder();

        for (char c : s.toCharArray()) {
            int digit = (c - '0' + 1) % 10;
            result.append(digit);
        }

        return Integer.parseInt(result.toString());
    }

    // GenerateTimestampPrefix generates a textual prefix containing the current time
    public static String generateTimestampPrefix() {
        long timestamp = System.currentTimeMillis() / 1000; // Convert to seconds
        String prefix = "TIME: " + timestamp;
        return prefix;
    }
}

