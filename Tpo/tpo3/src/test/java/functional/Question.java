package functional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Locale;

public class Question {
  @FindBy(xpath = "//div[3]/span[2]")
  private WebElement topQuestions;

  @FindBy(xpath = "//a[contains(text(),'Почему уважать себя сложно, но нужно?')]")
  private WebElement question;

  @FindBy(css = ".z7Rgh")
  private WebElement currentQuestion;

  @FindBy(xpath = "//a[contains(@href, '/question/228793132')]")
  private WebElement questionForLike;

  @FindBy(css = ".m3I3i > .icAW3 > div path")
  private WebElement like;

  @FindBy(css = ".vyeZZ > path")
  private WebElement notLike;

  @FindBy(css = ".w2Gqj path")
  private WebElement check1;

  @FindBy(css = "css=.w2Gqj > svg")
  private WebElement check2;

  @FindBy(css = ".AhoWl")
  private WebElement whoLikes;

  @FindBy(css = ".iy9DJ:nth-child(1) > .v5Cxe")
  private WebElement goBack;

  @FindBy(xpath = "//div[2]/a/div")
  private WebElement askQuestion;

  @FindBy(xpath = "//textarea[@id='question_text']")
  private WebElement questionText;

  @FindBy(xpath = "//div[@id='question_additional']/div/div")
  private WebElement addInfoQuestion;

  @FindBy(css = ".icAW3")
  private WebElement buttonQuestion;

  @FindBy(xpath = "//span[contains(.,'Выберите категорию')]")
  private WebElement category;

  @FindBy(xpath = "//div[12]/div/span")
  private WebElement clickToCategory;

  @FindBy(xpath = "//span[contains(.,'Выберите подкатегорию')]")
  private WebElement subCategory;

  @FindBy(css = ".IgotB:nth-child(6) .u2mHx")
  private WebElement clickToSubCategory;

  public void setCategory() {
    category.click();
  }

  public void setClickToCategory() {
    category.click();
  }

  public void setSubCategory() {
    category.click();
  }

  public void setClickToSubCategory() {
    category.click();
  }

  public void clickButtonToPost() {
    buttonQuestion.click();
  }

  public void inputQuestionText(String text) {
    questionText.sendKeys(text);
  }

  public void inputAddInfo(String text) {
    addInfoQuestion.sendKeys(text);
  }

  public void clickAskQuestion() {
    askQuestion.click();
  }

  public void setGoBack() {
    goBack.click();
  }

  public void setCheck2() {
    check2.click();
  }
  public void setNotLike() {
    notLike.click();
  }

  public void setCheck1() {
    check1.click();
  }
  public void clickToTopQuestion() {
    topQuestions.click();
  }

  public void clickToQuestion() {
    question.click();
  }

  public void clickToQuestionWhatILike() {
    questionForLike.click();
  }

  public String whoLikesSee() {
    return whoLikes.getText();
  }

  public void clickToLike() {
    like.click();
  }

  public String getQuestionName() {
    return currentQuestion.getText();
  }



  public WebDriver driver;
  public Question(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver; }
}
