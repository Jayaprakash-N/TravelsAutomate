package pageFactory;

//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Login 
{
	WebDriver driver;
	@FindBy(name="username")
	WebElement Username;
	
	@FindBy(name="password")
	WebElement Password;
	
	@FindBy(xpath = "//*[@class = 'wow fadeIn animated']/button")
	WebElement Login;
	
	public int TimeoutValue = 60;
	
	public Login(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TimeoutValue), this);
	}
	
	public void setUsername(String uname)
	{
		Username.sendKeys(uname);
	}
	
	public void setPassword(String pwd)
	{
		Password.sendKeys(pwd);
	}
	
	public void ClickLogin()
	{
		Login.click();
		//Password.sendKeys(Keys.ENTER);
	}
	
	public void setLogin(String Username,String Psswd)
	{
		this.setUsername(Username);
		this.setPassword(Psswd);
		this.ClickLogin();
	}
	

}
