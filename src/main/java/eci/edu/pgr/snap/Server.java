package eci.edu.pgr.snap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Server {

    private static AppiumDriver<MobileElement> driver;

    private static DesiredCapabilities desiredCapabilities;

    public Server() {
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0.2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "LG Magna LTE");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "4TIBNREMDAMFPB9L");
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 500);
        desiredCapabilities.setCapability("takesScreenshot", true);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.snapchat.android.LandingPageActivity");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.snapchat.android");
        desiredCapabilities.setCapability("app", "C:\\Users\\user\\Documents\\Snapchat\\src\\main\\resources\\Snapchat.apk");

    }


    public void instanceServer() throws MalformedURLException {
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<MobileElement>(url,desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }



    public  AppiumDriver<MobileElement> getDriver(){
        return driver;
    }



}
