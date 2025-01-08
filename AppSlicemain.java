package AppSliceAPP;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;	

public class AppSlicemain {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/Users/sayedmoataz/Downloads/chromedriver-mac-x64/chromedriver");
		WebDriver driver = new ChromeDriver();
        driver.navigate().to("https://w3news.ai");
        String pageTitle = driver.getTitle();
        System.out.println("pageTitle is: " + pageTitle);
        try {
        	var x = driver.findElement(By.id("id_DiscoverBtn"));
        	x.click();
        	var emailTextField = driver.findElement(By.id("id_nameGetTouch"));
        	emailTextField.sendKeys("sayedmoataz9@gmail.com");
        	var passWordTextField = driver.findElement(By.id("id_emailGetTouch"));
        	passWordTextField.sendKeys("123456789");
        	var signInButton = driver.findElement(By.id("id_DiscoverBtn"));
        	signInButton.click();
        } catch (Exception e) {
        	System.out.println("Error Loading :" + e);		
    	}
	}
}






