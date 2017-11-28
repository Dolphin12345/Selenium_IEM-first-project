package NS6;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import lib.EqualCompare;
import lib.ExcelDataConfig;
import lib.IEM_CommonLogin_New;

public class IEM_Delete_Project {
	@Test(testName = "Delete_Project_Success")
	public void deleteSuccess() throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "D:\\01_Dolphin\\Selenium_Software\\geckodriver.exe");
		ExcelDataConfig file = new ExcelDataConfig("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Delete_Project.xls");

		// Call from [IEM_CommonLogin_New] class
		IEM_CommonLogin_New driverCommonLogin = new IEM_CommonLogin_New ();
		driverCommonLogin.LaunchBrowser();
		WebDriver driver = driverCommonLogin.LoginToIEM();	
		
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		Actions action = new Actions(driver);

		WebElement Management_Dropdownlist = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"global.menu.registration\"]");
		Management_Dropdownlist.click();
		Thread.sleep(1000);

		WebElement Management_User = lib.getElement.getElementByXpath(driver,"//span[@jhitranslate=\"global.menu.entities.projectInformation\"]");
		Management_User.click();
		Thread.sleep(1000);
		
		for (int i = 1; i <= file.getSheet(0).getLastRowNum(); i++) {
			// Scroll browser
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("javascript:window.scrollBy(350,1000)");
			Thread.sleep(1000);
			
			Boolean isFind = false;
			
			// Confirm page 1
			List<WebElement> list = driver.findElements(By.xpath(".//*[contains(@class,'page-item')]"));
			WebElement pageOne = list.get(2);

			while (isFind == false) {

				List<WebElement> items = driver.findElements(By.xpath(".//*[contains(@class,'table-striped')]/tbody/tr"));
				System.out.println("Have "+ items.size() + " in this page");
				String project_name = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(1));
				
				Cell resultCell = file.getSheet(0).getRow(i).createCell(3);
				WebElement found = getRow(items, project_name);

				if (found != null) {
					System.out.println("STT" + i  + " : not null");
					// Scroll browser
					js.executeScript("javascript:window.scrollBy(350,1000)");
					Thread.sleep(1000);
					
					// Click on Delete icon
					WebElement form = found.findElement(By.xpath(".//*[contains(@class,'text-right')][1]"));
					WebElement deleteBtn = form.findElement(By.xpath(".//*[contains(@class,'btn-danger')]"));
					System.out.println("Found the element to delete! :)");
					Thread.sleep(1500);
					deleteBtn.click();
					
					// Dialog confirm delete
					WebElement confirmPopup = driver.findElement(By.name("deleteForm"));
					WebElement yesButton = confirmPopup.findElement(By.xpath(".//*[@jhitranslate=\"entity.action.delete\"]"));
					Thread.sleep(1500);
					yesButton.click();
					Thread.sleep(1000);
					
					// Get success message content
					WebElement notiPopup = driver.findElement(By.name("notificationForm"));
					WebElement TextNoti = notiPopup.findElement(By.className("modal-body"));
					Thread.sleep(1500);
					String messageActual = TextNoti.getText();
					String messageExpect = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(2));
					WebElement OK = notiPopup.findElement(By.xpath(".//*[@jhitranslate=\"entity.action.ok\"]"));
					
					System.out.println("Project name in the excel file " + i + " row is: " + project_name);
					System.out.println("Actual message is " + messageActual);
					System.out.println("Expectation message is " + messageExpect);
					System.out.println("Compare string status " + i + " row is: " + lib.EqualCompare.isEqual(messageActual, messageExpect));
					
					// Compare String
					if (EqualCompare.isEqual(messageActual, messageExpect)) {
						OK.click();
						Thread.sleep(1500);
						resultCell.setCellValue("PASSED");
//						pageOne.click();
						Thread.sleep(1500);
						System.out.println("PASSED");

					} else {
						OK.click();
						Thread.sleep(1500);
						System.out.println("FAILED");
//						pageOne.click();
						Thread.sleep(1500);
						resultCell.setCellValue("FAILED");
						
					}

					isFind = true;
					break;

				} else {
					System.out.println("STT " + i  + " : null");
					// Scroll browser
					js.executeScript("javascript:window.scrollBy(350,1000)");
					Thread.sleep(1000);
					
					// Verify the last page
					WebElement nextPage = lib.getElement.getElementByXpath(driver, "//a[@aria-label=\"Next\"]");
					String tabIndex = nextPage.getAttribute("tabindex");
					System.out.println(tabIndex);
					if (tabIndex == null) {
						nextPage.click();
						Thread.sleep(1500);

					} else {
						resultCell.setCellValue("NOT FOUND");
						System.out.println(
								"Row: " + i + " ProjectID = " + project_name + " >>> Not found projectID on this page!");
						pageOne.click();
						Thread.sleep(1500);
						break;
					}

				}

			}

		}

		driver.close();
		FileOutputStream outFile = new FileOutputStream(new File("D:\\01_Dolphin\\Selenium_Webdriver\\Selenium_IEM-first-project\\WebdriverBasic\\TestData\\Delete_Project.xls"));
		lib.ExcelDataConfig.wb.write(outFile);
		outFile.close();
	}

	// Get all item in a row
	private WebElement getRow(List<WebElement> elements, String projectName) {
		for (WebElement e : elements) {
			String eProjectName = getProjectName(e);
			if (eProjectName.equals(projectName)) {
				return e;
			}
		}
		return null;
	}

	// Get text in the <td> tag
	private String getProjectName(WebElement element) {
		List<WebElement> list = element.findElements(By.tagName("td"));
		WebElement eProjectName = list.get(2);
		String ProjectName = eProjectName.getText();
		return ProjectName;
	}

}
