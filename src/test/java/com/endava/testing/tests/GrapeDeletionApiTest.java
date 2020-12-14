package com.endava.testing.tests;

import com.endava.testing.steps.VineyardApiSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@WithTag("API")
public class GrapeDeletionApiTest {

    @Steps
    VineyardApiSteps vineyardApiSteps;

    public static final String GRAPE_NAME = "strugureSorin"; // schimba valoarea pentru a avea un nou tip de strugure

    @Test
    public void testGrapeDeletion() {

        while (vineyardApiSteps.isGrapeAvailable(GRAPE_NAME)) {
            vineyardApiSteps.deleteGrape(vineyardApiSteps.getGrapeId(GRAPE_NAME));
        }
    }

    @Test
    public void testMustDeletion() {

        while (vineyardApiSteps.isMustAvailable(GRAPE_NAME)) {
            vineyardApiSteps.deleteMust(vineyardApiSteps.getMustId(GRAPE_NAME));
        }
    }


}

