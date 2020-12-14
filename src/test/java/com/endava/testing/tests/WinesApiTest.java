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
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
@WithTag("API")
public class WinesApiTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());

    public Response response;


    public static final By WINE_COMPOSITION = By.cssSelector("li:nth-child(0)");
    public static final By WINE_TYPE = By.cssSelector("li:nth-child(1)");
    public static final By WINE_VOLUME = By.cssSelector("li:nth-child(2)");
    public static final By WINE_BOTTLING = By.cssSelector("li:nth-child(4");


    @Before
    public void setUp() {

    }

    @After
    public void close() {

    }

    @Test
    public void testWine() {

        addWine(WINE_COMPOSITION, WINE_TYPE, WINE_VOLUME,WINE_BOTTLING);
        LOGGER.info("Wine created.");

        getWine();

        String id = getWineId(WINE_TYPE);
        LOGGER.info("Wine id is:" + id);
        assertThat("", id, not(isEmptyOrNullString()));

        deleteWine(id);
        LOGGER.info("Wine deleted.");
        getWine();

        assertThat("", isWineAvailable(WINE_TYPE), is(false));
    }

    private String getWineId(By wineType) {

        return JsonPath.with(response.prettyPrint()).get("find { a -> a.type == '" + wineType + "'}.id").toString();
    }

    private Boolean isWineAvailable(By wineType) {

        return JsonPath.with(response.prettyPrint()).get("type").toString().contains((CharSequence) wineType);
    }

    public void addWine(By composition, By type,By volume,By bottling) {

        Map<String, Object> compositionMap = new HashMap<>();
        compositionMap.put("value", composition);
        compositionMap.put("unit", "rows");

        Map<String, Object> typeMap = new HashMap<>();
        typeMap.put("value", type);
        typeMap.put("unit", "rows");


        Map<String, Object> volumeMap = new HashMap<>();
        volumeMap.put("value", volume);
        volumeMap.put("unit", "rows");

        Map<String, Object> bottlingMap = new HashMap<>();
        bottlingMap.put("value", volume);
        bottlingMap.put("unit", "rows");

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("composition", composition);
        bodyMap.put("type", type);
        bodyMap.put("volume",volume);
        bodyMap.put("bottling",bottling);



        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .when()
                .post("https://endavawineapp.azurewebsites.net/wines");

        assertThat(response.getStatusCode(), is(200));

    }

    public void deleteWine(String id) {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("https://endavawineapp.azurewebsites.net/wines/" + id);

        assertThat(response.getStatusCode(), is(200));

    }

    public void getWine() {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://endavawineapp.azurewebsites.net/wines");

        assertThat(response.getStatusCode(), is(200));

    }
}