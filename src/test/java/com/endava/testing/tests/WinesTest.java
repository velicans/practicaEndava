package com.endava.testing.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.WithTag;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(SerenityRunner.class)
@WithTag("UI")
public class WinesTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());


    public static final String APP_URL = "https://wineappui.azurewebsites.net/";

    public static final By MENU = By.cssSelector("nav");
    public static final By WINES_TYPES = By.cssSelector("li:nth-child(1)");
    public static final By WINES_VOLUMES = By.cssSelector("li:nth-child(2)");

    public static final By TABLE = By.cssSelector("table.App-table tbody");


    @Managed(driver = "chrome")
    WebDriver driver;

    @Before
    public void setUp() {

        Map<String, Object> volume = new HashMap<>();
        volume.put("value", WINES_VOLUMES);
        volume.put("unit", "rows");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("type", WINES_VOLUMES);
        bodyMap.put("volume", volume);



        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("https://endavawineapp.azurewebsites.net/wines");

        System.out.println(response.prettyPrint());

        driver.get(APP_URL);
    }

    @After
    public void close() {
        driver.quit();
    }

    @Test
    public void testWine() {
        sleep(1);
        selectFromMenu("Wines");

        sleep(1);

        assertThat(getWinesNumber(), greaterThanOrEqualTo(6));
        // assertTrue(getWinesNumber() >= 7);
        LOGGER.info("Wines: " + getWinesNumber());

        assertThat(getVolume(), greaterThanOrEqualTo(2696));
        LOGGER.info("Volume: " + getVolume());

        selectFromMenu("Grapes");

        clickButton(WINES_TYPES);

        sleep(3);

        assertThat("We are not on must page, as expected.", verifyThatWeAreOnWine(), is(true));

        LOGGER.info("Button was clicked.");
    }

    private boolean verifyThatWeAreOnWine() {

        return driver.getCurrentUrl().contains("wine");
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

    private void clickButton(By wineType ){

        WebElement table = driver.findElement(TABLE);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-child(1)")).getText().contains((CharSequence) wineType)) {
                if (row.findElement(By.tagName("button")).isDisplayed()) {
                    row.findElement(By.tagName("button")).click();
                }
            }
        }
    }
}