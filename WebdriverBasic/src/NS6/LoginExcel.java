package NS6;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;

public class LoginExcel {

	WebDriver driver;

	@Test(dataProvider = "ProjectData")
	public void LoginToWebsite(String user, String pass) throws InterruptedException {
		// System.setProperty("webdriver.gecko.driver",
		// "D:\\Selenium\\geckodriver-v0.19.0-win64\\geckodriver.exe");
		// driver = new FirefoxDriver();
		// driver.manage().window().maximize();
		// //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		// driver.get("http://192.168.7.43:9001/#/");
		//
		//
		// Actions action = new Actions(driver);
		// //Click Account Menu
		// WebElement AccMenu = driver.findElement(By.id("account-menu"));
		// action.moveToElement(AccMenu).perform();
		// AccMenu.click();
		//
		// //Click Login
		// WebElement LoginMenu = driver.findElement(By.id("login"));
		// action.moveToElement(LoginMenu).perform();
		// LoginMenu.click();
		System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\geckodriver-v0.19.0-win64\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();

		driver.manage().window().maximize();

		driver.get("http://192.168.7.43:9001/#/");
		System.out.println("open http://192.168.7.43:9001/#/");
		Thread.sleep(15000);

		Actions action = new Actions(driver);
		WebElement AccMenu = getElementById(driver, "account-menu");

		action.moveToElement(AccMenu).perform();
		AccMenu.click();
		Thread.sleep(1000);
		WebElement LoginMenu = getElementById(driver, "login");
		action.moveToElement(LoginMenu).perform();
		LoginMenu.click();
		Thread.sleep(1000);

		// Input data
		WebElement UserMenu = getElementById(driver, "username");
		WebElement Password = getElementById(driver, "password");
		System.out.println("user=" + user + ", pass=" + pass);
		UserMenu.sendKeys(user);
		Password.sendKeys(pass);
		WebElement LoginButton = getElementByXpath(driver, "//button[@jhitranslate=\"login.form.button\"]");
		LoginButton.click();

		// System.out.println(driver.getTitle());
		// Assert.assertTrue(driver.getTitle().contains("Dashboard"),"User can't login -
		// Invalid");
		// System.out.println("Page Title verified - User can login Successfully");
		//
		LocalStorage localStorage = new LocalStorage(driver);

		String token = localStorage.getItemFromLocalStorage("jhi-authenticationtoken");

		if (token != null && !token.isEmpty()) {
			System.out.println("Page Title verified - User can login Successfully");
//			Actions action1 = new Actions(driver);
//			WebElement AccMenuHome = getElementById(driver,"account-menu");
//			action1.moveToElement(AccMenuHome).perform();
//			AccMenuHome.click();
//			WebElement LogoutMenu = getElementById(driver,"logout");
//			action1.moveToElement(LogoutMenu).perform();
//			LogoutMenu.click();
//			resultCell.setCellValue("PASS");
		} else {
			System.out.println("Page Title verified - User can login failed");
//			resultCell.setCellValue("FAILED");

		}

//		driver.quit();
//		System.out.println("driver.quit();");
//		Thread.sleep(5000);
	}
		
		@Test(dataProvider = "ProjectData1")
		public void LoginToWebsite1(String user, String pass) throws InterruptedException {
			
			System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\geckodriver-v0.19.0-win64\\geckodriver.exe");
			WebDriver driver = new FirefoxDriver();

			driver.manage().window().maximize();

			driver.get("http://192.168.7.43:9001/#/");
			System.out.println("open http://192.168.7.43:9001/#/");
			Thread.sleep(15000);

			Actions action = new Actions(driver);
			WebElement AccMenu = getElementById(driver, "account-menu");

			action.moveToElement(AccMenu).perform();
			AccMenu.click();
			Thread.sleep(1000);
			WebElement LoginMenu = getElementById(driver, "login");
			action.moveToElement(LoginMenu).perform();
			LoginMenu.click();
			Thread.sleep(1000);

			// Input data
			WebElement UserMenu = getElementById(driver, "username");
			WebElement Password = getElementById(driver, "password");
			System.out.println("user=" + user + ", pass=" + pass);
			UserMenu.sendKeys(user);
			Password.sendKeys(pass);
			WebElement LoginButton = getElementByXpath(driver, "//button[@jhitranslate=\"login.form.button\"]");
			LoginButton.click();

			
			//
			LocalStorage localStorage = new LocalStorage(driver);

			String token = localStorage.getItemFromLocalStorage("jhi-authenticationtoken");

			if (token != null && !token.isEmpty()) {
				System.out.println("Page Title verified - User can login Successfully");			
			} else {
				System.out.println("Page Title verified - User can login failed");		

			}
		
		
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

	// @AfterMethod
	// public void tearDown()
	// {
	// // driver.quit();
	// }
	//
	//

	@DataProvider(name = "ProjectData")
	public Object[][] passData() {

		ExcelDataConfig config = new ExcelDataConfig("C:\\Users\\ThanhTT\\eclipse-workspace\\WebdriverBasic\\TestData\\Login.xls");
	
//		int rows = config.getRowCount(0);
		
		int rows = 1;

		Object[][] data = new Object[rows][2];
		for (int i = 0; i < rows; i++) {
//			HSSFSheet sheet = config.getSheet(0);
//			Row row = sheet.getRow(i);
//			Cell resultCell = row.createCell(2);
			data[i][0] = config.getData(0, i, 0);
			data[i][1] = config.getData(0, i, 1);
//			data[i][2] = resultCell;
		}

		return data;
	}

	
	
	@DataProvider(name = "ProjectData1")
	public Object[][] passData1() {

		ExcelDataConfig config = new ExcelDataConfig("C:\\Users\\ThanhTT\\eclipse-workspace\\WebdriverBasic\\TestData\\Login.xls");
	
//		int rows = config.getRowCount(0);
		
		int rows1 = 2;

		Object[][] data = new Object[rows1][2];
		for (int i = 1; i < rows1; i++) {
//			HSSFSheet sheet = config.getSheet(0);
//			Row row = sheet.getRow(i);
//			Cell resultCell = row.createCell(2);
			data[i][0] = config.getData(0, i, 0);
			data[i][1] = config.getData(0, i, 1);
//			data[i][2] = resultCell;
		}

		return data;
	}

	
	
}
