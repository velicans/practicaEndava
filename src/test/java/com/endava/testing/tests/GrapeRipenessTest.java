package com.endava.testing.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Managed;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
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

    public static final String GRAPE_NAME = "new_grape1231"; // schimba valoarea pentru a avea un nou tip de strugure
    public static final String GRAPE_QUANTITY = "12.0";
    public static final String GRAPE_AGE = "12";
    public static final String GRAPE_RIPENESS = "88.0"; // daca valoarea este pe 87.0 o sa avem butonul "pick & crush grapes"

    @Managed(driver = "chrome")
    WebDriver driver;

    @Before
    public void setUp() {

        Map<String, Object> quantity = new HashMap<>();
        quantity.put("value", GRAPE_QUANTITY);
        quantity.put("unit", "rows");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", GRAPE_NAME);
        bodyMap.put("quantity", quantity);
        bodyMap.put("age", GRAPE_AGE);
        bodyMap.put("ripeness", GRAPE_RIPENESS);


        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("https://endavawineapp.azurewebsites.net/grapes");

        System.out.println(response.prettyPrint());

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

        assertThat(getWinesNumber(), greaterThanOrEqualTo(6));
        // assertTrue(getWinesNumber() >= 7);
        LOGGER.info("Wines: " + getWinesNumber());

        assertThat(getVolume(), greaterThanOrEqualTo(2696));
        LOGGER.info("Volume: " + getVolume());


    }

    private int getVolume() {

        String text = driver.findElement(WINES_VOLUMES).getText();

        return (int) Double.parseDouble(StringUtils.substringBetween(text, " "));
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
