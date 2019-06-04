import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class jenkins_demo
{

@Test
public void testgooglesearch(){

System.setProperty("webdriver.gecko.driver","C:\\gecko\\geckodriver.exe");


WebDriver driver = new FirefoxDriver();
//it will open the goggle page
driver.get("http://localhost:8080/myproject/welcome"); 
//we expect the title “Google “ should be present 
String Expectedtitle = "Welcome to servlet from Jerry";
//it will fetch the actual title 
//String Actualtitle = driver.getTitle();
//System.out.println("Before Assetion " + Expectedtitle + Actualtitle);
String Actualtitle = driver.findElement(By.xpath("/html/body")).getText();
//it will compare actual title and expected title
Assert.assertEquals(Actualtitle, Expectedtitle);
//print out the result
System.out.println("After Assertion " + Expectedtitle + Actualtitle + " Title matched ");
driver.quit();
 }
}
