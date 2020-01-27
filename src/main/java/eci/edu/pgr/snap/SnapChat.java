package eci.edu.pgr.snap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.springframework.beans.factory.annotation.Value;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SnapChat {

    private static Server server;
    private static AppiumDriver<MobileElement> driver;
    private static Thread sent;
    private static Thread receive;
    private static Socket socket;

    private static Logger LOGGER = Logger.getLogger(String.valueOf(SnapChat.class));

    private static List<MobileElement> recycleViewChildrens = null;

    private static boolean garbageNextMessage = false;


    @Value("${user}")
    private static String user = "p_ramirezxxx";

    @Value("${psw}")
    private static String psw = "pgr20191";


    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        //server = new Server();
        //server.instanceServer();
        //driver = server.getDriver();
        //login();
        //clickFirstChat();
        iniSocket();
        chat();
    }

    private static String reponseChat() {
        if (existParentsElements(ComponentName.recyleviewlist)) {
            recycleViewChildrens = driver.findElements(By.xpath(ComponentName.recyleviewlist));
            int mobileElementIth = preProcess();
            if (mobileElementIth == -1) return "";
            while (mobileElementIth >= 0) {
                MobileElement mb = recycleViewChildrens.get(mobileElementIth);
                String className_ = (mb.getAttribute("className"));
                String className[] = className_.split("\\.");
                if (className.length == 3) {
                    LOGGER.info("Se va a procesar " + className[2]);
                    if (className[2].equals(ComponentName.LINEAR_LAYOUT)) {
                        if (isValid(mobileElementIth) && isXComponent(mobileElementIth, ComponentName.TEXT_VIEW)) {
                            MobileElement textView = recycleViewChildrens.get(mobileElementIth + 1);
                            String textOfView = textView.getAttribute("className");
                            System.out.println("Attribute " + textOfView + " " + textView.getText());
                            mobileElementIth++;
                            if (textView.getText().equals("Yo")) {
                                garbageNextMessage = true;
                            }
                        } else if (isValid(mobileElementIth) && isXComponent(mobileElementIth, ComponentName.VIEW)) {
                            String message = "";
                            do {
                                mobileElementIth += 2; // isJavaClass, next element is Vies and other element, we wait that will be javaClass
                                MobileElement javaClass = recycleViewChildrens.get(mobileElementIth);
                                if (javaClass.getAttribute("className").equals("javaClass")) {
                                    message = message + javaClass.getText() + '\n';
                                }
                            } while (isValid(mobileElementIth) && isXComponent(mobileElementIth, ComponentName.VIEW));
                            if (garbageNextMessage) {
                                LOGGER.info(message.substring(0, message.length() - 1));
                                garbageNextMessage = false;
                            }
                            return message;
                        }
                    }


                }
                mobileElementIth++;
            }
        }
        return "";
    }



    private static int preProcess(){
       for(int i = recycleViewChildrens.size()-1; i>=0 ; i--){
            if(recycleViewChildrens.get(i).getAttribute("className").contains(ComponentName.TEXT_VIEW)){
                if(i-1>=0 && recycleViewChildrens.get(i).getAttribute("className").contains(ComponentName.LINEAR_LAYOUT)){
                    if(i+1<recycleViewChildrens.size() && isXComponent(i,ComponentName.LINEAR_LAYOUT)){
                        return i;
                    }
                }
            }
        }
        return -1;

    }

    private static boolean isValid(int i){
        return ((i+1 >=0) ? false : true);
    }

    private static boolean isXComponent(int i , String component){
        String className[] = recycleViewChildrens.get(i+1).getAttribute("className").split("\\.");
        if(className.length ==3 ){
            return className[2].equals(component);
        }
        return false;
    }




    private static void iniSocket() {
        try {
            socket = new Socket("localhost", 9999);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static void write(String res){
        driver.findElement(By.id("com.snapchat.android:id/chat_input_text_field")).sendKeys(res);
        Robot r = null;
        try {
            r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }


    private static boolean existParentsElements(String nameElment){
        try{
            List<MobileElement> recycleViewChildrens = driver.findElements(By.xpath(ComponentName.recyleviewlist));
            return true;
        }catch (Exception e){
            return false;
        }

    }

    private static void chat() {
        //write("Esto es un bot");
        sent = new Thread(
                new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    while (true) {
                        out.print(" Kha ");
                        out.flush();
                        System.out.println("Trying to read...");
                        String in = stdIn.readLine();
                        System.out.println(in);
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        sent.start();
        try {
            sent.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clickFirstChat() throws InterruptedException {
        List<MobileElement> ele = driver.findElements(By.className("android.view.View"));
        ele.get(0).click();
        Thread.sleep(10000);

    }

    private static void login() throws InterruptedException {
        driver.findElement(By.id("com.snapchat.android:id/login_and_signup_page_fragment_login_button")).click();
        driver.findElement(By.id("com.snapchat.android:id/username_or_email_field")).setValue(user);
        driver.findElement(By.id("com.snapchat.android:id/password_field")).setValue(psw);
        driver.findElement(By.id("com.snapchat.android:id/button_text")).click();
        driver.findElement(By.id("com.snapchat.android:id/feed_icon_container")).click();
        Thread.sleep(30000);
    }
}
