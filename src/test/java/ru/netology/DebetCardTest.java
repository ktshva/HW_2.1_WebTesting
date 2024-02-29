package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebetCardTest {
    WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        //WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupAll() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSuccessSent() {

        driver.get("http://127.0.0.1:9999");

        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Райан Гослинг");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79203520234");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }
}