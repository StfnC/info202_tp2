package ca.qc.bdeb.info202.tp2;

import java.util.Random;

public class De {
    // TODO: -Try to fix this, so I don't have to use static here
    private static Random r = new Random();

    public static int jeter() {
        return r.nextInt(6)+1;
    }
}
