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
import lib.Login;

public class Create_User_Unsuccess {
	@Test(testName = "Create_User_Unsuccess")
	public Create_User_Unsuccess() throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\SeleniumWebdriver\\chromedriver.exe");
		ExcelDataConfig file = new ExcelDataConfig("D:\\SeleniumWebdriver\\Workspace\\WebdriverBasic\\TestData\\Create-User-Success.xls");
		WebDriver driver = Login.LoginToIEM();	
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

		WebElement Management_Dropdownlist = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"global.menu.management\"]");
		Management_Dropdownlist.click();
		Thread.sleep(1000);

		WebElement Management_User = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"global.menu.entities.userManagement\"]");
		Management_User.click();
		Thread.sleep(1000);

		WebElement Management_User_Create = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"webApp.userManagement.home.createNewUser\"]");
		Thread.sleep(1500);
		Management_User_Create.click();
		Thread.sleep(2000);

		WebElement Username = lib.getElement.getElementById(driver, "field_LoginId");
		WebElement Display_name = lib.getElement.getElementById(driver, "field_FullName");
		WebElement Email = lib.getElement.getElementById(driver, "field_Email");
		WebElement Password = lib.getElement.getElementById(driver, "field_Password");
		WebElement ConfirmPassword = lib.getElement.getElementById(driver, "field_confirmNewPassword");
		
		
//Verify User ID
		
		// Case 1: User ID = null
		Username.sendKeys("quyennt");
		Thread.sleep(1000);
		Username.clear();
		Thread.sleep(1500);
		
		List<WebElement> list = driver.findElements(By.xpath(".//*[contains(@class,'form-group')]"));
		WebElement pageOne = list.get(0);
//		WebElement Username1 = driver.findElement(By.className("modal-body"));
//		List<WebElement> rowUserID = pageOne.findElement(By.xpath("//*[contains(@class,'form-text')]"));
		
		WebElement notiUserIDRequired = pageOne.findElement(By.xpath(".//*[@jhitranslate=\"entity.validation.required\"]"));
		String notiactual1 = notiUserIDRequired.getText();
		String notiexpect1 = file.getSheet(0).getRow(1).getCell(2).getStringCellValue();
		System.out.println("---" + notiactual1 + "---");
//		Cell resultCell = sheet.getRow(1).createCell(3);
		if (lib.EqualCompare.isEqual(notiactual1, notiexpect1)) {

			file.getSheet(0).getRow(1).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 1: UserID = null  >>> PASSED");

		} else {
			file.getSheet(0).getRow(1).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 1: UserID = null  >>> FAILED");
		}

		// Case 2: LoginID already exists in system
		Username.sendKeys("tham.01");
		Display_name.sendKeys("ThamNt");
		Email.sendKeys("test1810@gmail.com");
		Password.sendKeys("Quyennt@2410");
		ConfirmPassword.sendKeys("Quyennt@2410");
		WebElement save_user_button = lib.getElement.getElementByXpath(driver, "//span[@jhitranslate=\"entity.action.save\"]");
		save_user_button.click();
		Thread.sleep(1500);
		
		WebElement Errormsg = driver.findElement(By.xpath(".//*[contains(@class,'alert-dismissible')]"));
		WebElement TextErrormsg = Errormsg.findElement(By.tagName("div"));
		String notiactual2 = TextErrormsg.getText();
		String notiexpect2 = file.getSheet(0).getRow(2).getCell(2).getStringCellValue();
		if (lib.EqualCompare.isEqual(notiactual2, notiexpect2)) {

			file.getSheet(0).getRow(2).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 2: UserID already exists in system >>> PASSED");

		} else {
			file.getSheet(0).getRow(2).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 2: UserID already exists in system  >>> FAILED");
		}
		
		// Case 3/4/5: contain special char/ Contain space/ contain Japanese
		for (int i = 3; i <= file.getSheet(0).getLastRowNum(); i++) {

//			System.out.println("Row: " + i);
			String user_name = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(1));
			Cell resultCell1 = file.getSheet(0).getRow(i).createCell(3);
			WebElement notiUserID = lib.getElement.getElementByXpath(driver, "//small[@jhitranslate=\"entity.validation.loginId\"]");

			Username.clear();
			Username.sendKeys(user_name);
			Thread.sleep(1000);
			String notificationActual = notiUserID.getText();
			System.out.println("---" + notificationActual + "---");
			String notificationExpect = file.getSheet(0).getRow(i).getCell(2).getStringCellValue();

			if (lib.EqualCompare.isEqual(notificationActual, notificationExpect)) {
				resultCell1.setCellValue("PASSED");
//				resultCell1.setCellStyle(cellStyle);
				System.out.println(">>> Row " + i + ": UserID =" + user_name + " >>> PASSED");

			} else {
				resultCell1.setCellValue("FAILED");
//				resultCell1.setCellStyle(cellStyle);
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
		String notiexpect3 = file.getSheet(1).getRow(1).getCell(2).getStringCellValue();
		System.out.println("---" + notiDisplaynameActual + "---");
		if (lib.EqualCompare.isEqual(notiDisplaynameActual, notiexpect3)) {

			file.getSheet(1).getRow(1).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 1: Display Name = null  >>> PASSED");

		} else {
			file.getSheet(1).getRow(1).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 1: Display Name = null  >>> FAILED");
		}
		Display_name.clear(); 
		
		
//////////////////// Verify Email//////////////////////////////////////////////////////////////////////////////////////////////////
		// case 1: Email = null

		Email.sendKeys("quyennt");
		Thread.sleep(1000);
		Email.clear();

		WebElement notiEmailNull = lib.getElement.getElementByXpath(driver, "//small[@jhitranslate=\"entity.validation.email\"]");
		String notiactual11 = notiEmailNull.getText();
		String notiexpect11 = file.getSheet(2).getRow(1).getCell(2).getStringCellValue();
		System.out.println("---" + notiactual11 + "---");
//		Cell resultCell = sheet.getRow(1).createCell(3);
		if (lib.EqualCompare.isEqual(notiactual11, notiexpect11)) {

			file.getSheet(2).getRow(1).createCell(3).setCellValue("PASSED");
			System.out.println(">>> Row 1: Email = null  >>> PASSED");

		} else {
			file.getSheet(2).getRow(1).createCell(3).setCellValue("FAILED");
			System.out.println(">>> Row 1: Email = null  >>> FAILED");
		}  
		
		// Case next 
		
		for (int j = 2; j <= file.getSheet(2).getLastRowNum(); j++) {

//			System.out.println("Row: " + i);
			String email = lib.GetCellToString.getCellValue(file.getSheet(2).getRow(j).getCell(1));
			Cell resultCell = file.getSheet(2).getRow(j).createCell(3);
			WebElement notiEmail = lib.getElement.getElementByXpath(driver, "//small[@jhitranslate=\"entity.validation.email\"]");

			Email.clear();
			Email.sendKeys(email);
			Thread.sleep(1000);
			String notificationActual = notiEmail.getText();
			System.out.println("---" + notificationActual + "---");
			String notificationExpect = file.getSheet(2).getRow(j).getCell(2).getStringCellValue();

			if (lib.EqualCompare.isEqual(notificationActual, notificationExpect)) {
				resultCell.setCellValue("PASSED");
//				resultCell.setCellStyle(cellStyle);
				System.out.println(">>> Row " + j + ": Email =" + email + " >>> PASSED");

			} else {
				resultCell.setCellValue("FAILED");
//				resultCell.setCellStyle(cellStyle);
				System.out.println(">>> Row  " + j + ": Email =" + email + " >>> FAILED");
			}

		}
		
		Email.clear(); 
		

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\Create_User_Unsuccess.xls"));
		lib.ExcelDataConfig.wb.write(outFile);
		outFile.close();
	}





}
