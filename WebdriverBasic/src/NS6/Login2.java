package NS6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Login2 {
	public WebDriver driver;
	
	
	
	@BeforeMethod
	public void beforMethod() {
		System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\geckodriver-v0.19.0-win64\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();

		driver.manage().window().maximize();

		driver.get("http://192.168.7.43:9001/#/");
		//Thread.sleep(5000);
	}
	
	@Test
	public void main() throws IOException, InterruptedException {
		Actions action = new Actions(driver);
		WebElement AccMenu = driver.findElement(By.id("account-menu"));
		
		action.moveToElement(AccMenu).perform();
		AccMenu.click();
		WebElement LoginMenu = driver.findElement(By.id("login"));
		action.moveToElement(LoginMenu).perform();
		LoginMenu.click();
		//WebDriverWait wait = new WebDriverWait(driver, 10);
		//wait.until(ExpectedConditions.elementToBeClickable(LoginMenu));
		//action.click(LoginMenu).perform();
		
		//Following code is to locate the path of excel file. 
		FileInputStream file = new FileInputStream(new File("D:\\Selenium\\Test Data\\Login.xls"));
		
		//Following code is to initialize the excel file as a workbook. 
		HSSFWorkbook workbook = new HSSFWorkbook(file);

		//Following code is to initialize the excel sheet of the workbook. Here 0 (zero) refers to the first sheet of the workbook. 
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {

			String user = sheet.getRow(i).getCell(0).getStringCellValue();
			String pass = sheet.getRow(i).getCell(1).getStringCellValue();
			Cell cell = sheet.getRow(i).getCell(2);
			//cell.setCellValue("Passed");
			

			WebElement usertextbox = driver.findElement(By.id("username"));
			usertextbox.clear();
			usertextbox.sendKeys(user);

			WebElement passtextbox = driver.findElement(By.id("password"));
			passtextbox.clear();
			passtextbox.sendKeys(pass);

			driver.findElement(By.xpath("//button[@jhitranslate=\"login.form.button\"]")).click();
			Assert.assertTrue(driver.getTitle().contains("ŠÇ—"),"Login Failed");
			
			Actions action1 = new Actions(driver);
			WebElement AccMenuHome = driver.findElement(By.id("account-menu"));
			
			action1.moveToElement(AccMenuHome).perform();
			AccMenuHome.click();
			WebElement LogoutMenu = driver.findElement(By.id("logout"));
			action1.moveToElement(LogoutMenu).perform();
			LogoutMenu.click();
			
			//driver.findElement(By.id("userNavigationLabel")).click();
			//System.out.println("Row " + i + " - Email is: " + user);
			
			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
			Thread.sleep(3000);
			//driver.findElement(By.xpath("//a[contains(@data-gt,'menu_logout')]")).click();
			// driver.findElement(By.xpath(".//*[contains(@class,'_54nh')][9]")).sendKeys(pass);
			// System.out.println(pass);
			//// driver.findElement(By.xpath(".//*[@id='passwordNext']/content/span")).click();
			// driver.findElement(By.xpath(".//*[text()='Next']")).click();
			 
			
			sheet.getRow(i).getCell(2).setCellValue("Passed");
			Thread.sleep(1000);

		}

		driver.close();
		workbook.write(new FileOutputStream("D:\\Selenium\\Test Data\\Result.xls"));
		workbook.close();
		file.close();
	}
	
//@AfterMethod
//	public void afterMethod()
//	{
//	driver.quit();
//	}
		
	}


