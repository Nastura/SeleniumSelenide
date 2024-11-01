import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrderACardTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
        // Тест 1 Фамилия и имя
    void orderACard() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        String actual = form.findElement(By.className("input__control")).getAttribute("value");
        String expected = "Вавилова Анастасия";
        Assertions.assertEquals(expected, actual);

    }

    @Test
        // Тест 2 Номер телефона
    void orderACaryd() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79998153516");
        String actual = form.findElement(By.cssSelector("[data-test-id=phone] input")).getAttribute("value");
        String expected = "79998153516";
        Assertions.assertEquals(expected, actual);

    }


    @Test // Тест 3 Ошибка Имя и Фамилия указаные неверно.
    public void latinName() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vavilova Anastasiya");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test //Тест 4 Ошибка Поле обязательно для заполнения"
    public void NullName() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);
    }

    @Test //Тест 5 Ошибка Невалидный номер телефона
    public void InvalidNumber() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79997377");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, actual);
    }

    @Test  // Тест 6 Успешная отправка формы
    void nextForm() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79999999999");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        WebElement form2 = driver.findElement(By.className("App_appContainer__3jRx1"));
        String actual = form2.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }
}