package functional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage {

  public WebDriver driver;
  public ProfilePage(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver; }
  /** определение локатора меню пользователя */
  @FindBy(xpath = "//*[@id=\"ph-whiteline\"]/div/div[2]/div[2]/span[1]/img")
  private WebElement userMenu;
  /**
   * определение локатора кнопки выхода из аккаунта
   */
  @FindBy(xpath = "//div[@id='ph-whiteline']/div/div[2]/div[2]/span[2]")
  private WebElement logoutBtn;

  @FindBy(xpath = "//div[@id='ph-whiteline']/div/div[3]/div/div/div[2]/div[2]")
  private WebElement out;
  /**
   * метод для получения имени пользователя из меню пользователя
   */
  public String getUserName() {
    return userMenu.getAttribute("alt");
  }
  /**
   * метод для нажатия кнопки меню пользователя
   */
//  public void entryMenu() {
//    userMenu.click(); }
  /**
   * метод для нажатия кнопки выхода из аккаунта
   */
  public void userLogout() {
    logoutBtn.click();
    out.click();
  }
}
