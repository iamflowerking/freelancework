package WebDriverManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class WebDriverManager {
	
	public static WebDriver driver=null;
	public static Properties prop = new Properties();
	
	//Load all the Page Objects in the constructor class
	public WebDriverManager() {
		try {
    		prop.load( new FileInputStream("./src/test/resources/ObjectRepository/PageObjects.properties") );
    	} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}
	
	public void setDriver(WebDriver driver){
		this.driver=driver;
	}
	
	public void setUpDriver(String browser){
        switch (browser.toLowerCase().trim()) {
            case "chrome":
            	System.setProperty("webdriver.chrome.driver","E:\\EclipseWorkSpace\\SCDemo\\src\\test\\resources\\drivers\\chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
            	driver = new ChromeDriver(chromeOptions);
                break;
            default:
                throw new IllegalArgumentException("Browser \"" + browser + "\" isn't supported.");
        }
    }
	public void closeDriver(){
        try {
        	driver.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
        
    }
	public void quiteBrowser() {
		try {
			driver.quit();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	public void waitForPageLoad(int timeout){
		try {
			ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	       
	}
	
	public String getPropertyKey(String sObject) {
		String sResult ="";
		try {
			sResult = prop.getProperty(sObject);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return sResult;
	}
	
	public By getElementWithLocator(String WebElement) throws Exception {
		By byObject = null;
		try {
			String locatorTypeAndValue = prop.getProperty(WebElement);
			String[] locatorTypeAndValueArray = locatorTypeAndValue.split("->");
			String locatorType = locatorTypeAndValueArray[0].trim();
			String locatorValue = locatorTypeAndValueArray[1].trim();
			switch (locatorType.toUpperCase()) {
			case "ID":
				byObject = By.id(locatorValue);
				break;
			case "NAME":
				byObject = By.name(locatorValue);
				break;
			case "TAGNAME":
				byObject = By.tagName(locatorValue);
				break;
			case "LINKTEXT":
				byObject = By.linkText(locatorValue);
				break;
			case "PARTIALLINKTEXT":
				byObject = By.partialLinkText(locatorValue);
				break;
			case "XPATH":
				byObject = By.xpath(locatorValue);
				break;
			case "CSS":
				byObject =  By.cssSelector(locatorValue);
				break;
			case "CLASSNAME":
				byObject = By.className(locatorValue);
				break;
			default:
				byObject = null;
				break;
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return byObject;
    }
	
	public WebElement FindAnElement(String WebElement) throws Exception{
		WebElement weElement = null;
		try {
			weElement = driver.findElement(getElementWithLocator(WebElement));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
    	return weElement;
    }
	
	public List<WebElement> FindAnElementList(String WebElementList) throws Exception{
		List<WebElement> weElementList = null;
		try {
			weElementList = driver.findElements(getElementWithLocator(WebElementList));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
    	return weElementList;
    }
	
	public void PerformActionOnElement(String WebElement, String Action, String Text) throws Exception {
		try {
			switch (Action) {
			case "Click":
				FindAnElement(WebElement).click();
				break;
			case "EnterText":
				FindAnElement(WebElement).sendKeys(Text);
				break;
			case "Clear":
				FindAnElement(WebElement).clear();
				break;
			case "WaitForElementDisplay":
				waitForCondition("Presence",WebElement,60);
				break;
			case "WaitForElementClickable":
				waitForCondition("Clickable",WebElement,60);
				break;
			case "ElementNotDisplayed":
				waitForCondition("NotPresent",WebElement,60);
				break;
			default:
				throw new IllegalArgumentException("Action " + Action + " isn't supported.");
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
    	
    }
	
	public void waitForCondition(String TypeOfWait, String WebElement, int Time){
    	try {
	    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Time, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(Exception.class);
	    	switch (TypeOfWait)
	    	{
	    	case "PageLoad":
	    		wait.until( ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
	    		break;
	    	case "Clickable":
	    		wait.until(ExpectedConditions.elementToBeClickable(FindAnElement(WebElement)));
	    		break;
	    	case "Presence":
	    		wait.until(ExpectedConditions.presenceOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	case "Visibility":
	    		wait.until(ExpectedConditions.visibilityOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	case "NotPresent":
	    		wait.until(ExpectedConditions.invisibilityOfElementLocated(getElementWithLocator(WebElement)));
	    		break;
	    	default:
	    		Thread.sleep(Time*1000);
	    		break;
	    	}
    	}
	    	catch(Exception e)
	    	{
	    		throw new IllegalArgumentException("wait For Condition " + TypeOfWait + " isn't supported.");
	    	}
    }
	
}
