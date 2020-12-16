package com.endava.testing.tests;

import com.endava.testing.steps.VineyardApiSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
@WithTags({
        @WithTag("API"),
        @WithTag("grape")
})
public class GrapeApiFailTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Steps
    VineyardApiSteps vineyardApiSteps;

    public static final String GRAPE_NAME = "sorinTest8"; // schimba valoarea pentru a avea un nou tip de strugure

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
    public void testGrapeApi() {

        vineyardApiSteps.addGrape(GRAPE_NAME, GRAPE_QUANTITY, GRAPE_AGE, GRAPE_RIPENESS);
        LOGGER.info("Grape created.");

        vineyardApiSteps.getGrapes();

        String id = vineyardApiSteps.getGrapeId(GRAPE_NAME);
        LOGGER.info("Grape id is:" + id);
        assertThat("", id, not(isEmptyOrNullString()));

        vineyardApiSteps.deleteGrape(id);
        LOGGER.info("Grape deleted.");
        vineyardApiSteps.getGrapes();

        assertThat("", vineyardApiSteps.isGrapeAvailable(GRAPE_NAME), is(true));
    }


}
