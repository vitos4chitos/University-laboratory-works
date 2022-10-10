package function;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import mainFunction.MainFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class ModuleMainStub {
  private static final MainFunction mainFunction = Mockito.mock(MainFunction.class);

  private static void getMain() throws IOException, CsvException {
    try (CSVReader csvReader =
                 new CSVReader(new FileReader("src/test/resources/testData/main.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(mainFunction.calculate(Double.parseDouble(record[0]), 0.001)).thenReturn(Double.parseDouble(record[1]));
      }
    }
  }

  @BeforeAll
  public static void fill() throws IOException, CsvException {
    getMain();
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/main.csv")
  void cosTest(Double value, Double expected) {
    double result = mainFunction.calculate(value, 0.001);
    Assertions.assertEquals(result, expected);
  }
}
