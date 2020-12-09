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
public class GrapeApiTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());

    public Response response;

    public static final String GRAPE_NAME = "sorinTest5"; // schimba valoarea pentru a avea un nou tip de strugure
    public static final float GRAPE_QUANTITY = 12;
    public static final int GRAPE_AGE = 5;
    public static final float GRAPE_RIPENESS = 99; // daca valoarea este pe 87.0 o sa avem butonul "pick & crush grapes"

    @Before
    public void setUp() {

    }

    @After
    public void close() {

    }

    @Test
    public void testGrapeRipeness() {

        addGrape(GRAPE_NAME, GRAPE_QUANTITY, GRAPE_AGE, GRAPE_RIPENESS);
        LOGGER.info("Grape created.");

        getGrapes();

        String id = getGrapeId(GRAPE_NAME);
        LOGGER.info("Grape id is:" + id);
        assertThat("", id, not(isEmptyOrNullString()));

        deleteGrape(id);
        LOGGER.info("Grape deleted.");
        getGrapes();

        assertThat("", isGrapeAvailable(GRAPE_NAME), is(false));
    }

    private String getGrapeId(String grapeName) {

        return JsonPath.with(response.prettyPrint()).get("find { a -> a.name == '" + grapeName + "'}.id").toString();
    }

    private Boolean isGrapeAvailable(String grapeName) {

        return JsonPath.with(response.prettyPrint()).get("name").toString().contains(grapeName);
    }

    public void addGrape(String name, float quantity, int age, float ripeness) {

        Map<String, Object> quantityMap = new HashMap<>();
        quantityMap.put("value", quantity);
        quantityMap.put("unit", "rows");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("quantity", quantityMap);
        bodyMap.put("age", age);
        bodyMap.put("ripeness", ripeness);


        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("https://endavawineapp.azurewebsites.net/grapes");

        assertThat(response.getStatusCode(), is(200));

    }

    public void deleteGrape(String id) {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("https://endavawineapp.azurewebsites.net/grapes/" + id);

        assertThat(response.getStatusCode(), is(200));

    }

    public void getGrapes() {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://endavawineapp.azurewebsites.net/grapes");

        assertThat(response.getStatusCode(), is(200));

    }


}
