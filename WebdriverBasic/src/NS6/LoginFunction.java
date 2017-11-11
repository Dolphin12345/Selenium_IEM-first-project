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

public class LoginFunction {

	private WebDriver driver;
	ExcelDataConfig file = new ExcelDataConfig("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Login.xls");

	public void initForm() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "D:\\01_Dolphin\\Selenium_Software\\geckodriver.exe");
//		System.setProperty("webdriver.gecko.driver", "D:\\01_Dolphin\\Selenium_Software\\chromedriver.exe");
		String exePath = "D:\\01_Dolphin\\Selenium_Software\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		
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
	}

	@Test(testName = "Login Success")
	public void loginSuccess() throws Exception {
		for (int i = 1; i <= 5; i++) {
			initForm();
			Thread.sleep(1000);

			WebElement username = driver.findElement(By.id("username"));
			WebElement password = driver.findElement(By.id("password"));
			WebElement LoginButton = driver.findElement(By.xpath("//button[@jhitranslate=\"login.form.button\"]"));

			String user = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(1));
			String pass = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(2));
			Cell resultCell = file.getSheet(0).getRow(i).createCell(4);

			username.clear();
			username.sendKeys(user);
			Thread.sleep(1000);

			password.clear();
			password.sendKeys(pass);
			Thread.sleep(1000);

			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
			Thread.sleep(1000);

			try {
				LoginButton.click();
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}

			LocalStorage localStorage = new LocalStorage(driver);
			String token = localStorage.getItemFromLocalStorage("jhi-authenticationtoken");
			if (token != null && !token.isEmpty()) {
				resultCell.setCellValue("PASSED");

			} else {
				resultCell.setCellValue("FAILED");

			}
			driver.close();
			System.out.println("Row " + i + " - Username: " + user + " / Pass: " + pass + " >>>Result: " + resultCell);
		}

	} 

	@Test(testName = "Login Unssuccess")
	public void loginUnsuccess() throws Exception {
		initForm();
		System.out.println("Last Num row: " + file.getSheet(0).getLastRowNum());
		for (int i = 6; i <= file.getSheet(0).getLastRowNum(); i++) {
			WebElement username = driver.findElement(By.id("username"));
			WebElement password = driver.findElement(By.id("password"));
			WebElement LoginButton = driver.findElement(By.xpath("//button[@jhitranslate=\"login.form.button\"]"));

			String user = file.getSheet(0).getRow(i).getCell(1).getStringCellValue();
			String pass = file.getSheet(0).getRow(i).getCell(2).getStringCellValue();
			Cell resultCell = file.getSheet(0).getRow(i).createCell(4);
			String expectedResult = file.getSheet(0).getRow(i).getCell(3).getStringCellValue();

			username.clear();
			username.sendKeys(user);

			password.clear();
			password.sendKeys(pass);

			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
			Thread.sleep(1000);

			try {
				LoginButton.click();
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}

			WebElement Errormsg = driver.findElement(By.xpath(".//*[contains(@class,'alert-dismissible')]"));
			WebElement TextErrormsg = Errormsg.findElement(By.tagName("div"));
			String actualResult = TextErrormsg.getText();

			if (lib.EqualCompare.isEqual(actualResult, expectedResult)) {
				resultCell.setCellValue("PASSED");
			} else {
				resultCell.setCellValue("FAILED");
			}
			System.out
					.println("Row " + i + " - Username: " + user + " / Pass: " + pass + "  >>> Result: " + resultCell);

		}

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Login.xls"));
		lib.ExcelDataConfig.wb.write(outFile);
		outFile.close();
	}

}
