package testClass;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import propResources.ExcelReader;
import propertyReader.PropertiesLoad;
import pageFactory.*;

@Listeners({ListenerClass.RetryListenerClass.class, ListenerClass.TestListener.class})
public class TestLogin 
{
WebDriver driver;
String Uname;
String Paswd;
PropertiesLoad object;
Properties allObjects;
JavascriptExecutor js = (JavascriptExecutor)driver;

	@BeforeClass
	public void setup() throws IOException
	{
		object = new PropertiesLoad();
		allObjects = object.getObjectRepository();
		System.setProperty("webdriver.gecko.driver",allObjects.getProperty("geckodriver"));
	}
	
    @DataProvider(name="LoginData",parallel = true)
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
	
	@Test(dataProvider="LoginData")
	public void Login(String Username,String Password)
	{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(allObjects.getProperty("url")+"/login");	
		js.executeScript("return document.readyState").toString().equals("complete");
		Login lgn =new Login(driver);
		lgn.setLogin(this.Uname=Username, Password);
		js.executeScript("return document.readyState").toString().equals("complete");
		Assert.assertTrue(driver.findElement(By.id("title")).equals("My Account"));
		System.out.println("User"+this.Uname+ " logged in Successfully");
	}
	
	@Test   
	public void LoginWithWrongCreds()
	{
		Uname=allObjects.getProperty("username");
		Paswd=allObjects.getProperty("password");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(allObjects.getProperty("url")+"/login");	
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("return document.readyState").toString().equals("complete");
		Login lgn =new Login(driver);
		lgn.setLogin(Uname, Paswd);
		js.executeScript("return document.readyState").toString().equals("complete");
		Assert.assertFalse(driver.findElement(By.id("title")).equals("My Account"));
		System.out.println("User"+this.Uname+ " not logged in Successfully");
	}
	
	
	@AfterMethod
	public void closeBrowser()
	{
		System.out.println("Browser Close Invoked");
		driver.close();
	}
	
}
