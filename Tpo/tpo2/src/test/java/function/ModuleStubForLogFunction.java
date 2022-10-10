package function;

import base.Ln;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import complexFunctions.Cot;
import complexFunctions.Csc;
import complexFunctions.LogN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ModuleStubForLogFunction {
  public static final Ln ln = Mockito.mock(Ln.class);
  public static final LogN logN = Mockito.mock(LogN.class);


  private static void getLogN() throws IOException, CsvException {
    try (CSVReader csvReader =
                 new CSVReader(new FileReader("src/test/resources/testData/logN.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(logN.logN(Double.parseDouble(record[0]),Double.parseDouble(record[1]), 0.001)).thenReturn(Double.parseDouble(record[2]));
      }
    }
  }

  private static void getLn() throws IOException, CsvException {
    try (CSVReader csvReader =
                 new CSVReader(new FileReader("src/test/resources/testData/ln.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(ln.ln(Double.parseDouble(record[0]), 0.001)).thenReturn(Double.parseDouble(record[1]));
      }
    }
  }

  @BeforeAll
  public static void fill() throws IOException, CsvException {
   getLn();
   getLogN();
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/logN.csv")
  void LogNTest(Double base, Double value, Double expected) {
    double result = logN.logN(base, value, 0.001);
    Assertions.assertEquals(result, expected);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/ln.csv")
  void lnTest(Double value, Double expected) {
    double result = ln.ln(value, 0.001);
    Assertions.assertEquals(result, expected);
  }
}
