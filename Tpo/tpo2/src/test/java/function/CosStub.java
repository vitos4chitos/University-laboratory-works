package function;

import base.Cos;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class CosStub {

  public static final Cos cos = Mockito.mock(Cos.class);

  private static void getCos() throws IOException, CsvException {
    try (CSVReader csvReader =
                 new CSVReader(new FileReader("src/test/resources/testData/cos.csv"))) {
      List<String[]> records = csvReader.readAll();
      for (String[] record : records) {
        when(cos.cos(Double.parseDouble(record[0]), 0.001)).thenReturn(Double.parseDouble(record[1]));
      }
    }
  }

  @BeforeAll
  public static void fill() throws IOException, CsvException {
    getCos();
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/testData/cos.csv")
  void cosTest(Double value, Double expected) {
    double result = cos.cos(value, 0.001);
    Assertions.assertEquals(result, expected);
  }
}
