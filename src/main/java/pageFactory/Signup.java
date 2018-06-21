package pageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Signup 
{
	WebDriver driver;
	@FindBy(xpath="//*[@id='headersignupform']/div[3]/input")
	WebElement FirstName;
	
	@FindBy(xpath="//*[@id='headersignupform']/div[4]/input")
	WebElement LastName;
	
	@FindBy(xpath="//*[@id='headersignupform']/div[5]/input")
	WebElement Mob_No;
	
	@FindBy(xpath="//*[@id='headersignupform']/div[6]/input")
	WebElement Email;
	
	@FindBy(xpath="//*[@id='headersignupform']/div[7]/input")
	WebElement Psswd;
	
	@FindBy(xpath="//*[@id='headersignupform']/div[8]/input")
	WebElement CPsswd;
	
	@FindBy(xpath="//*[@id='headersignupform']/div[9]/button")
	WebElement Signup;
	
	public Signup(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
    public void setFirstName(String Fname)
    {
    	FirstName.sendKeys(Fname);
    }
    public void setLastName(String Lname)
    {
    	LastName.sendKeys(Lname);
    }
    public void setMobileNo(String Mbl_no)
    {
    	Mob_No.sendKeys(Mbl_no);
    }
	
    public void setEmail(String Emailid)
    {
    	Email.sendKeys(Emailid);
    }
	
    public void setpwd(String pwd)
    {
    	Psswd.sendKeys(pwd);
    }
    public void setCPwd(String cpwd)
    {
    	CPsswd.sendKeys(cpwd);
    }
	
    public void ClickSignup()
    {
    	Signup.click();
    }
	
	public void doSignup(String Fname, String Lname, String mbl_no,String email,String pwd,String cpwd)
	{
		this.setFirstName(Fname);
		this.setLastName(Lname);
		this.setMobileNo(mbl_no);//Long.toString(mbl_no));
		this.setEmail(email);
		this.setpwd(pwd);
		this.setCPwd(cpwd);
		this.ClickSignup();
	}
}