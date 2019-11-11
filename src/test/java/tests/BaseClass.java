package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    public static AppiumDriver<MobileElement> driver;

    ReadProperties capProperties = new ReadProperties();

    @Test
    public void sampleTest(){
        System.out.println("Rocha es marica");
    }

    @BeforeTest
    public void setup() {



        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"ANDROID");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "LG Magna LTE");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "4TIBNREMDAMFPB9L");
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
        desiredCapabilities.setCapability("takesScreenshot",true);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.snapchat.android.LandingPageActivity");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.snapchat.android");

        desiredCapabilities.setCapability("app","C:\\Users\\user\\Documents\\Snapchat\\src\\test\\resources\\Snapchat.apk");



        try {
            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            driver = new AppiumDriver<MobileElement>(url,desiredCapabilities);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.findElement(By.id("com.snapchat.android:id/login_and_signup_page_fragment_login_button")).click();
            driver.findElement(By.id("com.snapchat.android:id/username_or_email_field")).setValue("tcontinuum");
            driver.findElement(By.id("com.snapchat.android:id/password_field")).setValue("test12345");
            driver.findElement(By.id("com.snapchat.android:id/button_text")).click();



        } catch (MalformedURLException e) {
            System.out.println("This cause is  " + e.getCause());
            System.out.println("This messages is  " + e.getMessage());

        }


    }



    @AfterTest
    public void tearDown(){

    }
}
