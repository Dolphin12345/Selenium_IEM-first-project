package NS6;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import lib.ExcelDataConfig;
import lib.getElement;

public class LoginUnsuccess {

	@Test(testName = "Login Unssuccess")
	public LoginUnsuccess() throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\SeleniumWebdriver\\chromedriver.exe");
		ExcelDataConfig file = new ExcelDataConfig("D:\\LoginUnsuccess.xls");

		WebDriver driver = new ChromeDriver();
		driver.get("http://101.99.15.229:4386/#/");
		driver.manage().window().maximize();
		System.out.println("Open http://101.99.15.229:4386/#/");
		Thread.sleep(1000);

		Actions action = new Actions(driver);
		WebElement AccMenu = lib.getElement.getElementById(driver, "account-menu");
		action.moveToElement(AccMenu).perform();
		AccMenu.click();
		Thread.sleep(1000);

		WebElement LoginMenu = lib.getElement.getElementById(driver, "login");
		action.moveToElement(LoginMenu).perform();
		LoginMenu.click();
		Thread.sleep(1000);

		// Login.LoginToIEM(driver);

		for (int i = 1; i <= file.getSheet(0).getLastRowNum(); i++) {
			System.out.println("Row: " + i);
			String user = file.getSheet(0).getRow(i).getCell(1).getStringCellValue();
			String pass = file.getSheet(0).getRow(i).getCell(2).getStringCellValue();
			Cell resultCell = file.getSheet(0).getRow(i).createCell(4);

			WebElement username = getElement.getElementById(driver, "username");
			username.clear();
			username.sendKeys(user);

			WebElement password = getElement.getElementById(driver, "password");
			password.clear();
			password.sendKeys(pass);

			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
			Thread.sleep(1000);
			WebElement LoginButton = getElement.getElementByXpath(driver,
					"//button[@jhitranslate=\"login.form.button\"]");
			try {
				LoginButton.click();
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}

			WebElement Errormsg = driver.findElement(By.xpath(".//*[contains(@class,'alert-dismissible')]"));
			WebElement TextErrormsg = Errormsg.findElement(By.tagName("div"));
			String actualResult = TextErrormsg.getText();
			String expectedResult = file.getSheet(0).getRow(i).getCell(3).getStringCellValue();

			if (lib.EqualCompare.isEqual(actualResult, expectedResult)) {
				resultCell.setCellValue("PASSED");
			} else {
				resultCell.setCellValue("FAILED");
			}
			System.out
					.println("Row " + i + " - Username: " + user + " / Pass: " + pass + "  >>> Result: " + resultCell);

		}

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\LoginUnsuccess.xls"));
		lib.ExcelDataConfig.wb.write(outFile);
		outFile.close();
	}

}
