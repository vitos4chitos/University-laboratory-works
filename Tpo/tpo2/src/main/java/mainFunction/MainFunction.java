package mainFunction;

import base.Ln;
import com.sun.tools.javac.Main;
import complexFunctions.Cot;
import complexFunctions.Csc;
import complexFunctions.LogN;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class MainFunction {

    private final Csc csc;
    private final LogN logN;
    private final Cot cot;
    private final Ln ln;


    public MainFunction(Csc csc, LogN logN, Cot cot, Ln ln) {
        this.csc = csc;
        this.logN = logN;
        this.cot = cot;
        this.ln = ln;
    }

    public double calculate(double x, double delta){
        if(Double.compare(x, 0.0) <= 0){
            return (csc.csc(x, delta)/csc.csc(x, delta)) + cot.cot(x, delta);
        }
        else{
            double multiplier1 = Math.pow(ln.ln(x, delta), 2);
            double term1 = Math.pow((log5(x, delta) / log2(x, delta)), 2) / log5(x, delta);
            double term2 = log10(x, delta) * log2(x, delta);
            double term3 = log2(x, delta) + log5(x, delta);
            double multiplier2 = term1 + term2 + term3;
            return multiplier1 * multiplier2;

        }
    }

    private double log2(double x, double delta){
        return logN.logN(x, 2, delta);
    }


    private double log5(double x, double delta){
        return logN.logN(x, 5, delta);
    }

    private double log10(double x, double delta){
        return logN.logN(x, 10, delta);
    }
}
