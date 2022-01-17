import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class UI_Test {
    List<WebElement> headers =null;
    WebDriver driver = null;
    WebDriverWait waitdriver = null;
    DataProvider testDataObject;
    String username = "encryptsyn@gmail.com";
    String password = "@Test123";
    @BeforeClass
    public void setTestData(){
        this.testDataObject = new DataProvider();
    }
    @BeforeMethod
    public void setDriver(){
        System.setProperty("webdriver.chrome.driver","/Users/nelisiwenduli/Downloads/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    public void launchHomeUrl(){
        driver.navigate().to("http://automationpractice.com/");
        waitdriver = new WebDriverWait(driver,30);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
    @Test(priority = 1,enabled = true)
    public void TC1(){
       try{
           this.launchHomeUrl();

           Assert.assertTrue(driver.getCurrentUrl().contains("http://automationpractice.com/"));

           String searchCriteria = "Printed Dress";
           driver.findElement(By.id("search_query_top")).sendKeys(searchCriteria);
           driver.findElement(By.name("submit_search")).click();

           String constructedXpath= String.format("//a[contains(text(),'%s') and contains(@class,'product-name')]",searchCriteria );
           Assert.assertNotEquals(driver.findElement(By.xpath(constructedXpath)),null);
           driver.close();

       }catch(Exception e){
           Assert.fail(String.format("Issue while Navigating to Home Page URL is : ",e.getCause() ));
       }
    }
    @Test(enabled=true)
    public void TC2(){
        try{
            this.launchHomeUrl();

            String searchCriteria = "Printed Dress,Blouse,Short";
            String criterias[] = searchCriteria.split(",");

            for (String currentCriteria: criterias) {
                System.out.println(currentCriteria);
                driver.findElement(By.id("search_query_top")).clear();
                driver.findElement(By.id("search_query_top")).sendKeys(currentCriteria);
                driver.findElement(By.name("submit_search")).click();

                String constructedXpath= String.format("//a[contains(text(),'%s') and contains(@class,'product-name')]",currentCriteria );

                Assert.assertNotEquals(driver.findElement(By.xpath(constructedXpath)),null);

            }

        }catch(Exception e){
            Assert.fail(String.format("Issue while Searching with different Criteria is : ",e.getCause() ));
        }
    }

    @Test(priority = 3,enabled = true)
    public void TC3(){
        try{
            this.launchHomeUrl();

            Assert.assertTrue(driver.getCurrentUrl().contains("http://automationpractice.com/"));
            //Pull data fron test data object
            for (String searchKey:this.testDataObject.getData()) {

                driver.findElement(By.id("search_query_top")).sendKeys(searchKey);
                driver.findElement(By.name("submit_search")).click();

                String constructedXpath= String.format("//a[contains(text(),'%s') and contains(@class,'product-name')]",searchKey );
                Assert.assertNotEquals(driver.findElement(By.xpath(constructedXpath)),null);
            }

            driver.close();

        }catch(Exception e){
            Assert.fail(String.format("Issue while Navigating to Home Page URL is : ",e.getCause() ));
        }
    }
    @Test(priority = 4,enabled=true)
    public void TC4(){
        try{
            this.launchHomeUrl();

            Assert.assertTrue(driver.getCurrentUrl().contains("http://automationpractice.com/"));



            driver.findElement(By.xpath("//a[@class='login']")).click();
            driver.findElement(By.id("email")).sendKeys(this.username);
            driver.findElement(By.id("passwd")).sendKeys(this.password);
            driver.findElement(By.id("SubmitLogin")).click();

            Assert.assertNotEquals(driver.findElement(By.xpath("//a[contains(text(),'Sign out')]")),null);


        }catch(Exception e){
            Assert.fail(String.format("Issue while Navigating to Home Page URL is : ",e.getCause() ));
        }
    }
    @Test(priority = 5,enabled=true)
    public void TC5(){
        try{
            this.launchHomeUrl();

            Assert.assertTrue(driver.getCurrentUrl().contains("http://automationpractice.com/"));



            driver.findElement(By.xpath("//a[@class='login']")).click();
            driver.findElement(By.id("email")).sendKeys(this.username);
            driver.findElement(By.id("passwd")).sendKeys(this.password);
            driver.findElement(By.id("SubmitLogin")).click();

            Assert.assertNotEquals(driver.findElement(By.xpath("//a[contains(text(),'Sign out')]")),null);

            String searchCriteria = "Printed Dress";
            driver.findElement(By.id("search_query_top")).sendKeys(searchCriteria);
            driver.findElement(By.name("submit_search")).click();

            String constructedXpath= String.format("//a[contains(text(),'%s') and contains(@class,'product-name')]",searchCriteria );
            driver.findElement(By.xpath(constructedXpath)).click();

            String priceDisplay= driver.findElement(By.id("our_price_display")).getText().replace("$","");
            float itemPrice = Float.parseFloat(priceDisplay);

            Random rand = new Random();
            int unitNumber = rand.nextInt((9))+2;

            driver.findElement(By.id("quantity_wanted")).clear();
            driver.findElement(By.id("quantity_wanted")).sendKeys(String.valueOf(unitNumber));


            driver.findElement(By.xpath("//span[contains(text(),'Add to cart')]")).click();
            float expectedTotalAmount = this.calculateTotal(unitNumber,itemPrice);
            driver.findElement(By.xpath("//span[contains(@title,'Close window')]")).click();
            driver.findElement(By.xpath("//a[contains(@title,'View my shopping cart')]")).click();
            String actualAmount = driver.findElement(By.id("total_product")).getText().replace("$","");


            float actualTotalAmount = Float.parseFloat(actualAmount);

            System.out.println(actualTotalAmount);
            System.out.println(expectedTotalAmount);
            Assert.assertEquals(expectedTotalAmount,actualTotalAmount);

        }catch(Exception e){
            Assert.fail(String.format("calculate cart total issue : ",e.getMessage() ));
        }
    }
    public float calculateTotal(int unitNumber,float itemPrice){
        return unitNumber*itemPrice;
    }

    @Test(priority = 6,enabled = true)
    public void TC6(){
        try{
            this.launchHomeUrl();

            Assert.assertTrue(driver.getCurrentUrl().contains("http://automationpractice.com/"));

            String[] category = {"Women","T-shirts","Dresses"};

            Random rr = new Random();
            String randomCategory = category[rr.nextInt(category.length-1)];
            String constructedXpath= String.format("//a[@title='%s' and contains(@href,'category')]",randomCategory );


            driver.findElement(By.xpath(constructedXpath)).click();


        }catch(Exception e){
            Assert.fail(String.format("Issue Navigating categories : ",e.getCause() ));
        }
    }
    @AfterTest
    public void tearPageDown(){
        //this.driver.close();
    }
}
