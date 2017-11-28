package NS6;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import lib.ExcelDataConfig;
import lib.IEM_CommonLogin_New;

public class IEM_Create_User_Unsuccess_New {
	@Test(testName = "Create_User_Unsuccess")
	public void Create_User_Unsuccess_New() throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\01_Dolphin\\Selenium_Software\\geckodriver.exe");
		ExcelDataConfig file = new ExcelDataConfig("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Create_User_Unsuccess.xls");
		
		// Call from [IEM_CommonLogin_New] class
		IEM_CommonLogin_New driverCommonLogin = new IEM_CommonLogin_New ();
		driverCommonLogin.LaunchBrowser();
		WebDriver driver = driverCommonLogin.LoginToIEM();
				
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		WebElement Management_Dropdownlist = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"global.menu.management\"]");
		Management_Dropdownlist.click();
		Thread.sleep(1000);

		WebElement Management_User = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"global.menu.entities.userManagement\"]");
		Management_User.click();
		Thread.sleep(1000);

		for (int i = 1; i <= file.getSheet(0).getLastRowNum(); i++) {
			WebElement Management_User_Create = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"webApp.userManagement.home.createNewUser\"]");
			Thread.sleep(1500);
			Management_User_Create.click();
			Thread.sleep(2000);

			String fUserName = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(1));
			String fDisplayName = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(2));
			String fEmail = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(3));
			String fPassword = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(4));
			String fConfirmPassword = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(5));
			String fMessageExpect = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(6));
			Cell fResult = file.getSheet(0).getRow(i).createCell(7);

			WebElement eUsername = lib.getElement.getElementById(driver, "field_LoginId");
			WebElement eDisplayName = lib.getElement.getElementById(driver, "field_FullName");
			WebElement eEmail = lib.getElement.getElementById(driver, "field_Email");
			WebElement ePassword = lib.getElement.getElementById(driver, "field_Password");
			WebElement eConfirmPassword = lib.getElement.getElementById(driver, "field_confirmNewPassword");
			WebElement eCancelBtn = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"entity.action.cancel\"]");
			WebElement eSaveBtn = driver.findElement(By.xpath(".//*[contains(@class,'btn-primary')]"));
			eUsername.sendKeys(fUserName);
			eDisplayName.sendKeys(fDisplayName);
			eEmail.sendKeys(fEmail);
			ePassword.sendKeys(fPassword);
			eConfirmPassword.sendKeys(fConfirmPassword);
			Thread.sleep(1000);

			// Get list alert messages
			String textMessage = null;
			List<WebElement> listElement = driver.findElements(By.xpath(".//*[contains(@class,'form-group')]/div/div/small"));

			// Get alert message display
			for (WebElement e : listElement) {
				String eMessageDisplay = e.getAttribute("hidden");
				if (eMessageDisplay == null) {
					WebElement message = e.findElement(By.tagName("span"));
					textMessage = message.getText();
					System.out.println("==============" + textMessage + "=============");
				}

			}

			// Get Attribute "disabled"
			String eSaveBtnDisable = eSaveBtn.getAttribute("disabled");

			//User ID exists in DB, in this case, need submit DATA to server
			if (i == 2) {
				eSaveBtn.click();
				WebElement eAlertDismissible = driver.findElement(By.xpath(".//*[contains(@class,'alert-dismissible')]/div"));
				String eTextAlert = eAlertDismissible.getText();
				if (eSaveBtnDisable == null && lib.EqualCompare.isEqual(eTextAlert, fMessageExpect)) {
					System.out.println("--------------" + eSaveBtnDisable + "----------------");
					fResult.setCellValue("PASSED");
					System.out.println(">>> Row " + i + ": >>> PASSED");
					Thread.sleep(2000);
					eCancelBtn.click();
					Thread.sleep(1000);
				} else {
					System.out.println("--------------" + eSaveBtnDisable + "----------------");
					fResult.setCellValue("FAILED");
					System.out.println(">>> Row " + i + " >>> FAILED");
					Thread.sleep(2000);
					eCancelBtn.click();
					Thread.sleep(1000);
				}

			}

			if (i != 2) {
				if (eSaveBtnDisable != null && lib.EqualCompare.isEqual(textMessage, fMessageExpect)) {

					System.out.println("--------------" + eSaveBtnDisable + "----------------");
					fResult.setCellValue("PASSED");
					System.out.println(">>> Row " + i + ": >>> PASSED");
					Thread.sleep(2000);
					eCancelBtn.click();
					Thread.sleep(1000);
				} else {
					System.out.println("--------------" + eSaveBtnDisable + "----------------");
					fResult.setCellValue("FAILED");
					System.out.println(">>> Row " + i + " >>> FAILED");
					Thread.sleep(2000);
					eCancelBtn.click();
					Thread.sleep(1000);
				}

			}
		}

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Create_User_Unsuccess.xls"));
		lib.ExcelDataConfig.wb.write(outFile);
		outFile.close();
	}
}
