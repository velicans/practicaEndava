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

@RunWith(SerenityRunner.class)
@WithTag("UI")
public class GrapeRipenessTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());


    public static final String APP_URL = "https://wineappui.azurewebsites.net/";

    public static final By MENU = By.cssSelector("nav");
    public static final By WINES_TYPES = By.cssSelector("li:nth-child(1)");
    public static final By WINES_VOLUMES = By.cssSelector("li:nth-child(2)");

    public static final By TABLE = By.cssSelector("table.App-table tbody");

    public static final String GRAPE_NAME = "strugureSorin2"; // schimba valoarea pentru a avea un nou tip de strugure
    public static final float GRAPE_QUANTITY = 12;
    public static final int GRAPE_AGE = 12;
    public static final float GRAPE_RIPENESS = 88; // daca valoarea este pe 87.0 o sa avem butonul "pick & crush grapes"

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

        selectFromMenu("Must");
        int mustCount = getTypeCount();
        float mustVolume = getVolume();

        selectFromMenu("Wines");
        int wineCount = getTypeCount();
        int wineVolume = (int) getVolume();

        selectFromMenu("Grapes");
        clickCrushButton(GRAPE_NAME);
        sleep(1);

        assertThat(getTypeCount(), is(mustCount + 1));
        assertThat(getVolume(), is(mustVolume + (GRAPE_QUANTITY * 50)));

        selectMust(GRAPE_NAME);
        clickFerment();
        sleep(1);

        assertThat(getTypeCount(), is(wineCount + 1));
        assertThat(getTypeCount(), is(wineCount + 1));
        sleep(1);
    }

    private boolean verifyThatWeAreOnMust() {

        return driver.getCurrentUrl().contains("must");
    }

    private float getVolume() {

        String text = driver.findElement(WINES_VOLUMES).getText();

        return Float.parseFloat(StringUtils.substringBetween(text, ": ", " liters"));
    }

    private int getTypeCount() {

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

    private void clickCrushButton(String grapeName) {

        WebElement table = driver.findElement(TABLE);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-child(1)")).getText().contains(grapeName)) {
                if (row.findElement(By.tagName("button")).isDisplayed()) {
                    row.findElement(By.tagName("button")).click();
                }
            }
        }
    }

    private void selectMust(String grapeName) {

        WebElement table = driver.findElement(TABLE);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-child(2)")).getText().contains(grapeName)) {
                if (row.findElement(By.cssSelector("input[type=checkbox]")).isDisplayed()) {
                    row.findElement(By.cssSelector("input[type=checkbox]")).click();
                }
            }
        }
    }

    private void clickFerment() {
        if (driver.findElement(By.cssSelector("button")).isDisplayed()) {
            driver.findElement(By.cssSelector("button")).click();
        }
    }
}
