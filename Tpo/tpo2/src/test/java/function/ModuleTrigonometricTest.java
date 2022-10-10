package function;

import base.Cos;
import base.Ln;
import base.Sin;
import complexFunctions.Cot;
import complexFunctions.Csc;
import complexFunctions.LogN;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import util.CsvReadWrite;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleTrigonometricTest {
  private final Double delta = 0.001;
  private final Cos cos = new Cos();
  private final Sin sin = new Sin();
  private final Ln ln = new Ln();
  private final LogN logN = new LogN(ln);
  private final Cot cot = new Cot(sin, cos);
  private final Csc csc = new Csc(sin);
  private final CsvReadWrite csvPrinter = new CsvReadWrite();

//  @ParameterizedTest
//  @CsvFileSource(resources = "/trigonometryMocks/cos.csv")
//  void cosTest(Double x, BigDecimal expected) throws FileNotFoundException {
//    BigDecimal result = cos.cos(x, delta);
//    csvPrinter.setFilePath("src/test/resources/output/cos.csv");
//    csvPrinter.print(x, result.doubleValue());
//    assertEquals(expected.floatValue(), result.floatValue(), accuracy);
//  }
}
