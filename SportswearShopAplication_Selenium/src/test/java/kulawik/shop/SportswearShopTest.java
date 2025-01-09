package kulawik.shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class SportswearShopTest {
    private WebDriver driver;
    @BeforeEach
    void setUp() {
        // Ustawienie ścieżki do sterownika ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium-drivers\\Chrome\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8000/");
    }

    @Test
   public  void testRightTitle() {
        String title = driver.getTitle();
        assertEquals(title, "Sportswear Shop");
    }
    @Test
    public  void testProductPresence() {
        List<WebElement> products=driver.findElements(By.className("product"));
        assertFalse(products.isEmpty(), "Produkty nie zostały odnaalezione");
    }
    @Test
    public  void testFilterProducts()throws InterruptedException {
        WebElement filter = driver.findElement(By.id("priceFilter"));
        filter.sendKeys("Below 100 PLN");
        Thread.sleep(2000);

        List<WebElement> products =driver.findElements(By.className("product"));
        for(WebElement product: products){
            if(product.isDisplayed()){
                String priceText= driver.findElement(By.xpath(".//p[2]")).getText();
                int price = Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
                assertTrue(price < 100, "Produkt powyżej 100 PLN widoczny.");
            }
        }
    }

    @Test
    public void testContactForm() {
        driver.findElement(By.linkText("Contact")).click();
        driver.findElement(By.id("name")).sendKeys("Jan Kowalski");
        driver.findElement(By.id("email")).sendKeys("jan.kowalski@example.com");
        driver.findElement(By.id("message")).sendKeys("To jest testowa wiadomość.");
        driver.findElement(By.cssSelector("form button")).click();
    }


    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}