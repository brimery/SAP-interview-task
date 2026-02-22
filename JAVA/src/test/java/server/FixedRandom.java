package java.server;

import java.util.Random;

public class FixedRandom extends Random {
    int value;
    public FixedRandom(int value) {
        this.value = value;
    }
    @Override
    public int nextInt(int origin, int bound) {
        return value;
    }

}
