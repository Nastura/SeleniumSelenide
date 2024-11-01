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

    @Test // Отправка Ф.И. русскими буквами включая букву ё.
    void letterE() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ёжикина Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String expected = driver.findElement(By.className("paragraph")).getAttribute("data-test-id");
        String actual = null;
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка Ф.И. включающая в себя дефисы.
    void hyphen() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кириллова-Угрюмова Алена");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        WebElement form2 = driver.findElement(By.className("App_appContainer__3jRx1"));
        String actual = form2.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка Ф.И. содержащая пробелы.
    void spase() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Нурбела Огли Алена");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        WebElement form2 = driver.findElement(By.className("App_appContainer__3jRx1"));
        String actual = form2.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Ф.И. состоящее из кириллических букв.
    public void cyrillic() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vavilova Anastasiya");
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Ф.И. состоящее из спецсимволов.
    public void specialCharacters() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия.");
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Ф.И. состоящее с буквой - й.
    public void letterTh() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Лукьянов Йосиф");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка формы с незаполненным Ф.И..
    public void nullName() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка формы с незаполненным полем телефона.
    public void nullNumber() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анастасия");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка заявки с заполненным полем только номер телефона.
    public void oneNumber() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка заявки с номером начинающимся с цифры 8.
    public void numberEight() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89655844662");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка заявки с номером состоящим из менее 11 цифр.
    public void lessThan11() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79997377");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка заявки с номером состоящим из более 8 цифр.
    public void moreThan8() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("896558446622");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        String actual = form.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Успешная отправка формы с валидными данными.
    void nextForm() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[role=button]")).click();
        WebElement form2 = driver.findElement(By.className("App_appContainer__3jRx1"));
        String actual = form2.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expected, actual);
    }

    @Test // Отправка формы с незаполненным чек боксом.
    void checkbox() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_size_m"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Вавилова Анастасия");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79655844662");
        form.findElement(By.cssSelector("[role=button]")).click();
        String nameClass = "checkbox_checked";
        Boolean expected = false;
        String actual = form.findElement(By.cssSelector("label")).getAttribute("class");
        Boolean result = actual.contains(nameClass);
        Assertions.assertEquals(expected, result);
    }
}