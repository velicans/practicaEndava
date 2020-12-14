package com.endava.testing.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.WithTag;
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
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
@WithTag("API")
public class MustApiTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());

    public Response response;


    public static final By MUST_NAME = By.cssSelector("li:nth-child(1)");
    public static final By MUST_VOLUMES = By.cssSelector("li:nth-child(2)");
    public static final By MUST_TYPES = By.cssSelector("li:nth-child(3");


    @Managed(driver = "chrome")
    WebDriver driver;


    @Before
    public void setUp() {

    }

    @After
    public void close() {

    }

    @Test
    public void testMust() {

        addMust(MUST_NAME, MUST_VOLUMES, MUST_TYPES);
        LOGGER.info("MUST created.");

        getMust();

        String id = getMustId(MUST_NAME);
        LOGGER.info("Must id is:" + id);
        assertThat("", id, not(isEmptyOrNullString()));

        deleteMust(id);
        LOGGER.info("Must deleted.");
        getMust();

        assertThat("", isMustAvailable(MUST_NAME), is(false));
    }

    private String getMustId(By mustName) {

        return JsonPath.with(response.prettyPrint()).get("find { a -> a.name == '" + mustName + "'}.id").toString();
    }

    private Boolean isMustAvailable(By mustName) {

        return JsonPath.with(response.prettyPrint()).get("name").toString().contains((CharSequence) mustName);
    }

    public void addMust(By name, By volume, By type) {

        Map<String, Object> volumeMap = new HashMap<>();
        volumeMap.put("value", volume);
        volumeMap.put("unit", "rows");

        Map<String, Object> nameMap = new HashMap<>();
        nameMap.put("value", name);
        nameMap.put("unit", "rows");


        Map<String, Object> typeMap = new HashMap<>();
        typeMap.put("value", volume);
        typeMap.put("unit", "rows");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("volume", volumeMap);
        bodyMap.put("type", type);



        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("https://endavawineapp.azurewebsites.net/must");

        assertThat(response.getStatusCode(), is(200));

    }

    public void deleteMust(String id) {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("https://endavawineapp.azurewebsites.net/must/" + id);

        assertThat(response.getStatusCode(), is(200));

    }

    public void getMust() {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://endavawineapp.azurewebsites.net/must");

        assertThat(response.getStatusCode(), is(200));

    }
}