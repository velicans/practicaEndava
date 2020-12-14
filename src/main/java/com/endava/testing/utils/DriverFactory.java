package com.endava.testing.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.annotations.Managed;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    @Managed
    private static RemoteWebDriver driver;

    public static RemoteWebDriver getDriver() {
        return driver;
    }

    public static void setDriver() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(getChromeOption());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    protected static ChromeOptions getChromeOption() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");

        if (!SystemUtils.IS_OS_WINDOWS && !SystemUtils.IS_OS_MAC) {
            chromeOptions.addArguments("--no-sandbox"); // needed for Ubuntu
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("window-size=1920,1080");
        }

        return chromeOptions;

    }

}
