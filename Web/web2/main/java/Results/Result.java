package Results;

import java.io.Serializable;

public class Result implements Serializable {
    private double x;
    private double y;
    private double r;
    private boolean result;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isResult() {
        return result;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setR(double r){
        this.r = r;
    }

    public void setResult(boolean result){
        this.result = result;
    }
}
