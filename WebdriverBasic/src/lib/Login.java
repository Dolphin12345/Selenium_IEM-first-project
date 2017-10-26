package lib;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import lib.getElement;

public class Login {

	public static WebDriver LoginToIEM() throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("http://101.99.15.229:4386/#/");
		driver.manage().window().maximize();
		System.out.println("Open http://101.99.15.229:4386/#/");
		Thread.sleep(1000);

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
}
