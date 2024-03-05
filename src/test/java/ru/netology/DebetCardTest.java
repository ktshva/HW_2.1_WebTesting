package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

class DebetCardTest {
    WebDriver driver;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void afterEach() {
        driver.quit();
    }

    @Test
    void shouldSuccessSent() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Райан Гослинг");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79203520234");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldFailedEngName() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ryan Gosling");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79203520234");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldFailedPhoneNumber() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Райян Гослинг");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7920352023455");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldEmptyName() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79203520234");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNoPhoneNumber() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Райян Гослинг");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldFailedNumberWith8() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Райян Гослинг");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89203520234");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldFailedOneWord() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Гослинг");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79203520234");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldValidName() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ёслинг Райян");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79203520234");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }

    @Test
    void shouldEmptyCheckbox() {

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Райян Гослинг");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79203520234");
        driver.findElement(By.className("button")).click();
        Boolean actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).isEnabled();
        Boolean expected = true;
        assertEquals(expected, actual);
    }
}