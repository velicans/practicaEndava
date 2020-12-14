package com.endava.testing.pages;

import com.endava.testing.utils.DriverFactory;
import net.thucydides.core.annotations.Managed;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class PageObjectBase {

    public static final By MENU = By.cssSelector("nav");
    public static final By WINES_TYPES = By.cssSelector("li:nth-child(1)");
    public static final By WINES_VOLUMES = By.cssSelector("li:nth-child(2)");

    public static final By TABLE = By.cssSelector("table.App-table tbody");

    @Managed
    protected static RemoteWebDriver driver;

    public PageObjectBase() {

        driver = DriverFactory.getDriver();
    }
    
    public float getVolume() {

        String text = driver.findElement(WINES_VOLUMES).getText();

        return Float.parseFloat(StringUtils.substringBetween(text, ": ", " liters"));
    }

    public int getTypeCount() {

        String text = driver.findElement(WINES_TYPES).getText();

        return Integer.parseInt(text.replaceAll("\\D+", ""));
    }


}
