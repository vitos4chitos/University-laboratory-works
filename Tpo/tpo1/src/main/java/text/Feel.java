package text;

import java.util.List;

public interface Feel {

    default FeelCategory howAreYouFeeling(List<Long> randomThoughts) {
        boolean fibonacciFlag = true;
        boolean arithmeticFlag = true;
        boolean geometricFlag = true;
        long a = randomThoughts.get(0), b = randomThoughts.get(1), c;

        for (int i = 2; i < randomThoughts.size(); i++) {
            c = randomThoughts.get(i);
            if (c == a + b) {
                a = b;
                b = c;
            } else {
                fibonacciFlag = false;
                break;
            }
        }

        a = randomThoughts.get(0);
        b = randomThoughts.get(1);
        long sign = 0, step = b - a;
        if (a > b) {
            sign = 1;
            step = a - b;
        }
        for (int i = 2; i < randomThoughts.size(); i++) {
            c = randomThoughts.get(i);
            if ((sign == 1 && c == b - step) || (sign == 0 && c == b + step)) {
                b = c;
            } else {
                arithmeticFlag = false;
                break;
            }
        }

        a = randomThoughts.get(0);
        b = randomThoughts.get(1);
        sign = 0;
        step = b / a;
        if (a > b) {
            sign = 1;
            step = a / b;
        }
        for (int i = 2; i < randomThoughts.size(); i++) {
            c = randomThoughts.get(i);

            if ((sign == 1 && c == b / step) || (sign == 0 && c == b * step)) {
                b = c;
            } else {
                geometricFlag = false;
            }
        }

        if (fibonacciFlag)
            return FeelCategory.BEST;
        if (geometricFlag)
            return FeelCategory.GOOD;
        if (arithmeticFlag)
            return FeelCategory.NORMAL;
        return FeelCategory.UNCOMFORTABLE;
    };
}
