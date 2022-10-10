package functional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AnsQuestion {
  public WebDriver driver;
  public AnsQuestion(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
  }




}
