package NS6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Select;
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
import org.testng.Assert;
import org.testng.annotations.Test;

public class Create_User_Success {

	@Test(testName = "Create_User_Success")
	public Create_User_Success() throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\SeleniumWebdriver\\chromedriver.exe");
		FileInputStream file = new FileInputStream(new File("D:\\Create-User-Success.xls"));
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

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			WebElement Management_User_Create = getElementByXpath(driver,
					"//span[@jhitranslate=\"webApp.userManagement.home.createNewUser\"]");
			Thread.sleep(1500);
			Management_User_Create.click();
			Thread.sleep(2000);

			System.out.println("Row: " + i);
			String user_name = getCellValue(sheet.getRow(i).getCell(1));
			String display_name = getCellValue(sheet.getRow(i).getCell(2));
			String email = sheet.getRow(i).getCell(3).getStringCellValue();
			String organization = sheet.getRow(i).getCell(4).getStringCellValue();
			String pass_word = getCellValue(sheet.getRow(i).getCell(5));
			String confirmpass = getCellValue(sheet.getRow(i).getCell(6));
			boolean isActived = sheet.getRow(i).getCell(7).getBooleanCellValue();
			Cell resultCell = sheet.getRow(i).createCell(9);

			WebElement Username = getElementById(driver, "field_LoginId");
			Username.clear();
			Username.sendKeys(user_name);

			WebElement Display_name = getElementById(driver, "field_FullName");
			Display_name.clear();
			Display_name.sendKeys(display_name);

			WebElement Email = getElementById(driver, "field_Email");
			Email.clear();
			Email.sendKeys(email);

			 WebElement Organization = getElementById(driver, "field_Org");
			 Select dropdown = new Select(Organization);
			 dropdown.selectByVisibleText(organization);

			WebElement Password = getElementById(driver, "field_Password");
			Password.clear();
			Password.sendKeys(pass_word);

			WebElement ConfirmPassword = getElementById(driver, "field_confirmNewPassword");
			ConfirmPassword.clear();
			ConfirmPassword.sendKeys(confirmpass);

			WebElement AccountDeactivation = getElementById(driver, "field_IsActivated");
			if (isActived) // if Checked
				AccountDeactivation.click();
			Thread.sleep(2000);

			WebElement save_user_button = getElementByXpath(driver, "//span[@jhitranslate=\"entity.action.save\"]");
			try {
				save_user_button.click();
				Thread.sleep(1500);
			} catch (Exception e) {
				// TODO: handle exception
			}

			// WebElement notification =
			// driver.findElement(By.xpath(".//*[contains(@name,'notificationForm')]"));
			WebElement notification = driver.findElement(By.name("notificationForm"));
			WebElement TextNoti = notification.findElement(By.className("modal-body"));
			String notificationActual = TextNoti.getText();
			String notificationExpect = sheet.getRow(i).getCell(8).getStringCellValue();

			if (isEqual(notificationActual, notificationExpect)) {
				WebElement ok_button = getElementByXpath(driver, "//span[@jhitranslate=\"entity.action.ok\"]");
				ok_button.click();
				Thread.sleep(1500);

				WebElement back_button = getElementByXpath(driver, "//span[@jhitranslate=\"entity.action.back\"]");
				back_button.click();
				resultCell.setCellValue("PASSED");
				resultCell.setCellStyle(cellStyle);

			} else {
				resultCell.setCellValue("FAILED");
				resultCell.setCellStyle(cellStyle);
			}
			System.out.println(">>> Row " + i + "- Created user successfully!");

		}

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\Create-User-Success.xls"));
		workbook.write(outFile);
		outFile.close();
	}

	private boolean isEqual(String notificationActual, String notificationExpect) {
		boolean isEqual = false;
		try {
			// Verify TC: Display error message: “Wrong password. Try again.”
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
		
		/*if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			double cellValue = cell.getNumericCellValue();
			value = cellValue + "";
		} else {
			
		}*/
		return value;
	}

}