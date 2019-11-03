package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.*;
public class BaseClass {

    public static AppiumDriver<MobileElement> driver;

    @BeforeTest
    public void setup() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"ANDROID");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.1");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ALE L23");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "G7T7N16B10000883");
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
        //desiredCapabilities.setCapability(MobileCapabilityType.APPLICATION_NAME,"CHROME");
        try {
            URL url = new URL("http://127.0.0.1:4723/wb/hub");
            driver = new AppiumDriver<MobileElement>(url,desiredCapabilities);
        } catch (MalformedURLException e) {
            System.out.println("This cause is  " + e.getCause());
            System.out.println("This messages is  " + e.getMessage());

        }


    }

    @Test
    public void sampleTest(){
            System.out.println("Rocha es marica");
    }

    @AfterTest
    public void tearDown(){

    }
}
