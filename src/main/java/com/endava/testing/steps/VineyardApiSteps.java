package com.endava.testing.steps;

import com.endava.testing.utils.CONST;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VineyardApiSteps {


    public Response response;

    @Step("I get id for {0}")
    public String getGrapeId(String grapeName) {

        return JsonPath.with(response.prettyPrint()).get("find { a -> a.name == '" + grapeName + "'}.id").toString();
    }

    @Step("I check if {0} is available.")
    public Boolean isGrapeAvailable(String grapeName) {

        return JsonPath.with(response.prettyPrint()).get("name").toString().contains(grapeName);
    }

    @Step("I add a new grape.")
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
                .post(CONST.GRAPE_API_URL);

        assertThat(response.getStatusCode(), is(200));

    }

    @Step("I delete a grape.")
    public void deleteGrape(String id) {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .delete(CONST.GRAPE_API_URL + id);

        assertThat(response.getStatusCode(), is(200));

    }

    @Step("I get all grapes.")
    public void getGrapes() {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .get(CONST.GRAPE_API_URL);

        assertThat(response.getStatusCode(), is(200));

    }

    @Step("I get must id for {0}.")
    public String getMustId(String grapeName) {

        return JsonPath.with(response.prettyPrint()).get("find { a -> a.name == '" + grapeName + "'}.id").toString();
    }

    @Step("I check if must is available.")
    public Boolean isMustAvailable(String grapeName) {

        getMust();
        return JsonPath.with(response.prettyPrint()).get("name").toString().contains(grapeName);
    }

    @Step("I add a new must.")
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
                .post(CONST.MUST_API_URL);

        assertThat(response.getStatusCode(), is(200));

    }

    @Step("I delete a must.")
    public void deleteMust(String id) {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body("[" + id + "]")
                .when()
                .delete(CONST.MUST_API_URL);

        assertThat(response.getStatusCode(), is(200));

    }

    @Step("I get all musts.")
    public void getMust() {

        response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .when()
                .get(CONST.MUST_API_URL);

        assertThat(response.getStatusCode(), is(200));

    }

}
