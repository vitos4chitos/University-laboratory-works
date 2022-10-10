package funсtion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionDecompositionTest {

    private final funсtion.FunctionDecomposition moduleFunction = new funсtion.FunctionDecomposition();

    @Test
    @DisplayName("Test function degree")
    void testModulePowerForOne() {
        assertEquals(-1, moduleFunction.power(5L));
        assertEquals(1, moduleFunction.power(4L));
        assertEquals(1, moduleFunction.power(0L));
    }

    @Test
    void testModulePowerForNumbers() {
        assertEquals(8., moduleFunction.power(2., 3L));
        assertEquals(81., moduleFunction.power(3., 4L));
        assertEquals(1., moduleFunction.power(1., 0L));
        assertEquals(0., moduleFunction.power(0., 5L));
        assertEquals(33_232_930_569_601., moduleFunction.power(7., 16L));
    }

    @Test
    void testModuleFactorial() {
        assertEquals(120L, moduleFunction.fac(5L));
        assertEquals(1L, moduleFunction.fac(1L));
        assertEquals(3_628_800L, moduleFunction.fac(10L));
        assertEquals(2_432_902_008_176_640_000L, moduleFunction.fac(20L));
    }

    @Test
    void testModuleInputArgs() {
        assertEquals(false, moduleFunction.checkInputArgs("3523 gf-232!", "3"));
        assertEquals(false, moduleFunction.checkInputArgs("3", "-=1111"));
        assertEquals(false, moduleFunction.checkInputArgs("1`=23-", "--1"));

        String boundLong = String.valueOf(Long.MAX_VALUE);
        String boundDouble = String.valueOf(Double.MAX_VALUE);
        assertEquals(true, moduleFunction.checkInputArgs(boundDouble, boundLong));

        assertEquals(true, moduleFunction.checkInputArgs("0", "9999999"));
        assertEquals(true, moduleFunction.checkInputArgs("-999999", "0"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 10.1, 20.4, 30.3, 40., 50., 60., 70., 80., 90., 100.})
    @DisplayName("Test function in [0 - 100] degree")
    void testModuleDecompositionZeroToOneHundred(double values) {
        final double x = values * (PI / 180);
        double results = moduleFunction.tryFunctionDecomposition(x, 5L);
        double cos_x = Math.cos(x);
        assertTrue((results - cos_x < 0.000001));
    }

    @ParameterizedTest
    @ValueSource(doubles = {101.4, 135., 145.3, 155., 157., 169.7, 200.0, 250., 245.1, 270.9, 350.})
    @DisplayName("Test function in [101 - 360) values like degree")
    void testModuleDecompositionOneHundredOneToThreeHundredFifty(double values) {
        final double x = values * (PI / 180);
        double results = moduleFunction.tryFunctionDecomposition(x, 5L);
        double cos_x = Math.cos(x);
        assertTrue((results - cos_x < 0.000001));
    }

    @ParameterizedTest
    @ValueSource(doubles = {(3 * PI), (5 * PI) / 2, (7 * PI) / 3, ((2 * PI) + ((2 * PI) / 7))})
    @DisplayName("Test function with P period values")
    void testModuleDecompositionWithPeriod(double values) {
        final double x = values * (PI / 180);
        double results = moduleFunction.tryFunctionDecomposition(x, 5L);
        double cos_x = Math.cos(x);
        assertTrue((results - cos_x < 0.000001));
    }




}