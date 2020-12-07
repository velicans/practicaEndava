package com.endava.testing.tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(SerenityRunner.class)
public class GrapeRipenessTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());


    public static final String APP_URL = "https://wineappui.azurewebsites.net/";

    public static final By MENU = By.cssSelector("nav");
    public static final By WINES_TYPES = By.cssSelector("li:nth-child(1)");
    public static final By WINES_VOLUMES = By.cssSelector("li:nth-child(2)");

    @Managed(driver = "chrome")
    WebDriver driver;

    @Before
    public void setUp() {

        driver.get(APP_URL);
    }

    @After
    public void close() {
        driver.quit();
    }

    @Test
    public void testGrapeRipeness() {
        sleep(1);
        selectFromMenu("Wines");

        sleep(1);

        assertThat(getWinesNumber(), greaterThanOrEqualTo(7));
        // assertTrue(getWinesNumber() >= 7);
        LOGGER.info("Wines: " + getWinesNumber());

        assertThat(getVolume(), greaterThanOrEqualTo(2996));
        LOGGER.info("Volume: " + getVolume());
        

    }

    private int getVolume() {

        String text = driver.findElement(WINES_VOLUMES).getText();

        return Integer.parseInt(text.replaceAll("\\D+", ""));
    }

    private int getWinesNumber() {

        String text = driver.findElement(WINES_TYPES).getText();

        return Integer.parseInt(text.replaceAll("\\D+", ""));
    }

    private void selectFromMenu(String linkText) {

        driver.findElement(MENU)
                .findElement(By.linkText(linkText)).click();
    }

    private void sleep(int seconds) {

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
