package eci.edu.pgr.snap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.springframework.beans.factory.annotation.Value;

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

public class SnapChat {

    private static Server server;
    private static AppiumDriver<MobileElement> driver;
    private static Thread sent;
    private static Thread receive;
    private static Socket socket;


    @Value("${user}")
    private static String user = "p_ramirezxxx";

    @Value("${psw}")
    private static String psw = "pgr20191";


    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        server = new Server();
        server.instanceServer();
        driver = server.getDriver();
        login();
        clickFirstChat();
        //iniSocket();
        chat();
        while(true){
            listChat();
            Thread.sleep(1000);
        }


    }

    private static void listChat() {
        System.out.println("entro");
        List<MobileElement> reciclerView = driver.findElementByClassName("androidx.recyclerview.widget.RecyclerView").findElementsByClassName("android.widget.LinearLayout");
        String name = "";
        ArrayList<String> conversacion  = new ArrayList<>();
        for (int i = reciclerView.size()-1 ; i >= 0 ; i--){
            List<MobileElement> elements =  reciclerView.get(i).findElementsByClassName("android.widget.TextView");
            //System.out.println(elements.size());
            if(elements.size() > 0){
                name = elements.get(0).getText();
                break;
            }
            //findElementByClassName("javaClass").getText()
            //conversacion.add(reciclerView.get(i).getTagName());
            System.out.println(reciclerView.get(i).getTagName());
        }
        if(!name.equals("Yo")){
            reciclerView.get(reciclerView.size()-1).findElementByClassName("javaClass").getText();
            System.out.println(conversacion);
        }



        /*int lastIndex = reciclerView.size();
        System.out.println(lastIndex);
        String user = reciclerView.get(lastIndex-2).findElementByClassName("android.widget.TextView").getText();
        System.out.println(user);
        System.out.println(user.equals("Yo"));
        user.equals("Yo");*/


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

    private static void chat() {
        write("Esto es un bot");
        /*sent = new Thread(
                new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    while (true) {
                        out.print(userInput.readLine() + "\r\n");
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
        */
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
