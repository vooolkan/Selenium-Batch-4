package utils;



import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.AcceptAlert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonMethods {
	
	public static WebDriver driver;

	public static void setUpDriver(String browser, String url) {
		if (browser.equalsIgnoreCase("chrome")) {
			// For mac
			System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver");
			// for windows
			// System.setProperty("webdriver.chrome.driver",
			// "src/drivers/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver");
			driver = new FirefoxDriver();
		} else {
			System.out.println("browser given is wrong");
		}
		
		driver.manage().window().fullscreen();
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		
	}

	/**
	 * This method will select a specified value from a drop down
	 * @author Syntax 
	 * @param Select element, String text
	 */
	public static void selectValueFromDD(WebElement element, String text) {
		Select select = new Select(element);
		List<WebElement> options = select.getOptions();
		boolean isSelected=false;
		for (WebElement option : options) {
			String optionText = option.getText();
			if (optionText.equals(text)) {
				select.selectByVisibleText(text);
				System.out.println("Option with text "+text+" is selected");
				isSelected=true;
				break;
			}
		}
		if(!isSelected) {
			System.out.println("Option with text +"+text+"is not available");
		}
	}

	/**
	 * This method will select a specified value from a drop down by its index
	 * @author Syntax 
	 * @param Select element, int index
	 */
	public static void selectValueFromDD(WebElement element, int index) {
		Select select = new Select(element);
		List<WebElement> options = select.getOptions();
		if (options.size() > index) {
			select.selectByIndex(index);
		} else {
			System.out.println("Invalid index has been passed");
		}
	}

	public static void sendText(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
	}
/**
 * Method will accept alert
 * @throws NoAlertPresentException if alert is not present
 */
	public static void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException e) {
			System.out.println("Alert was not present");
		}
	}
	/**
	 * Method will dismiss alert
	 * @throws NoAlertPresentException if alert is not present
	 */
	public static void dismissAlert() {
		try {
			Alert alert=driver.switchTo().alert();
			alert.dismiss();
		}catch (NoAlertPresentException e) {
			System.out.println("Alert was not present");
		}
	}
	/**
	 * Method will get text of an alert
	 * @throws NoAlertPresentException if alert is not present
	 * @return String text
	 */
	public static String getAlertText() {
		
		try {
			Alert alert=driver.switchTo().alert();
			return alert.getText();
		}catch (NoAlertPresentException e) {
			System.out.println("Alert was not present");
			return null;
		}
		
	}
	/**
	 * Method that will switch control to the specified frame
	 * @param frame id or frame name
	 */
	public static void switchToFrame(String idOrName) {
		try {
			driver.switchTo().frame(idOrName);
		}catch(NoSuchFrameException e) {
			System.out.println("Frame is not present");
		}
	}
	/**
	 * Method that will switch control to the specified frame
	 * @param frame element
	 */
	public static void switchToFrame(WebElement element) {
		try {
			driver.switchTo().frame(element);
		}catch(NoSuchFrameException e) {
			System.out.println("Frame is not present");
		}
	}
	/**
	 * Method that will switch control to the specified frame
	 * @param frame index
	 */
	public static void switchToFrame(int index) {
		try {
			driver.switchTo().frame(index);
		}catch(NoSuchFrameException e) {
			System.out.println("Frame is not present");
		}
	}
    
	
	
	/**
	 * Method that will enable to click on hover WebElement.
	 * @param webelement;
	 * @author yunus kazan;
	 */
	public  static void actionClick(WebElement element) {
		
		
		Actions action= new Actions(driver);
		
		if(element.isEnabled()) {
			
		action.moveToElement(element).click().perform();
		
		}
		
	}
	
	/**
	 * Method that will enable to click on any button.
	 * @param webelement;
	 * @author yunus kazan;
	 */
	public  static void clickElement(WebElement element) {
		
		if(element.isEnabled()) {
			
		element.click();
		
		}
		
	}
		/**
		 * Method that will enable to select a/all check box of many radio buttons
		 * @author yunuskazan
		 * @param webelement element;
		 * @param String value;
		 * @throws InterruptedException 
		 * 
		 */
	
	public static void radioButton(List<WebElement> element, String value ) throws InterruptedException {
		

		for (int i=1; i<element.size(); i++) {
			
			WebElement eachElement = element.get(i);
			
			String radioButtonText= eachElement.getAttribute("type");
			Thread.sleep(2000);
			eachElement.click();
			
				if (radioButtonText.contains(value)) {
					eachElement.click();
					break;
				}
			}
		}
	
	
	
	/**
	 * Method that will enable to select a/all check box of many check boxes
	 * @author yunuskazan
	 * @param webelement element;
	 * @param String value;
	 * 
	 */

public static void checkBox(List<WebElement> element, String value) {
	
	String ElementValue = null;
	
	for(int i=0; i<element.size(); i++) {
		
		WebElement EachElement= element.get(i);
		    
		    ElementValue= EachElement.getText();
		    
		if(ElementValue.contains(value) & EachElement.isEnabled()) {
			
			EachElement.click();
		}
	}
		
		if((value.equalsIgnoreCase("all"))) {
			
			for(int i=0; i<element.size(); i++) {
				
				WebElement AllElement= element.get(i);
				
				AllElement.click();
		}
	}
}
	
	/**
	 * Method that will enable to select three check box of many check boxes
	 * @author yunuskazan
	 * @param webelement element;   select#sel1
	 * @param String value;
	 * 
	 */

public static void MultipleCheckBox(List<WebElement> element, String value1, String value2, String value3) {
	
	for(int i=0; i<element.size(); i++) {
		
		WebElement EachElement= element.get(i);
		    
		    String ElementValue= EachElement.getText();
		    
		if(ElementValue.contains(value1) || ElementValue.contains(value2) || ElementValue.contains(value3)) {
			
			EachElement.click();
		}
		
	}
	
	
}
	

/**
 * Method that will enable to select drop down menu options by visible text
 * @author yunuskazan
 * @param webelement element;   select#sel1
 * @param String value;
 * 
 */

public static void dropDown(WebElement element, String value) {
	
	boolean isSelected= false;
	
	Select select= new Select(element);

	List<WebElement> allOptions= select.getOptions();
	
	for(WebElement EachOption:allOptions){
	
	         String option= EachOption.getText();
	    
	if(option.contains(value)) {
		
		select.selectByVisibleText(value);
	    isSelected= true;
	    System.out.println("Option with text "+value+" is selected");
	    break;
	}
	
}
	if(!isSelected) { 
		
		System.out.println("Option with text "+value+" is NOT availabe to select"); 
		
	}


}

/**
 * Method that will enable to select a calendar day from 30 days options
 * @author yunuskazan
 * @param List<WebElement> Table; 
 * @param String Day;
 * 
 */
		public static void calender(List<WebElement> Table, String value) throws InterruptedException {
		
			List <WebElement> cells=Table;
			
			for(WebElement cell: cells) {
				
				String EachText= cell.getText();
				
				if(EachText.contains(value)) {
					cell.click();
					break;
				}
		}
}
		
		/**
		 * Method that will enable to select verify a date 
		 * @author yunuskazan
		 * @param List<WebElement> Table; 
		 * @param String Day;
		 * 
		 */
				public static void verifyDate(List<WebElement> Table, String value) throws InterruptedException {
				
					List <WebElement> cells=Table;
					
					for(WebElement cell: cells) {
						
						String EachText= cell.getText();
						
						if(EachText.contains(value)) {
							cell.click();
							break;
						}
				}
		}
		
		
		
				/**
				 * Method that will enable for element to be waited until visible
				 * @author yunuskazan
				 * @param WebElement element, int time
				 */
				public static void waitForElementBeVisible(WebElement element, int time) {
					WebDriverWait wait = new WebDriverWait(driver, time);
					wait.until(ExpectedConditions.visibilityOf(element));
				}

				public static void waitForElementBeVisible(By locator, int time) {
					WebDriverWait wait = new WebDriverWait(driver, time);
					wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				}

				public static void waitForElementBeClickable(WebElement element, int time) {
					WebDriverWait wait = new WebDriverWait(driver, time);
					wait.until(ExpectedConditions.elementToBeClickable(element));
				}

				public static void waitForElementBeClickable(By locator, int time) {
					WebDriverWait wait = new WebDriverWait(driver, time);
					wait.until(ExpectedConditions.elementToBeClickable(locator));
				}
				
				
				/**
				 * Method that will enable to take screenshot
				 * @author yunuskazan
				 * @param String folder name, string  time
				 */
				
		public static void takeAscreenShot(String folderName, String fileName) {
			
			TakesScreenshot camera= (TakesScreenshot)driver;
			
			File newfile= camera.getScreenshotAs(OutputType.FILE);
			
			try {
				FileUtils.copyFile(newfile, new File("screenShots/"+folderName+"/"+fileName+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.out.println("Screenshot was Not taken");
			}
			
		}
		
		/**
		 * Method that will enable to upload a file
		 * @author yunuskazan
		 * @param WebElement elementSendkey, WebElement elementUpload, WebElement elementDisplayed, String filePath;
		 */
		
public static void uploadFile(WebElement elementSendkey, WebElement elementUpload, WebElement elementDisplayed, String filePath) {
	
	elementSendkey.sendKeys(filePath);
	elementUpload.click();
	
	boolean isDisplayed=elementDisplayed.isDisplayed();
	
	if (isDisplayed) {
		System.out.println("File uploaded successfully");
	}else {
		System.out.println("File uploaded successfully");
	}
	
}

/**
 * Method that will enable to scroll down on a web page
 * @author yunus kazan
 * @param int pixel
 */

public static void scrollDown(int pixels) {
	JavascriptExecutor js=(JavascriptExecutor)driver;
	js.executeScript("window.scrollBy(0,"+pixels+")");
}

/**
 * Method that will enable to scroll Up on a webpage
 * @author yunuskazan
 * @param int pixel
 */

public static void scrollUp(int pixels) {
	JavascriptExecutor js=(JavascriptExecutor)driver;
	js.executeScript("window.scrollBy(0,-"+pixels+")");
}

/**
 * Method that will enable to click by JS.executer on a webpage
 * @author yunuskazan
 * @param WebElement element
 */

public static void jsClick(WebElement element) {
	
	JavascriptExecutor js=(JavascriptExecutor)driver;
	js.executeScript("arguments[0].click();", element);
}
		
	
   public static void AlertFunction(WebDriver driver, WebElement element, String text ) throws InterruptedException {
	   
	   element.click();
	   
	   Thread.sleep(3000);
	   
	 Alert alert= driver.switchTo().alert();
	    
	    alert.getText();
	      
	    if(alert.getText().equals(text)) {
	    
	    	System.out.println(alert.getText()+" Text is available.");
	
	    	alert.accept();
	    	
	    } else if(!alert.getText().equals(text)) {
	    	
	    	System.out.println(alert.getText()+" Text is NOT available.");
	    	
	    	alert.accept();
	    	
	    }
	    
	 driver.quit();
   }
   
   
   
public static void ConfirmPopupsFunction(WebDriver driver, WebElement element, String text ) throws InterruptedException {
	   
		
		element.click();
		
		Thread.sleep(3000);
		
	    Alert confirmPopup= driver.switchTo().alert();
	   
	    String confirmPopupText=  confirmPopup.getText();
	      
	    if(confirmPopupText.equals(text)) {
	    
	    	System.out.println(confirmPopup.getText()+" Text is available.");
	    	
	    	Thread.sleep(3000);
	    	confirmPopup.accept();
	    	
	    	
	    } else if(!confirmPopupText.equals(text)) {
	    	
	    	System.out.println(confirmPopup.getText()+" Text is NOT available.");
	    	Thread.sleep(3000);
	    	confirmPopup.dismiss();
	    	
	    }else {
	    	
	    	System.out.println(confirmPopup.getText()+" Text is NOT available.");
	    }
	    
	   
   }


public static void PromptPopupsFunction(WebDriver driver, WebElement element, String text ) throws InterruptedException {
	   
		
		element.click();
		
		Thread.sleep(3000);
	   
	    Alert PromptPopup= driver.switchTo().alert();
	   
	    String PromptPopupText=  PromptPopup.getText();
	      
	    if(PromptPopupText.equals(text)) {
	    
	    	System.out.println(PromptPopup.getText()+" Text is available.");
	    	
	    	Thread.sleep(3000);
	    	PromptPopup.accept();
	    	
	    } else if(!PromptPopupText.equals(text)) {
	    	
	    	System.out.println(PromptPopup.getText()+" Text is NOT available.");
	    	
	    	Thread.sleep(3000);
	    	PromptPopup.dismiss();
	    	
	    }else {
	    	
	    	System.out.println(PromptPopup.getText()+" Text is NOT available.");
	    }
	    
	   
}
    
    
    
}
