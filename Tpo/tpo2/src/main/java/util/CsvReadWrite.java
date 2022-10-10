package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class CsvReadWrite {
  private String filePath = "";

  public void setFilePath(String fileName) {
    this.filePath = fileName;
  }

  public void print(double x, double y) throws FileNotFoundException {
    try (PrintStream printStream = new PrintStream(new FileOutputStream(filePath, true))) {
      printStream.printf("%s, %s \n", x, y);
    }
  }
}
