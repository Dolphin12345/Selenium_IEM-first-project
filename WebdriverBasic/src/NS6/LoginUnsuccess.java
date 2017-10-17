package NS6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginUnsuccess {

	@Test(testName = "Login Unssuccess")
	public LoginUnsuccess() throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\SeleniumWebdriver\\chromedriver.exe");

		// Locate the path of excel file.
		FileInputStream file = new FileInputStream(new File("D:\\LoginUnsuccess.xls"));

		// Initialize the excel file as a workbook.
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		// Initialize the excel sheet of the workbook
		// 0 (zero) refers to the first sheet of the workbook.
		HSSFSheet sheet = workbook.getSheetAt(0);
		// Create Style for Cell
		HSSFCellStyle cellStyle = workbook.createCellStyle();

		// -----Set normal border for Cell-----
		// cellStyle.setBorderRight(BorderStyle.THIN);
		// cellStyle.setBorderBottom(BorderStyle.THIN);

		WebDriver driver = new ChromeDriver();
		driver.get("http://192.168.7.43:9001/#/");
		driver.manage().window().maximize();
		System.out.println("Open http://192.168.7.43:9001/#/");
		Thread.sleep(1000);

		Actions action = new Actions(driver);
		WebElement AccMenu = getElementById(driver, "account-menu");
		action.moveToElement(AccMenu).perform();
		AccMenu.click();
		Thread.sleep(1000);

		WebElement LoginMenu = getElementById(driver, "login");
		action.moveToElement(LoginMenu).perform();
		LoginMenu.click();
		Thread.sleep(1000);

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			System.out.println("Row: " + i);
			String user = sheet.getRow(i).getCell(1).getStringCellValue();
			String pass = sheet.getRow(i).getCell(2).getStringCellValue();
			Cell resultCell = sheet.getRow(i).createCell(4);

			WebElement username = getElementById(driver, "username");
			username.clear();
			username.sendKeys(user);

			WebElement password = getElementById(driver, "password");
			password.clear();
			password.sendKeys(pass);

			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
			Thread.sleep(1000);
			WebElement LoginButton = getElementByXpath(driver, "//button[@jhitranslate=\"login.form.button\"]");
			try {
				LoginButton.click();
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}

			WebElement Errormsg = driver.findElement(By.xpath(".//*[contains(@class,'alert-dismissible')]"));
			WebElement TextErrormsg = Errormsg.findElement(By.tagName("div"));
			String actualResult = TextErrormsg.getText();
			String expectedResult = sheet.getRow(i).getCell(3).getStringCellValue();

			if (isEqual(actualResult, expectedResult)) {
				resultCell.setCellValue("PASSED");
				resultCell.setCellStyle(cellStyle);
			} else {
				resultCell.setCellValue("FAILED");
				resultCell.setCellStyle(cellStyle);
			}
			System.out
					.println("Row " + i + " - Username: " + user + " / Pass: " + pass + "  >>> Result: " + resultCell);

		}

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\LoginUnsuccess.xls"));
		workbook.write(outFile);
		outFile.close();
	}

	private boolean isEqual(String actualResult, String expectedResult) {
		boolean isEqual = false;
		try {
			// Verify TC: Display error message: “Wrong password. Try again.”
			Assert.assertEquals(actualResult, expectedResult);
			isEqual = true;
		} catch (AssertionError e) {
		}
		return isEqual;
	}

	private WebElement getElementById(WebDriver driver, String id) throws InterruptedException {
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

	private WebElement getElementByXpath(WebDriver driver, String xpath) throws InterruptedException {
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