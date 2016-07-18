import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckApartments {
    private static WebDriver driver;
    private static String baseUrl;
    private static boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();

    public static void main(String[] args) {
        driver = new FirefoxDriver();
        baseUrl = "http://mail.gilfondrt.ru/private/auth.php";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        driver.get(baseUrl + "/private/auth.php");
        driver.findElement(By.name("numfile")).clear();
        driver.findElement(By.name("numfile")).sendKeys("1615-001303-110716");
        driver.findElement(By.name("pass")).clear();
        driver.findElement(By.name("pass")).sendKeys("DEBE3358");
        driver.findElement(By.xpath("//input[@value='Подтвердить']")).click();
        driver.findElement(By.linkText(" Добавить квартиры")).click();
        WebElement element = driver.findElement(By.id("select"));
        new Select(element).selectByVisibleText("Поручение №11234 Верхнеуслонский район");

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.name("rty_id")));
        element = driver.findElement(By.name("rty_id"));

        List<WebElement> options = new Select(element).getOptions();
        boolean houseSixFound = false;
        boolean houseEightFound = false;
        for (WebElement webElement : options) {
            if (webElement.getText().contains("№6")) {
                houseSixFound = true;
                break;
            } else if (webElement.getText().contains("№8")) {
                houseEightFound = true;
            }
        }
        Assert.assertTrue(houseEightFound);
        Assert.assertTrue("Шестой дом не найден =(", houseSixFound);

        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }
}
