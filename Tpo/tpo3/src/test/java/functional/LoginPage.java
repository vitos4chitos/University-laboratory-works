package functional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
  @FindBy(xpath = "//input[@name='username']")
  private WebElement loginField;

  @FindBy(xpath = "//*[@id=\"ph-whiteline\"]/div/div[2]/button")
  private WebElement inBtn;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div[2]/div/div/form/div[2]/div/div[3]/div/div/div[1]/button")
  private WebElement loginBtn;

  @FindBy(
      xpath =
          "//div[@id='root']/div/div/div/div[2]/div/div/form/div[2]/div/div[3]/div/div/div/div/button/span")
  private WebElement goBtn;
  @FindBy(
      xpath =
          "//input[@name='password']")
  private WebElement passwdField;

  public void inputLogin(String login) {
    loginField.sendKeys(login); }

  public void inputPasswd(String passwd) {
    passwdField.sendKeys(passwd); }

  public void clickLoginIn() {
    inBtn.click(); }

  public void clickLoginBtn() {
    loginBtn.click(); }

  public void clickLoginGo() {
    goBtn.click(); }

  public WebDriver driver;
  public LoginPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver; }
}
