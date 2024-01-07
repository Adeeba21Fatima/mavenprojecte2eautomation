package SeleniumAutomationFrameworkDesign.SeleniumAutomationFramework;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.Window;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {
	public static void main(String[] args)  {
		try {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("adeeba21fatima@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("adeebaF786$");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".mb-3"))));

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.tagName("b")).getText().equalsIgnoreCase("ZARA COAT 3"))
				.findFirst().orElse(null);
		System.out.println(prod.getText());
		String name = "ZARA COAT 3";
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("toast-container"))));
		Thread.sleep(5);
		Actions action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("button[routerlink*='/dashboard/cart']"))));
		WebElement element = driver.findElement(By.cssSelector("button[routerlink*='/dashboard/cart']"));
		action.moveToElement(element).click().build().perform();
		element.click();
		List<WebElement> cartItems = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match=  cartItems.stream().anyMatch(item -> item.getText().equalsIgnoreCase(name));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[placeholder='Select Country']"))));
		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("india");
		Thread.sleep(5000);
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,900)", "");
		driver.findElement(By.cssSelector(".action__submit")).click();
		String message = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(message.equalsIgnoreCase("Thankyou for the order."));
		driver.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
}
