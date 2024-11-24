package testCasesAll;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;

/* Author - Imanur Ali
 * Date - Nov 25, 2024
 * Time - 02:50 AM 
 */

public class TestFitpeo {
	
	WebDriver driver;
	WebElement bottomTextField;
	
	@BeforeTest(alwaysRun = true)
	public void beforeClass() {
		  WebDriverManager.firefoxdriver().setup();
		  driver = new FirefoxDriver();
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
	}
	
	@Test(priority = 0)
  	public void navigateHomepage() {
		driver.get("https://www.fitpeo.com/");
	}
	
	@Test(priority = 1)
	public void navigateRevenueCalculatorPage() {
		WebElement revenueCalculatorLink = driver.findElement(By.linkText("Revenue Calculator"));
		revenueCalculatorLink.click();	
	}
	
	@Test(priority = 2, dependsOnMethods = "navigateRevenueCalculatorPage")
	public void scrollDownAndSetSlider() {  
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,300)");
		WebElement slider = driver.findElement(By.xpath("//span[@class='MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary css-1sfugkh']"));
		Actions actions = new Actions(driver);
		actions.dragAndDropBy(slider, 94, 0).release().perform();	
		
	}
	
	@Test(priority = 3, dependsOnMethods = "scrollDownAndSetSlider")
	public void changeSliderValue() {
		
		bottomTextField = driver.findElement(By.xpath("//input[@type='number']"));
		bottomTextField.click();
		bottomTextField.clear();
		bottomTextField.sendKeys("560");
	}
	
	@Test(priority = 4, dependsOnMethods = "changeSliderValue")
	public void scrollDownAndselectCheckboxes() {
		
		bottomTextField.clear();
		bottomTextField.sendKeys("820");
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,500)");
		
		WebElement checkboxCPT_99091 = driver.findElement(By.xpath("//div[@class='MuiBox-root css-rfiegf']//div[1]//label[1]//span[1]//input[1]"));
		WebElement checkboxCPT_99453 = driver.findElement(By.xpath("//div[@class='MuiBox-root css-1p19z09']//div[2]//label[1]//span[1]//input[1]"));
		WebElement checkboxCPT_99454 = driver.findElement(By.xpath("//div[3]//label[1]//span[1]//input[1]"));
		WebElement checkboxCPT_99474 = driver.findElement(By.xpath("//div[8]//label[1]//span[1]//input[1]"));
		
		checkboxCPT_99091.click();
		checkboxCPT_99453.click();
		checkboxCPT_99454.click();
		
		jse.executeScript("window.scrollBy(0,300)");
		checkboxCPT_99474.click();
		
		WebElement totalRecurring = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body2 inter css-1xroguk'][contains(text(),'Total Recurring Reimbursement for all Patients Per')]"));
		String expected = "Total Recurring Reimbursement for all Patients Per Month:\n"
				+ "$110700";
		String actual = totalRecurring.getText();
		
		Assert.assertEquals(actual, expected);
	}

		@AfterTest(alwaysRun = true)
		
		public void afterClass() {
			driver.close();
		}

}
