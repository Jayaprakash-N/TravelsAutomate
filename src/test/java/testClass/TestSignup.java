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
import pageFactory.*;
import propResources.ExcelReader;
import propertyReader.*;

@Listeners({ListenerClass.RetryListenerClass.class, ListenerClass.TestListener.class})
public class TestSignup 
{
	WebDriver driver;
	PropertiesLoad object;
	private Properties allObjects;
	private String email;
	JavascriptExecutor js = (JavascriptExecutor)driver;
	
	@BeforeClass
	public void setup() throws IOException
	{
		object = new PropertiesLoad();
        allObjects = object.getObjectRepository();
		System.setProperty("webdriver.gecko.driver",allObjects.getProperty("geckodriver"));
	}
	
	@DataProvider(name="SignUpData",parallel = true)
	public Object[][] getDataFromDataprovider() throws IOException
    {
    	Object[][] object = null; 
    	ExcelReader file = new ExcelReader();
        Sheet SheetData = file.readExcel(System.getProperty("user.dir")+"\\src\\test\\resources\\","SignUpData.xlsx" , "SignUpData");
     	int rowCount = SheetData.getLastRowNum()-SheetData.getFirstRowNum();
     	object = new Object[rowCount][6];
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
	
	@Test(dataProvider="SignUpData")
	public void signUp(String Fname,String Lname,String mobno,String email,String pwd,String rePwd)
	{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(allObjects.getProperty("url")+"/register");		
		js.executeScript("return document.readyState").toString().equals("complete");
		Signup sgn=new Signup(driver);
		sgn.doSignup(Fname,Lname,mobno,this.email=email,pwd,rePwd);
		js.executeScript("return document.readyState").toString().equals("complete");
		if(!(driver.findElement(By.id("title")).equals("Register")))
		{
			Assert.assertTrue(driver.getTitle().equals("My Account"));
			System.out.println("User"+this.email+ " Signed In Successfully");
		}
		System.out.println("User"+this.email+ " Signed In Already");
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		System.out.println("Browser Close Invoked");
		driver.close();
	}

}

