package com.example;

import com.vimalselvam.testng.listener.ExtentTestNgFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.UUID;

public class BaseTest {

    private static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
                "./src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {

        if (result.getStatus() == ITestResult.FAILURE) {
            String filename = UUID.randomUUID().toString();
            File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scr, new File("./test-output/img/" + filename + ".png"));
            if (ExtentTestNgFormatter.getInstance() != null) {
                ExtentTestNgFormatter.getInstance().addScreenCaptureFromPath(result, "img/" + filename + ".png");
            }
        }
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}