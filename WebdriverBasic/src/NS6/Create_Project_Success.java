package NS6;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import lib.ExcelDataConfig;
import lib.Login;

public class Create_Project_Success {
	@Test(testName = "Create_Project_Unsuccess")
	public void Create_Project_Unsuccess() throws Exception {

		System.setProperty("webdriver.chrome.driver", "D:\\SeleniumWebdriver\\chromedriver.exe");
		ExcelDataConfig file = new ExcelDataConfig("D:\\Create_Project.xls");
		WebDriver driver = Login.LoginToIEM();
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		Actions action = new Actions(driver);

		WebElement Management_Dropdownlist = lib.getElement.getElementByXpath(driver,
				"//span[@jhitranslate=\"global.menu.registration\"]");
		Management_Dropdownlist.click();
		Thread.sleep(1000);

		WebElement Management_User = lib.getElement.getElementByXpath(driver,
				"//span[@jhitranslate=\"global.menu.entities.projectInformation\"]");
		Management_User.click();
		Thread.sleep(1000);

		// WebElement Management_User_Create =
		// lib.getElement.getElementByXpath(driver,
		// "//span[@jhitranslate=\"webApp.projectRegistration.home.createNewProject\"]");
		// Thread.sleep(1500);
		// Management_User_Create.click();
		// Thread.sleep(2000);
		//
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// js.executeScript("javascript:window.scrollBy(350,1000)");
		// Thread.sleep(1000);

		for (int i = 1; i <= file.getSheet(0).getLastRowNum(); i++) {

			WebElement Management_User_Create = lib.getElement.getElementByXpath(driver,
					"//span[@jhitranslate=\"webApp.projectRegistration.home.createNewProject\"]");
			Thread.sleep(1500);
			Management_User_Create.click();
			Thread.sleep(2000);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("javascript:window.scrollBy(350,1000)");
			Thread.sleep(1000);

			/*-------------Insert Field 1: Item status-----------------*/
			// Get text form file data and convert it to integer type
			String eItemStatus = file.getSheet(0).getRow(i).getCell(1).getStringCellValue();
			int statusItemNumber = Integer.parseInt(eItemStatus);
			List<WebElement> oRadioButton = driver.findElements(By.name("ProjectStatusId"));
			if (statusItemNumber >= 0 && statusItemNumber <= oRadioButton.size()) {
				oRadioButton.get(statusItemNumber).click();
			} else {
				throw new NotFoundException("Option " + oRadioButton + " not found!");
			}

			/*-------------Insert Field 2: Business status------------*/
			String eBusinessStatus = file.getSheet(0).getRow(i).getCell(2).getStringCellValue();
			int businessStatusNumber = Integer.parseInt(eBusinessStatus);
			WebElement dropBusinessStatus = lib.getElement.getElementById(driver, "field_ProjectBusinessStatus");
			dropBusinessStatus.click();

			List<WebElement> wBusinessStatus = dropBusinessStatus.findElements(By.tagName("option"));
			System.out.println(wBusinessStatus);
			if (businessStatusNumber >= 0 && businessStatusNumber <= oRadioButton.size()) {
				wBusinessStatus.get(businessStatusNumber).click();
			} else {
				throw new NotFoundException("Option " + wBusinessStatus + " not found!");
			}

			/*--------------Insert Field 3: Customer name --------------*/
			//Get list row in form
//			List<WebElement> eListRowInForm = driver.findElements(By.xpath(".//*[contains(@class,'form-group')]"));
			
//			WebElement eRowCustomerName = eListRowInForm.get(0);
//			WebElement ePopupCustomerName = eRowCustomerName
//					.findElement(By.xpath("//div[contains(@class,'col-sm-9')]/div/span"));
			WebElement ePopupCustomerName= driver.findElement(By.xpath("html/body/jhi-main/div[2]/div/jhi-project-registration-edit/form/div[1]/div[6]/div[1]/div[3]/div/div[1]/span"));

			try {
				action.moveToElement(ePopupCustomerName).perform();
				ePopupCustomerName.click();
				Thread.sleep(2000);
				Boolean isFind = false;
				while (isFind == false) {
					// List all row in a current page
					WebElement popup = lib.getElement.getElementByXpath(driver, "//*[@class=\"modal-content\"]");
					List<WebElement> listCustomerName = popup
							.findElements(By.xpath(".//*[contains(@class,'table-striped')]/tbody/tr"));

					// Print list elements
					System.out
							.println("===============================================================================");
					for (WebElement e : listCustomerName) {
						System.out.println(e.getText());
					}
					System.out
							.println("===============================================================================");

					String customerUserName = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(3));
					WebElement foundCustomerName = getRow(listCustomerName, customerUserName);

					if (foundCustomerName != null) {
						WebElement saveButton = popup
								.findElement(By.xpath(".//*[contains(@jhitranslate,'entity.action.save')]"));
						foundCustomerName.isSelected();
						Thread.sleep(5000);
						saveButton.click();
						Thread.sleep(3000);
						isFind = true;
						break;

					} else {
						System.out.println("STT " + customerUserName + " : not found in this page. Next page...!");
						// Verify the last page
						WebElement nextPage = lib.getElement.getElementByXpath(driver, "//a[@aria-label=\"Next\"]");
						String tabIndex = nextPage.getAttribute("tabindex");
						System.out.println(tabIndex);
						if (tabIndex == null) {
							nextPage.click();
							Thread.sleep(1000);

						} else {
							System.out.println("Row: " + i + " UserID = " + customerUserName
									+ " >>> Not found CustomerName on this page!");
							// pageOne.click();
							Thread.sleep(1000);
							break;
						}
					}
				}

			} catch (Exception e) {
				System.out.println("-----" + ePopupCustomerName + " not found! -----");
			}

			/*--------------Insert Field 4: Remarks --------------*/
			String remark = file.getSheet(0).getRow(i).getCell(4).getStringCellValue();
			WebElement eRemarks = lib.getElement.getElementById(driver, "field_Note");
			eRemarks.sendKeys(remark);

			/*--------------Insert Field 5:  Estimate number --------------*/
			String estimateNumber = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(5));
			WebElement eEstimateNumber = lib.getElement.getElementById(driver, "field_QuotationNumber");
			eEstimateNumber.sendKeys(estimateNumber);

			/*--------------Insert Field 6:  Order schedule --------------*/
			String orderSchedule = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(6));
			WebElement eOrderSchedule = lib.getElement.getElementById(driver, "field_PlanToRecieveOrderTime");
			eOrderSchedule.sendKeys(orderSchedule);

			/*--------------Insert Field 7:  Responsibility By --------------*/
			WebElement ePopupResponsibilityBy = driver.findElement(By.xpath("html/body/jhi-main/div[2]/div/jhi-project-registration-edit/form/div[1]/div[6]/div[1]/div[7]/div/div[1]/span/i"));
//			WebElement eListRowResponsibility = eListRowInForm.get(1);
			
//			WebElement ePopupResponsibilityBy= eListRowResponsibility.findElement(By.xpath("//div[contains(@class,'col-sm-9')]/div/span"));
			
				action.moveToElement(ePopupResponsibilityBy).perform();
				ePopupResponsibilityBy.click();
				Thread.sleep(2000);
				Boolean isFind = false;
				while (isFind == false) {
					// List all row in a current page
					WebElement popup = lib.getElement.getElementByXpath(driver, "//*[@class=\"modal-content\"]");
					List<WebElement> listEmployee  = popup
							.findElements(By.xpath(".//*[contains(@class,'table-striped')]/tbody/tr"));
					String ResponsibilityBy = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(7));

					// Print list elements
					System.out
							.println("===============================================================================");
					for (WebElement e : listEmployee) {
						System.out.println(e.getText());
					}
					System.out
							.println("===============================================================================");

					WebElement foundResponsibilityBy = getRow(listEmployee, ResponsibilityBy);

					if (foundResponsibilityBy != null) {
						WebElement saveButton = popup
								.findElement(By.xpath(".//*[contains(@jhitranslate,'entity.action.save')]"));
						foundResponsibilityBy.isSelected();
						Thread.sleep(5000);
						saveButton.click();
						Thread.sleep(3000);
						isFind = true;
						break;

					} else {
						System.out.println("STT " + ResponsibilityBy + " : not found in this page. Next page...!");
						// Verify the last page
						WebElement nextPage = lib.getElement.getElementByXpath(driver, "//a[@aria-label=\"Next\"]");
						String tabIndex = nextPage.getAttribute("tabindex");
						System.out.println(tabIndex);
						if (tabIndex == null) {
							nextPage.click();
							Thread.sleep(1000);

						} else {
							System.out.println("Row: " + i + " UserID = " + ResponsibilityBy
									+ " >>> Not found ResponsibilityBy on this page!");
							// pageOne.click();
							Thread.sleep(1000);
							break;
						}
					}
				}
			
			

			/*--------------Insert Field 8:  Estimate URL --------------*/
			String estimateURL = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(8));
			WebElement eEstimateURL = lib.getElement.getElementById(driver, "field_EstimateURL");
			eEstimateURL.sendKeys(estimateURL);

			/*--------------Insert Field 9:  Estimate URL Name --------------*/
			String estimateURLName = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(9));
			WebElement eEstimateURLName = lib.getElement.getElementById(driver, "field_EstimateURL");
			eEstimateURLName.sendKeys(estimateURLName);

			/*--------------Insert Field 10:  Request URL --------------*/
			String requestURL = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(10));
			WebElement eRequestURL = lib.getElement.getElementById(driver, "field_RequestURL");
			eRequestURL.sendKeys(requestURL);

			/*--------------Insert Field 11:  Request URL Name --------------*/
			String requestURLName = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(11));
			WebElement eRequestURLName = lib.getElement.getElementById(driver, "field_RequestName");
			eRequestURLName.sendKeys(requestURLName);

			/*------------------ Insert Field 12: Organization------------------*/
			String organization = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(12));
			WebElement eOrganization = lib.getElement.getElementById(driver, "field_Org");
			// eOrganization.click();
			try {
				Select dropdown = new Select(eOrganization);
				dropdown.selectByVisibleText(organization);
			} catch (Exception e) {
				System.out.println("Not found Element! Try again please...");
			}
			// List<WebElement> listOrganization =
			// eOrganization.findElements(By.tagName("option"));
			//
			// if (businessStatusNumber >= 0 && businessStatusNumber <=
			// oRadioButton.size()) {
			// eOrganization.sendKeys(organization);
			// } else {
			// throw new NotFoundException("Option " + eOrganization + " not
			// found!");
			// }

			/*------------------ Insert Field 13: Project Name------------------*/
			String projectName = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(13));
			WebElement eProjectName = lib.getElement.getElementById(driver, "field_Name");
			eProjectName.sendKeys(projectName);

			/*------------------ Insert Field 14: Project Number-----------------*/
			String projectNumber = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(14));
			WebElement eProjectNumber = lib.getElement.getElementById(driver, "field_ProjectNumber");
			eProjectNumber.sendKeys(projectNumber);

			/*--------------Insert Field 15:  Start Date--------------*/
			String startDate = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(15));
			WebElement eStartDate = lib.getElement.getElementById(driver, "field_StartDate");
			eStartDate.sendKeys(startDate);

			/*--------------Insert Field 16: End Date --------------*/
			String endDate = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(16));
			WebElement eEndDate = lib.getElement.getElementById(driver, "field_EndDate");
			eEndDate.sendKeys(endDate);

			/*--------------Insert Field 17: Contract URL --------------*/
			String contractURL = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(17));
			WebElement eContractURL = lib.getElement.getElementById(driver, "field_ContractURL");
			eContractURL.sendKeys(contractURL);

			/*--------------Insert Field 18: Contract name --------------*/
			String contractName = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(18));
			WebElement eContractName = lib.getElement.getElementById(driver, "field_ContractName");
			eContractName.sendKeys(contractName);
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
			String notificationExpect = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(19));
			Cell resultCell = file.getSheet(0).getRow(i).createCell(20);
			WebElement ok_button = lib.getElement.getElementByXpath(driver,
					"//span[@jhitranslate=\"entity.action.ok\"]");
			WebElement back_button = lib.getElement.getElementByXpath(driver,
					"//span[@jhitranslate=\"entity.action.back\"]");

			if (lib.EqualCompare.isEqual(notificationActual, notificationExpect)) {
				
				ok_button.click();
				Thread.sleep(1500);

				
				back_button.click();
				Thread.sleep(1500);
				resultCell.setCellValue("PASSED");
				System.out.println(">>> Row " + i + "- Created project successfully!");

			} else {
				ok_button.click();
				Thread.sleep(1500);
				back_button.click();
				Thread.sleep(1500);
				resultCell.setCellValue("FAILED");
				System.out.println(">>> Row " + i + "- Created project unsuccessfully!");
			}

			
		}
		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\Create_Project.xls"));
		lib.ExcelDataConfig.wb.write(outFile);
		outFile.close();
	}
		

	// Get all item in a row
	private WebElement getRow(List<WebElement> elements, String userName) {
		// WebDriver driver = null; // Assigned elsewhere
		// JavascriptExecutor js = (JavascriptExecutor) driver;

		for (WebElement e : elements) {
			String eUserName = getUserName(e);
			if (eUserName.equals(userName)) {
				// e.isSelected();
				e.click();
				System.out.println("Have a row!");
				return e;
			}
		}
		return null;
	}

	// Get text in the <td> tag
	private String getUserName(WebElement element) {
		List<WebElement> list = element.findElements(By.tagName("td"));
		WebElement eUserName = list.get(0);
		String userName = eUserName.getText();
		System.out.println("userName: " + userName);
		return userName;
	}

}
