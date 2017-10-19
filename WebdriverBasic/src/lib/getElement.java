package lib;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class getElement {
	public static WebElement getElementById(WebDriver driver, String id) throws InterruptedException {
		WebElement webElement;
		do {
			try {
				webElement = driver.findElement(By.id(id));
			} catch (Exception e) {
				webElement = null;
				System.out.println("unable to get " + id + ". Sleep ...");
				Thread.sleep(1000);
			}
		} while (webElement == null);
		System.out.println("Got webElement=" + webElement);
		return webElement;
	}

	public static WebElement getElementByXpath(WebDriver driver, String xpath) throws InterruptedException {
		WebElement webElement;
		do {
			try {
				webElement = driver.findElement(By.xpath(xpath));
			} catch (Exception e) {
				webElement = null;
				System.out.println("unable to get " + xpath + ". Sleep ...");
				Thread.sleep(1000);
			}
		} while (webElement == null);
		System.out.println("Got webElement=" + webElement);
		return webElement;
	}


}
