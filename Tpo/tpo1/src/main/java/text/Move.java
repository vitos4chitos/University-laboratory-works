package text;

public interface Move {

    void move(long x, long y);

    double travelTime(double length);

    boolean canGet(double length);
}
