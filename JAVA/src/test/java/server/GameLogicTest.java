package server;

class GameLogicTest {
    private final GameLogic gameLogic = new GameLogic();

    public static void main(String[] args) {
        GameLogicTest t = new GameLogicTest();
        t.testValidateGuess_validInput();
        t.testValidateGuess_tooShort();
        System.out.println("All tests passed.");
    }

    void testValidateGuess_validInput() {
        int result = GameLogic.validateGuess("1234");
        if (result != 1234) throw new AssertionError("expected 1234, got " + result);

    }

    void testValidateGuess_nullInput() {
        assertThrows(IllegalArgumentException.class,
                () -> GameLogic.validateGuess(null));
    }



    void testValidateGuess_tooShort() {
        try {
            GameLogic.validateGuess("123");
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException ignored) { }
    }



}
