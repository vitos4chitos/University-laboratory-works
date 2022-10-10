package base;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cos {

    public double cos(double x, double delta){
        x = simple(x);
        double sum = 0;
        int n = 1;
        double step;
        do {
            step = nextTerm(n, x);
            sum += step;
            n++;
        } while (Double.compare(Math.abs(step), delta) >= 0);
        return sum + 1;

    }

    private double simple(double x) {
        while (Double.compare(x, -2 * Math.PI) < 0) {
            x += 2 * Math.PI;
        }
        while (Double.compare(x, Math.PI * 2) > 0) {
            x -= 2 * Math.PI;
        }
        return x;
    }

    private double nextTerm(int step, double value){
        BigDecimal divider = factorial(step * 2);
        BigDecimal divisible = BigDecimal.valueOf(value).pow(2 * step);
        if(step % 2 != 0){
            divider = divider.negate();
        }
        System.out.println(divider + " " + divisible);
        return divisible.divide(divider, 10, RoundingMode.HALF_UP).doubleValue();
    }

    private BigDecimal factorial(int x){
        BigDecimal value = BigDecimal.valueOf(1);
        for(int i = 1; i <= x; i++) {
            value = value.multiply(BigDecimal.valueOf(i));
        }
        return value;
    }
}
