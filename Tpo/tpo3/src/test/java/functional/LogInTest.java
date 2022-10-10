package functional;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LogInTest {

  public static LoginPage loginPage;
  public static ProfilePage profilePage;
  public static WebDriver driver;
  public static Question question;

  @BeforeClass
  public static void setup() {
    System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    // задержка на выполнение теста = 10 сек.
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.get(ConfProperties.getProperty("loginpage"));

    loginPage = new LoginPage(driver);
    profilePage = new ProfilePage(driver);
    question = new Question(driver);

  }

  @Test
  public void loginTest() {

    loginPage.clickLoginIn();
    loginPage.driver.switchTo().frame(2);
    loginPage.inputLogin(ConfProperties.getProperty("username"));
    loginPage.clickLoginBtn();
    loginPage.inputPasswd(ConfProperties.getProperty("password"));
    loginPage.clickLoginGo();
    loginPage.driver.switchTo().frame(1);
    loginPage.driver.switchTo().window(loginPage.driver.getWindowHandle());
    String user = profilePage.getUserName();
    Assert.assertEquals(ConfProperties.getProperty("username") + "@mail.ru", user);
  }

  @Test
  public void questionTest() {
    driver.get(ConfProperties.getProperty("loginpage"));
    question.clickToTopQuestion();
    question.clickToQuestion();
    Assert.assertEquals("Почему уважать себя сложно, но нужно?", question.getQuestionName());
  }

  @Test
  public void likeTest() {
    driver.get(ConfProperties.getProperty("loginpage"));
    question.clickToTopQuestion();
    question.clickToQuestionWhatILike();
    question.clickToLike();
    int prev = Integer.parseInt(question.whoLikesSee());
    //System.out.println(question.whoLikesSee());
    question.clickToLike();
    question.clickToLike();
    int post = Integer.parseInt(question.whoLikesSee());
    Assert.assertEquals(1, Math.abs(prev - post));
  }

  @Test
  public void askQuestion() {
    question.clickAskQuestion();
    question.inputQuestionText("Как писать функциональные тесты на Java при использовании Selenium ?");
    question.inputAddInfo("Я использую веб драйвер для гугл хром");
    //question.setCategory();
    //question.setClickToCategory();
    //question.setSubCategory();
    //question.setClickToSubCategory();
    question.clickButtonToPost();
  }

  @Test
  public void ansQuestion() {
    //driver.get(ConfProperties.getProperty("loginpage"));
    question.clickToTopQuestion();
  }
  @AfterClass
    public static void tearDown() {
      driver.quit();
    }
}
