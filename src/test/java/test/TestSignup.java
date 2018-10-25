package test;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageFactory.*;
import propResources.ExcelReader;
import propertyReader.*;

public class TestSignup 
{
	WebDriver driver;
	PropertiesLoad object;
	private Properties allObjects;
	private String email;
	//WebDriverWait wait;
	
	@BeforeClass
	public void setup() throws IOException
	{
		object = new PropertiesLoad();
        allObjects = object.getObjectRepository();
		System.setProperty("webdriver.gecko.driver",allObjects.getProperty("geckodriver"));
		//driver = new FirefoxDriver();
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
	}
	
	@DataProvider(name="SignUpData")
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
	
	@Test(priority=1,dataProvider="SignUpData")
	public void signUp(String Fname,String Lname,String mobno,String email,String pwd,String rePwd)
	{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(allObjects.getProperty("url")+"/register");	
		//driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("return document.readyState").toString().equals("complete");
		Signup sgn=new Signup(driver);
		sgn.doSignup(Fname,Lname,mobno,this.email=email,pwd,rePwd);
		js.executeScript("return document.readyState").toString().equals("complete");
		if(!(driver.findElement(By.id("title")).equals("Register")))
		{
			Assert.assertTrue("Registration Verification",driver.findElement(By.xpath("//*[@id='body-section']/h3[@class=RTL]")).getText().contains(Fname));
			System.out.println("User"+this.email+ " Signed In Successfully");
		}
		System.out.println("User"+this.email+ " Signed In Successfully");
	}
	
	@AfterMethod
	public void closeBrowser()
	{
		System.out.println("Browser Close Invoked");
		driver.close();
	}

}

