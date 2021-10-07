package TestNGConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver;
	String browser;
	String url;
	// login Data
	String USER_NAME = "demo@techfios.com";
	String PASSWORD = "abc123";

	// TestData
	String FULL_NAME = "TestNG";
	String COMPANY_NAME = "Techfios";
	String EMAIL = "ABC234@techfios.com";
	String PHONE_NUMBER = "4698552125";
	String COUNTRY = "Angola";

	// storing element with By class
	By userNameField = By.xpath("//input[@id='username']");
	By passWordField = By.xpath("//*[@id=\"password\"]");
	By signInButtonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By dashboardHeaderField = By.xpath("//h2[contains(text(), 'Dashboard')]");
	By customerButtonField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By addCustomerButtonField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By fullNameField = By.xpath("//*[@id=\"account\"]");
	By companyNameField = By.xpath("//*[@id=\"cid\"]");
	By emailField = By.xpath("//*[@id=\"email\"]");
	By phoneField = By.xpath("//*[@id=\"phone\"]");
	By countryField = By.xpath("//*[@id=\"country\"]");
	By saveButtonField = By.xpath("//*[@id=\"submit\"]");

	@BeforeClass

	public void readConfig() {
		// to read files in java //File Reader //InputStream //Buffered //Scanner

		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

		}

		catch (IOException e) {
			e.printStackTrace();

		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		// running Firefox
//		System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
//		driver = new FirefoxDriver();

		// running Chrome
//		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
//		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
	}

	@Test(priority = 1)
	public void loginTest() {

		driver.findElement(userNameField).sendKeys(USER_NAME);
		driver.findElement(passWordField).sendKeys(PASSWORD);
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "Wrong Page");

	}

	@Test(priority = 2)
	public void addCustomerTest() {

		// storing element with By class

		driver.findElement(userNameField).sendKeys(USER_NAME);
		driver.findElement(passWordField).sendKeys(PASSWORD);
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "Wrong Page");

		driver.findElement(customerButtonField).click();
		driver.findElement(addCustomerButtonField).click();

		
//		generateRandomNo(999);//method

		driver.findElement(fullNameField).sendKeys(FULL_NAME + generateRandomNo(999));

//		driver.findElement(companyNameField).sendKeys(COMPANY_NAME);

		selectFromDropdown(companyNameField , COMPANY_NAME);

		driver.findElement(emailField).sendKeys(generateRandomNo(999) + EMAIL);
		driver.findElement(phoneField).sendKeys(PHONE_NUMBER + generateRandomNo(999));
//		driver.findElement(countryField).sendKeys(COUNTRY);
//		Select sel2 =new Select(driver.findElement(countryField));
//		sel2.selectByVisibleText(COUNTRY);
		
		selectFromDropdown(countryField, COUNTRY);
//		driver.findElement(saveButtonField).click();
	}

	private int generateRandomNo(int boundryNumber) {
		Random rnd = new Random();
		int generateNo = rnd.nextInt(boundryNumber);
		return generateNo;
		
	}

	public void selectFromDropdown(By element, String visibleText) {
		Select sel = new Select(driver.findElement(element));
		sel.selectByVisibleText(visibleText);

	}

	// @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
