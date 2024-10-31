import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.LatinException;
import ru.netology.Validator;

public class OrderACardTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    Validator valid = new Validator();

    @Test
        // открытие страницы
    void addSite() {
        driver.get("http://localhost:9999/");
    }

    @Test
        //Ввод Фамилия и Имя русские буквы, дефисы и пробелы.
    void orderACard() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");

    }

    @Test
    public void latinName() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vavilova Anastasiya");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.className("input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
        //ввод телефона
    void telephone() {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
    }

    @Test
        //галочка
    void chitBox() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
    }

    @Test
        // отправка пустой формы
    void next() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[role=button]")).click();
    }

    @Test
        // отправка пустой формы
    void nextForm() throws InterruptedException {
        driver.get("http://localhost:9999/");
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
    }

//    @Test
//    public void validate() throws LatinException {
//        driver.get("http://localhost:9999/");
//        WebElement form = driver.findElement(By.className("form_size_m"));
//        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vavilova Anastasiya");
//        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
//        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
//        String validName = form.findElement(By.className("input__control")).getText();
//        Assertions.assertEquals("Vavilova Anastasiya", validName);
////        Assertions.assertThrows(LatinException.class, () -> valid.validator(validName));
//    }
}