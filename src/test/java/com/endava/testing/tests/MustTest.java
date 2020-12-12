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
public class MustTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());


    public static final String APP_URL = "https://wineappui.azurewebsites.net/";

    public static final By MENU = By.cssSelector("nav");
    public static final By MUST_NAME = By.cssSelector("li:nth-child(1)");
    public static final By MUST_VOLUMES = By.cssSelector("li:nth-child(2)");
    public static final By MUST_TYPES = By.cssSelector("li:nth-child(3)");

    public static final By TABLE = By.cssSelector("table.App-table tbody");

    @Managed(driver = "chrome")
    WebDriver driver;

    @Before
    public void setUp() {

        Map<String, Object> volumes= new HashMap<>();
        volumes.put("value", MUST_VOLUMES);
        volumes.put("unit", "rows");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", MUST_NAME);
        bodyMap.put("volumes", volumes);
        bodyMap.put("type", MUST_TYPES);



        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("https://endavawineapp.azurewebsites.net/must");

        System.out.println(response.prettyPrint());

        driver.get(APP_URL);
    }

    @After
    public void close() {
        driver.quit();
    }

    @Test
    public void testMust() {
        sleep(1);
        selectFromMenu("Must");

        sleep(1);

        assertThat(getMustNumber() *50, greaterThanOrEqualTo(6));
        // assertTrue(getMustNumber() >= 7);
        LOGGER.info("Must: " + getMustNumber()*50);

        assertThat(getVolume(), greaterThanOrEqualTo(2696));
        LOGGER.info("Volume: " + getVolume());

        selectFromMenu("Must");

        clickButton(MUST_NAME);

        sleep(3);

        assertThat("We are not on must page, as expected.", verifyThatWeAreOnMust(), is(true));

        LOGGER.info("Button was clicked.");
    }

    private boolean verifyThatWeAreOnMust() {

        return driver.getCurrentUrl().contains("must");
    }

    private int getVolume() {

        String text = driver.findElement(MUST_VOLUMES).getText();

        return (int) Double.parseDouble(StringUtils.substringBetween(text, " "));
    }

    private int getMustNumber() {

        String text = driver.findElement(MUST_TYPES).getText();

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

    private void clickButton(By mustName) {

        WebElement table = driver.findElement(TABLE);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-child(1)")).getText().contains((CharSequence) mustName)) {
                if (row.findElement(By.tagName("button")).isDisplayed()) {
                    row.findElement(By.tagName("button")).click();
                }
            }
        }
    }
}
