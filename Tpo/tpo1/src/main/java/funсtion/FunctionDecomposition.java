package funсtion;


public class FunctionDecomposition {

    private double results = 0.;

    public Double tryFunctionDecomposition(Double x, final Long BOUND) {
        for (long k = 0; k <= BOUND; k++) {
            double a = (power(k) * power(x, 2 * k)) / ((fac(2 * k)));
            results += a;
        }
        return results;
    }

    public Double power(Double n, Long degree) {
        Double result = 1.;
        for (int i = 1; i <= degree; i++)
            result *= n;
        return result;
    }

    public Long power(Long degree) {
        long result = 1L;
        for (int i = 1; i <= degree; i++)
            result *= -1;
        return result;
    }

    public Long fac(Long arg) {
        if (arg == 1 || arg == 0)
            return 1L;
        else
            return fac(arg - 1) * arg;
    }

    public Boolean checkInputArgs(String x, String k) {
        try {
            double x_check = Double.parseDouble(x);
            long k_check = Long.parseLong(k);

        } catch (ClassCastException | NumberFormatException e) {
            return false;
        }
        return true;
    }
}
