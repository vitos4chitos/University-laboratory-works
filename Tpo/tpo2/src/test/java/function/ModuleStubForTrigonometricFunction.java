package function;

import base.Cos;
import base.Ln;
import base.Sin;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import complexFunctions.Cot;
import complexFunctions.Csc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class ModuleStubForTrigonometricFunction {

  public static final Cos cos = Mockito.mock(Cos.class);
  public static final Sin sin = Mockito.mock(Sin.class);
  public static final Cot cot = Mockito.mock(Cot.class);
  public static final Csc csc = Mockito.mock(Csc.class);

  private static void getCos() throws IOException, CsvException {
    try (CSVReader csvReader =
        new CSVReader(new FileReader("src/test/resources/testData/cos.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(cos.cos(Double.parseDouble(record[0]), 0.001)).thenReturn(Double.parseDouble(record[1]));
      }
    }
  }

  private static void getSin() throws IOException, CsvException {
    try (CSVReader csvReader =
                 new CSVReader(new FileReader("src/test/resources/testData/sin.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(sin.sin(Double.parseDouble(record[0]), 0.001)).thenReturn(Double.parseDouble(record[1]));
      }
    }
  }

  private static void getCot() throws IOException, CsvException {
    try (CSVReader csvReader =
                 new CSVReader(new FileReader("src/test/resources/testData/cot.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(cot.cot(Double.parseDouble(record[0]), 0.001)).thenReturn(Double.parseDouble(record[1]));
      }
    }
  }

  private static void getCsc() throws IOException, CsvException {
    try (CSVReader csvReader =
                 new CSVReader(new FileReader("src/test/resources/testData/csc.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(csc.csc(Double.parseDouble(record[0]), 0.001)).thenReturn(Double.parseDouble(record[1]));
      }
    }
  }

  @BeforeAll
  public static void fill() throws IOException, CsvException {
    getCos();
    getSin();
    getCsc();
    getCot();
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/cos.csv")
  void cosTest(Double value, Double expected) {
    double result = cos.cos(value, 0.001);
    Assertions.assertEquals(result, expected);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/sin.csv")
  void sinTest(Double value, Double expected) {
    double result = sin.sin(value, 0.001);
    Assertions.assertEquals(result, expected);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/cot.csv")
  void cotTest(Double value, Double expected) {
    double result = cot.cot(value, 0.001);
    Assertions.assertEquals(result, expected);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/csc.csv")
  void cscTest(Double value, Double expected) {
    double result = csc.csc(value, 0.001);
    Assertions.assertEquals(result, expected);
  }
}
