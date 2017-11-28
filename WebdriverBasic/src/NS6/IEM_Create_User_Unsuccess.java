package NS6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IEM_Create_User_Unsuccess {
	@Test(testName = "Create_User_Unsuccess")
	public void IEM_Create_User_Unsuccess() throws Exception {

		System.setProperty("webdriver.gecko.driver", "D:\\01_Dolphin\\Selenium_Software\\geckodriver.exe");
		FileInputStream file = new FileInputStream(new File("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Create_User_Unsuccess.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCellStyle cellStyle = workbook.createCellStyle();

		WebDriver driver = new ChromeDriver();
		driver.get("http://101.99.15.229:4386/#/");
		driver.manage().window().maximize();
		System.out.println("Open http://101.99.15.229:4386/#/");
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

		WebElement username = getElementById(driver, "username");
		username.clear();
		username.sendKeys("admin");

		WebElement password = getElementById(driver, "password");
		password.clear();
		password.sendKeys("admin");

		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		Thread.sleep(1000);
		WebElement LoginButton = getElementByXpath(driver, "//button[@jhitranslate=\"login.form.button\"]");
		try {
			LoginButton.click();
			Thread.sleep(1000);
			System.out.println("Login to page successfully!");
		} catch (Exception e) {
			// TODO: handle exception
		}

		WebElement Management_Dropdownlist = getElementByXpath(driver,
				"//span[@jhitranslate=\"global.menu.management\"]");
		Management_Dropdownlist.click();
		Thread.sleep(1000);

		WebElement Management_User = getElementByXpath(driver,
				"//span[@jhitranslate=\"global.menu.entities.userManagement\"]");
		Management_User.click();
		Thread.sleep(1000);

		WebElement Management_User_Create = getElementByXpath(driver,
				"//span[@jhitranslate=\"webApp.userManagement.home.createNewUser\"]");
		Thread.sleep(1500);
		Management_User_Create.click();
		Thread.sleep(2000);

		WebElement Username = getElementById(driver, "field_LoginId");
		WebElement Display_name = getElementById(driver, "field_FullName");
		WebElement Email = getElementById(driver, "field_Email");
		WebElement Password = getElementById(driver, "field_Password");
		WebElement ConfirmPassword = getElementById(driver, "field_confirmNewPassword");
		
		/*
//Verify User ID
		
		// Case 1: User ID = null
		Username.sendKeys("quyennt");
		Thread.sleep(1000);
		Username.clear();

		WebElement Username1 = driver.findElement(By.name("editForm"));
		WebElement rowUserID = Username1.findElement(By.xpath("//*[contains(@class,'form-group')][1]"));
		WebElement notiUserIDRequired = rowUserID
				.findElement(By.xpath(".//*[@jhitranslate=\"entity.validation.required\"]"));
		String notiactual1 = notiUserIDRequired.getText();
		String notiexpect1 = sheet.getRow(1).getCell(2).getStringCellValue();
		System.out.println("---" + notiactual1 + "---");
//		Cell resultCell = sheet.getRow(1).createCell(3);
		if (isEqual(notiactual1, notiexpect1)) {

			sheet.getRow(1).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 1: UserID = null  >>> PASSED");

		} else {
			sheet.getRow(1).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 1: UserID = null  >>> FAILED");
		}

		// Case 2: LoginID already exists in system
		Username.sendKeys("tham.01");
		Display_name.sendKeys("ThamNt");
		Email.sendKeys("test1810@gmail.com");
		Password.sendKeys("Quyennt@2410");
		ConfirmPassword.sendKeys("Quyennt@2410");
		WebElement save_user_button = getElementByXpath(driver, "//span[@jhitranslate=\"entity.action.save\"]");
		save_user_button.click();
		Thread.sleep(1500);
		
		WebElement Errormsg = driver.findElement(By.xpath(".//*[contains(@class,'alert-dismissible')]"));
		WebElement TextErrormsg = Errormsg.findElement(By.tagName("div"));
		String notiactual2 = TextErrormsg.getText();
		String notiexpect2 = sheet.getRow(2).getCell(2).getStringCellValue();
		if (isEqual(notiactual2, notiexpect2)) {

			sheet.getRow(2).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 2: UserID already exists in system >>> PASSED");

		} else {
			sheet.getRow(2).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 2: UserID already exists in system  >>> FAILED");
		}
		
		// Case 3/4/5: contain special char/ Contain space/ contain Japanese
		for (int i = 3; i <= sheet.getLastRowNum(); i++) {

//			System.out.println("Row: " + i);
			String user_name = getCellValue(sheet.getRow(i).getCell(1));
			Cell resultCell1 = sheet.getRow(i).createCell(3);
			WebElement notiUserID = getElementByXpath(driver, "//small[@jhitranslate=\"entity.validation.loginId\"]");

			Username.clear();
			Username.sendKeys(user_name);
			Thread.sleep(1000);
			String notificationActual = notiUserID.getText();
			System.out.println("---" + notificationActual + "---");
			String notificationExpect = sheet.getRow(i).getCell(2).getStringCellValue();

			if (isEqual(notificationActual, notificationExpect)) {
				resultCell1.setCellValue("PASSED");
				resultCell1.setCellStyle(cellStyle);
				System.out.println(">>> Row " + i + ": UserID =" + user_name + " >>> PASSED");

			} else {
				resultCell1.setCellValue("FAILED");
				resultCell1.setCellStyle(cellStyle);
				System.out.println(">>> Row  " + i + ": UserID =" + user_name + " >>> FAILED");
			}

		}
		
		Username.clear(); 
		
//Verify Display name
		//Case 1: Displayname = null
		Display_name.sendKeys("quyennt");
		Thread.sleep(1000);
		Display_name.clear();
	
		////////////////////////////////
		List<WebElement> formRow = driver.findElements(By.className("form-group"));
		WebElement eUserName = formRow.get(0);
		WebElement eRequired = eUserName.findElement(By.xpath("//*[@jhitranslate='entity.validation.required']"));
		WebElement eSpan = eRequired.findElement(By.tagName("span"));
		System.out.println("(.)(.) " + eSpan.getText());
		////////////////////////////////

//		WebElement Displayname1 = driver.findElement(By.name("editForm"));
		WebElement rowDisplayname = driver.findElement(By.xpath("//*[contains(@class,'form-group')][2]"));
		WebElement notiDisplaynameNotNull = rowDisplayname
				.findElement(By.xpath(".//small[@jhitranslate=\"entity.validation.required\"]"));
		String notiDisplaynameActual = notiDisplaynameNotNull.getText();
		HSSFSheet sheet2 = workbook.getSheetAt(1);
		String notiexpect3 = sheet2.getRow(1).getCell(2).getStringCellValue();
		System.out.println("---" + notiDisplaynameActual + "---");
		if (isEqual(notiDisplaynameActual, notiexpect3)) {

			sheet.getRow(1).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 1: Display Name = null  >>> PASSED");

		} else {
			sheet.getRow(1).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 1: Display Name = null  >>> FAILED");
		}
		Display_name.clear(); */
		
		
//////////////////// Verify Email//////////////////////////////////////////////////////////////////////////////////////////////////
		// case 1: Email = null
		HSSFSheet sheet3 = workbook.getSheetAt(2);
		Email.sendKeys("quyennt");
		Thread.sleep(1000);
		Email.clear();

		WebElement notiEmailNull = getElementByXpath(driver, "//small[@jhitranslate=\"entity.validation.email\"]");
		String notiactual1 = notiEmailNull.getText();
		String notiexpect1 = sheet3.getRow(1).getCell(2).getStringCellValue();
		System.out.println("---" + notiactual1 + "---");
//		Cell resultCell = sheet.getRow(1).createCell(3);
		if (isEqual(notiactual1, notiexpect1)) {

			sheet3.getRow(1).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 1: Email = null  >>> PASSED");

		} else {
			sheet3.getRow(1).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 1: Email = null  >>> FAILED");
		}  
		
		// Case next 
		
		for (int j = 2; j <= sheet3.getLastRowNum(); j++) {

//			System.out.println("Row: " + i);
			String email = getCellValue(sheet3.getRow(j).getCell(1));
			Cell resultCell = sheet3.getRow(j).createCell(3);
			WebElement notiEmail = getElementByXpath(driver, "//small[@jhitranslate=\"entity.validation.email\"]");

			Email.clear();
			Email.sendKeys(email);
			Thread.sleep(1000);
			String notificationActual = notiEmail.getText();
			System.out.println("---" + notificationActual + "---");
			String notificationExpect = sheet3.getRow(j).getCell(2).getStringCellValue();

			if (isEqual(notificationActual, notificationExpect)) {
				resultCell.setCellValue("PASSED");
				resultCell.setCellStyle(cellStyle);
				System.out.println(">>> Row " + j + ": Email =" + email + " >>> PASSED");

			} else {
				resultCell.setCellValue("FAILED");
				resultCell.setCellStyle(cellStyle);
				System.out.println(">>> Row  " + j + ": Email =" + email + " >>> FAILED");
			}

		}
		
		Email.clear(); 
		

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Create_User_Unsuccess.xls"));
		workbook.write(outFile);
		outFile.close();
	}

	private boolean isEqual(String notificationActual, String notificationExpect) {
		boolean isEqual = false;
		try {
			// Verify TC: Display error message
			Assert.assertEquals(notificationActual, notificationExpect);
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

	private String getCellValue(HSSFCell cell) {
		String value = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			value = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			value = cell.getCellFormula();
			break;
		default:
			value = cell.getStringCellValue();
			break;
		}

		/*
		 * if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) { double
		 * cellValue = cell.getNumericCellValue(); value = cellValue + ""; }
		 * else {
		 * 
		 * }
		 */
		return value;
	}

}
