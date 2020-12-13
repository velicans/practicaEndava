package com.endava.testing.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.WithTag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
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

    public static final String MUST_NAME = "sorinTest"; // schimba valoarea pentru a avea un nou tip de strugure

    public static final float Must_QUANTITY = 12;
    public static final String MUST_TYPE = "cola";

    @Before
    public void setUp() {

    }

    @After
    public void close() {

    }

    @Test
    public void testMustApi() {

        addMust(MUST_NAME, Must_QUANTITY, MUST_TYPE);
        LOGGER.info("Must created.");

        getMust();

        String id = getGrapeId(MUST_NAME);
        LOGGER.info("Must id is:" + id);
        assertThat("", id, not(isEmptyOrNullString()));

        deleteMust(id);
        LOGGER.info("Must deleted.");
        getMust();

        assertThat("", isGrapeAvailable(MUST_NAME), is(false));
    }

    private String getGrapeId(String grapeName) {

        return JsonPath.with(response.prettyPrint()).get("find { a -> a.name == '" + grapeName + "'}.id").toString();
    }

    private Boolean isGrapeAvailable(String grapeName) {

        return JsonPath.with(response.prettyPrint()).get("name").toString().contains(grapeName);
    }

    public void addMust(String name, float quantity, String type) {

        Map<String, Object> quantityMap = new HashMap<>();
        quantityMap.put("value", quantity);
        quantityMap.put("unit", "liters");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("volume", quantityMap);
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
                .body("[" + id + "]")
                .when()
                .delete("https://endavawineapp.azurewebsites.net/must/");

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
