package complexFunctions;

import base.Cos;
import base.Sin;
import lombok.Getter;

@Getter
public class Cot {
    private final Sin sin;
    private final Cos cos;

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public double cot(double x, double delta) {
        return cos.cos(x, delta)/sin.sin(x, delta);
    }
}
