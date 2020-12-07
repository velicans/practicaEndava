package com.endava.testing.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

@RunWith(SerenityRunner.class)
public class GrapeRipenessTest {

    public static final String GRAPE_NAME = "new_grape2";
    public static final String GRAPE_QUANTITY = "16.0";
    public static final String GRAPE_AGE = "66";
    public static final String GRAPE_RIPENESS = "89.0";
    public static final String APP_URL = "https://endavawineapp.azurewebsites.net";

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
                .post(APP_URL + "/grapes");

        System.out.println(response.prettyPrint());

    }

    @Test
    public void testGrapeRipeness() {
        driver.get(APP_URL);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
