package NS6;


	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.util.List;
	import java.util.concurrent.TimeUnit;
	import org.apache.poi.ss.usermodel.Cell;
	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import lib.ExcelDataConfig;
	import lib.Login;
	import org.testng.annotations.Test;

	public class View_User_Detail  {
		@Test(testName = "View_User_Detail")
		public void deleteSuccess() throws InterruptedException, IOException {
			System.setProperty("webdriver.chrome.driver", "D:\\SeleniumWebdriver\\chromedriver.exe");
			ExcelDataConfig file = new ExcelDataConfig("D:\\View_User.xls");

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
				Boolean isFind = false;
				
				// Confirm page 1
				List<WebElement> list = driver.findElements(By.xpath(".//*[contains(@class,'page-item')]"));
				WebElement pageOne = list.get(2);

				while (isFind == false) {

					List<WebElement> items = driver
							.findElements(By.xpath(".//*[contains(@class,'table-striped')]/tbody/tr"));
					System.out.println("Have "+ items.size() + " in this page");
					String user_name = lib.GetCellToString.getCellValue(file.getSheet(0).getRow(i).getCell(1));
					
					Cell resultCell = file.getSheet(0).getRow(i).createCell(3);
					WebElement found = getRow(items, user_name);

					if (found != null) {
						System.out.println("STT" + i  + " : not null");
						WebElement form = found.findElement(By.xpath(".//*[contains(@class,'text-right')][1]"));
						WebElement viewBtn = form.findElement(By.xpath(".//*[contains(@class,'btn-info')]"));
						System.out.println("Found the element to view! :)");
						viewBtn.click();
						Thread.sleep(1500);
						WebElement backBtn = form.findElement(By.xpath(".//*[@jhitranslate=\"entity.action.back\"]"));
						Thread.sleep(1500);
						backBtn.click();
						pageOne.click();
						// khong so sanh duoc text??????
//						if (lib.EqualCompare.isEqual(messageActual, messageExpect)) {
//							OK.click();
//							Thread.sleep(1500);
//							resultCell.setCellValue("PASSED");
//							pageOne.click();
//							Thread.sleep(1500);
//							System.out.println("PASSED");
//
//						} else {
//							OK.click();
//							Thread.sleep(1500);
//							System.out.println("FAILED");
//							pageOne.click();
//							Thread.sleep(1500);
//							resultCell.setCellValue("FAILED");
//							
//						}

						isFind = true;
						break;

					} else {
						System.out.println("STT " + i  + " : null");
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
									"Row: " + i + " UserID = " + user_name + " >>> Not found userID on this page!");
							pageOne.click();
							Thread.sleep(1500);
							break;
						}

					}

				}

			}

			driver.close();
			FileOutputStream outFile = new FileOutputStream(new File("D:\\View_User.xls"));
			lib.ExcelDataConfig.wb.write(outFile);
			outFile.close();
		}

		// Get all item in a row
		private WebElement getRow(List<WebElement> elements, String userName) {
			for (WebElement e : elements) {
				String eUserName = getUserName(e);
				if (eUserName.equals(userName)) {
					return e;
				}
			}
			return null;
		}

		// Get text in the <td> tag
		private String getUserName(WebElement element) {
			List<WebElement> list = element.findElements(By.tagName("td"));
			WebElement eUserName = list.get(2);
			String userName = eUserName.getText();
			return userName;
		}

	}



//	}
