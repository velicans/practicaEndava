package com.endava.testing.tests;

import com.endava.testing.steps.VineyardApiSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
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
@WithTag("API")
public class MustApiTest {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Steps
    VineyardApiSteps vineyardApiSteps;

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

        vineyardApiSteps.addMust(MUST_NAME, Must_QUANTITY, MUST_TYPE);
        LOGGER.info("Must created.");

        vineyardApiSteps.getMust();

        String id = vineyardApiSteps.getMustId(MUST_NAME);
        LOGGER.info("Must id is:" + id);
        assertThat("", id, not(isEmptyOrNullString()));

        vineyardApiSteps.deleteMust(id);
        LOGGER.info("Must deleted.");
        vineyardApiSteps.getMust();

        assertThat("", vineyardApiSteps.isMustAvailable(MUST_NAME), is(false));
    }


}
