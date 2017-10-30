package NS6;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import lib.ExcelDataConfig;
import lib.Login;

public class Create_User_Success {

	@Test(testName = "Create_User_Success")
	public Create_User_Success() throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\SeleniumWebdriver\\chromedriver.exe");
		ExcelDataConfig file = new ExcelDataConfig(
				"D:\\SeleniumWebdriver\\Workspace\\WebdriverBasic\\TestData\\Create-User-Success.xls");

		WebDriver driver = Login.LoginToIEM();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

		WebElement Management_Dropdownlist = lib.getElement.getElementByXpath(driver,
				"//span[@jhitranslate=\"global.menu.management\"]");
		Management_Dropdownlist.click();

		WebElement Management_User = lib.getElement.getElementByXpath(driver,
				"//span[@jhitranslate=\"global.menu.entities.userManagement\"]");
		Management_User.click();
		Thread.sleep(1000);

		for (int i = 1; i <= file.getSheet(0).getLastRowNum(); i++) {
			WebElement Management_User_Create = lib.getElement.getElementByXpath(driver,
					"//span[@jhitranslate=\"webApp.userManagement.home.createNewUser\"]");
			Thread.sleep(1500);
			Management_User_Create.click();
			Thread.sleep(2000);

			System.out.println("Row: " + i);
			String user_name = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(1));
			String display_name = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(2));
			String email = file.getSheet(0).getRow(i).getCell(3).getStringCellValue();
			String organization = file.getSheet(0).getRow(i).getCell(4).getStringCellValue();
			String pass_word = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(5));
			String confirmpass = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(6));
			boolean isActived = file.getSheet(0).getRow(i).getCell(7).getBooleanCellValue();
			Cell resultCell = file.getSheet(0).getRow(i).createCell(9);

			WebElement Username = lib.getElement.getElementById(driver, "field_LoginId");
			Username.clear();
			Username.sendKeys(user_name);

			WebElement Display_name = lib.getElement.getElementById(driver, "field_FullName");
			Display_name.clear();
			Display_name.sendKeys(display_name);

			WebElement Email = lib.getElement.getElementById(driver, "field_Email");
			Email.clear();
			Email.sendKeys(email);

			WebElement Organization = lib.getElement.getElementById(driver, "field_Org");
			Select dropdown = new Select(Organization);
			dropdown.selectByVisibleText(organization);

			WebElement Password = lib.getElement.getElementById(driver, "field_Password");
			Password.clear();
			Password.sendKeys(pass_word);

			WebElement ConfirmPassword = lib.getElement.getElementById(driver, "field_confirmNewPassword");
			ConfirmPassword.clear();
			ConfirmPassword.sendKeys(confirmpass);

			WebElement AccountDeactivation = lib.getElement.getElementById(driver, "field_IsActivated");
			if (isActived) // if Checked
				AccountDeactivation.click();
			Thread.sleep(2000);

			WebElement save_user_button = lib.getElement.getElementByXpath(driver,
					"//span[@jhitranslate=\"entity.action.save\"]");

			try {
				save_user_button.click();
				Thread.sleep(1500);
			} catch (Exception e) {
				// TODO: handle exception
			}

			WebElement notification = driver.findElement(By.name("notificationForm"));
			WebElement TextNoti = notification.findElement(By.className("modal-body"));
			String notificationActual = TextNoti.getText();
			String notificationExpect = file.getSheet(0).getRow(i).getCell(8).getStringCellValue();

			if (lib.EqualCompare.isEqual(notificationActual, notificationExpect)) {
				WebElement ok_button = lib.getElement.getElementByXpath(driver,
						"//span[@jhitranslate=\"entity.action.ok\"]");
				ok_button.click();
				Thread.sleep(1500);

				WebElement back_button = lib.getElement.getElementByXpath(driver,
						"//span[@jhitranslate=\"entity.action.back\"]");
				back_button.click();
				resultCell.setCellValue("PASSED");

			} else {
				resultCell.setCellValue("FAILED");
			}
			System.out.println(">>> Row " + i + "- Created user successfully!");

		}

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\Create-User-Success.xls"));
		lib.ExcelDataConfig.wb.write(outFile);
		outFile.close();
	}

}