package function;

import base.Cos;
import base.Ln;
import base.Sin;
import com.opencsv.exceptions.CsvException;
import complexFunctions.Cot;
import complexFunctions.Csc;
import complexFunctions.LogN;
import mainFunction.MainFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;

public class IntegrationModule {

  @ParameterizedTest
  @CsvFileSource(resources = "/integration.csv")
  void mainWithoutStub(Double value, Double expected) {
    MainFunction mainFunction =
        new MainFunction(
            new Csc(new Sin()), new LogN(new Ln()), new Cot(new Sin(), new Cos()), new Ln());
    Assertions.assertTrue(mainFunction.calculate(value, 0.00001) - expected < 0.0001);
  }

  @BeforeAll
  public static void init() throws IOException, CsvException {
    ModuleStubForTrigonometricFunction.fill();
    ModuleStubForLogFunction.fill();
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/cot.csv")
  void cotWithCosStub(Double value, Double expected) {
    Cot cot = new Cot(new Sin(), ModuleStubForTrigonometricFunction.cos);
    System.out.println(cot.cot(value, 0.001) + " " + expected);
    Assertions.assertTrue(cot.cot(value, 0.001) - expected < 0.001);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/logN.csv")
  void logNWithLnStub(Double value, Double base, Double expected) {
    LogN logN = new LogN(ModuleStubForLogFunction.ln);
    //System.out.println(logN.logN(value, base, 0.001) + " " + expected);
    Assertions.assertTrue(logN.logN(value, base, 0.001) - expected < 0.001);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/csc.csv")
  void cscWithSin(Double value, Double expected) {
    Csc csc = new Csc(ModuleStubForTrigonometricFunction.sin);
    Assertions.assertTrue(csc.csc(value, 0.001) - expected < 0.0001);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/integration.csv")
  void mainModuleWithAll(Double value, Double expected) {
    Csc csc = ModuleStubForTrigonometricFunction.csc;
    Cot cot = ModuleStubForTrigonometricFunction.cot;
    LogN logN = ModuleStubForLogFunction.logN;
    Ln ln = ModuleStubForLogFunction.ln;
    MainFunction mainFunction = new MainFunction(csc, logN, cot, ln);
    //System.out.println(mainFunction.calculate(value, 0.001) + " " + expected);
    Assertions.assertTrue(mainFunction.calculate(value, 0.001) - expected < 0.001);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/integration.csv")
  void mainModuleWithLogN(Double value, Double expected) {
    LogN logN = ModuleStubForLogFunction.logN;
    MainFunction mainFunction =
        new MainFunction(new Csc(new Sin()), logN, new Cot(new Sin(), new Cos()), new Ln());
    // System.out.println(mainFunction.calculate(value, 0.001) + " " + expected);
    Assertions.assertTrue(mainFunction.calculate(value, 0.001) - expected < 0.0001);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/integration.csv")
  void mainModuleWithCosAndSin(Double value, Double expected) {
    Cos cos = ModuleStubForTrigonometricFunction.cos;
    Sin sin = ModuleStubForTrigonometricFunction.sin;
    MainFunction mainFunction =
        new MainFunction(new Csc(sin), new LogN(new Ln()), new Cot(sin, cos), new Ln());
    Assertions.assertTrue(mainFunction.calculate(value, 0.001) - expected < 0.0001);
  }
}
