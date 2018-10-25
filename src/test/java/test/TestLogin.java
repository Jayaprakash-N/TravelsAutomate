package test;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;
import propResources.ExcelReader;
import propertyReader.PropertiesLoad;
import pageFactory.*;

public class TestLogin 
{
WebDriver driver;
String Uname;
String Paswd;
PropertiesLoad object;
Properties allObjects;

	@BeforeSuite
	public void setup() throws IOException
	{
		object = new PropertiesLoad();
		allObjects = object.getObjectRepository();
		System.setProperty("webdriver.gecko.driver",allObjects.getProperty("geckodriver"));
	}
	

    @DataProvider(name="LoginData")
	public Object[][] getDataFromDataprovider() throws IOException
    {
    	Object[][] object = null; 
    	ExcelReader file = new ExcelReader();
        Sheet SheetData = file.readExcel(System.getProperty("user.dir")+"\\src\\test\\resources\\","LoginData.xlsx" , "LoginData");
     	int rowCount = SheetData.getLastRowNum()-SheetData.getFirstRowNum();
     	object = new Object[rowCount][2];
     	for (int i = 0; i < rowCount; i++) 
     	{
    		Row row = SheetData.getRow(i+1);
    		for (int j = 0; j < row.getLastCellNum(); j++) 
    		{
    			object[i][j] = row.getCell(j).toString();
    		}
    	}
     	return object;	 
	}
	
	@Test(priority=1, dataProvider="LoginData")
	public void Login(String Username,String Password)
	{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(allObjects.getProperty("url")+"/login");	
		//driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("return document.readyState").toString().equals("complete");
		Login lgn =new Login(driver);
		lgn.setLogin(this.Uname=Username, Password);
		js.executeScript("return document.readyState").toString().equals("complete");
		Assert.assertTrue("Login Verification", driver.findElement(By.id("title")).equals("My Account"));
		driver.findElement(By.xpath("//*[@id='body-section']/a[contains(text(),'My Profile')]")).click();
		js.executeScript("return document.readyState").toString().equals("complete");
		Assert.assertEquals(Uname, driver.findElement(By.xpath("//*[@id='profilefrm']/input[@name='Email']")).getAttribute("value"));
		System.out.println("User"+this.Uname+ " logged in Successfully");
	}
	
	@Test(priority=2)   
	public void Login()
	{
		Uname=allObjects.getProperty("username");
		Paswd=allObjects.getProperty("password");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(allObjects.getProperty("url")+"/login");	
		//driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("return document.readyState").toString().equals("complete");
		Login lgn =new Login(driver);
		lgn.setLogin(Uname, Paswd);
		js.executeScript("return document.readyState").toString().equals("complete");
		Assert.assertTrue("Login Verification", driver.findElement(By.id("title")).equals("My Account"));
		driver.findElement(By.xpath("//*[@id='body-section']/a[contains(text(),'My Profile')]")).click();
		js.executeScript("return document.readyState").toString().equals("complete");
		Assert.assertEquals(Uname, driver.findElement(By.xpath("//*[@id='profilefrm']/input[@name='Email']")).getAttribute("value"));
		System.out.println("User"+this.Uname+ " logged in Successfully");
	}
	
	
	@AfterMethod
	public void closeBrowser()
	{
		System.out.println("Browser Close Invoked");
		driver.close();
	}
	
	/*@AfterSuite
	public void closeConn()
	{
		driver.quit();
	}*/

}
