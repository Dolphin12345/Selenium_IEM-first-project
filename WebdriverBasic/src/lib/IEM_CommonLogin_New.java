package lib;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
//import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class IEM_CommonLogin_New {
	WebDriver driver;
	String baseURL = "http://101.99.15.229:4386/#/";

	@BeforeTest
	public void LaunchBrowser() {
		System.setProperty("webdriver.gecko.driver", "D:\\01_Dolphin\\Selenium_Software\\geckodriver.exe");
		String ChromePath = "D:\\01_Dolphin\\Selenium_Software\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", ChromePath);
		driver = new ChromeDriver();
//		The latest Chrome version is ocurring an error which can't open maximum- (Session info: chrome=62.0.3202.94)=> unable to connect to renderer
//		 driver.manage().window().maximize();
		
//		// Firefox browser
//		driver= new FirefoxDriver();
//		driver.manage().window().maximize();
		
//		// IE browser
//		System.out.println("launching IE browser");
//		String driverPath = "D:\\01_Dolphin\\Selenium_Software\\IEDriverServer.exe";
//		System.setProperty("webdriver.ie.driver", driverPath);
//		driver = new InternetExplorerDriver();
//		driver.manage().window().maximize();
		
		driver.get(baseURL);
		System.out.println("This is " + baseURL + " system");
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
	}

	@Test
	public WebDriver LoginToIEM() throws InterruptedException {
		// WebDriver driver = null;
		Actions action = new Actions(driver);
		WebElement AccMenu = getElement.getElementById(driver, "account-menu");
		action.moveToElement(AccMenu).perform();
		AccMenu.click();
		Thread.sleep(1000);

		WebElement LoginMenu = getElement.getElementById(driver, "login");
		action.moveToElement(LoginMenu).perform();
		LoginMenu.click();
		Thread.sleep(1000);
		
		WebElement username = lib.getElement.getElementById(driver, "username");
		username.clear();
		username.sendKeys("admin");

		WebElement password = lib.getElement.getElementById(driver, "password");
		password.clear();
		password.sendKeys("admin");
		
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		Thread.sleep(1000);
		
		WebElement LoginButton = lib.getElement.getElementByXpath(driver, "//button[@jhitranslate=\"login.form.button\"]");
		try {
			LoginButton.click();
			Thread.sleep(1000);
			System.out.println("Login to page successfully!");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return driver;
	}

//	@AfterTest
//	public void aftertest() {
//		driver.quit();
//	}
}
