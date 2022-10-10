package complexFunctions;

import base.Sin;

public class Csc {

    private final Sin sin;

    public Csc(Sin sin) {
        this.sin = sin;
    }

    public double csc(double x, double delta) {
        return 1 / sin.sin(x, delta);
    }
}
