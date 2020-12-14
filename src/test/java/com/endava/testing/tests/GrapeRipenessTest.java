package com.endava.testing.tests;

import com.endava.testing.steps.VineyardApiSteps;
import com.endava.testing.steps.VineyardUiSteps;
import com.endava.testing.utils.CONST;
import com.endava.testing.utils.Utils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@WithTag("UI")
public class GrapeRipenessTest extends UiBaseTest {

    public static final String GRAPE_NAME = "strugureSorin2"; // schimba valoarea pentru a avea un nou tip de strugure
    public static final float GRAPE_QUANTITY = 12;
    public static final int GRAPE_AGE = 12;
    public static final float GRAPE_RIPENESS = 88; // daca valoarea este pe 87.0 o sa avem butonul "pick & crush grapes"

    @Steps
    VineyardUiSteps vineyardUiSteps;

    @Steps
    VineyardApiSteps vineyardApiSteps;

    @Before
    public void setUp() {


        vineyardApiSteps.addGrape(GRAPE_NAME, GRAPE_QUANTITY, GRAPE_AGE, GRAPE_RIPENESS);

    }

    @Test
    public void testGrapeRipeness() {

        vineyardUiSteps.selectFromMenu(CONST.MENU_MUST);
        int mustCount = vineyardUiSteps.getMustTypeCount();
        float mustVolume = vineyardUiSteps.getMustVolume();

        vineyardUiSteps.selectFromMenu(CONST.MENU_WINE);
        int wineCount = vineyardUiSteps.getWineTypeCount();
        int wineVolume = (int) vineyardUiSteps.getWineVolume();

        vineyardUiSteps.selectFromMenu(CONST.MENU_GRAPE);
        vineyardUiSteps.clickCrushButton(GRAPE_NAME);
        Utils.sleep(3);

        float mustQuantity = GRAPE_QUANTITY * 50;
        vineyardUiSteps.verifyMustCount(mustCount + 1);
        // vineyardUiSteps.verifyMustVolume(mustQuantity);


        vineyardUiSteps.selectMust(GRAPE_NAME);
        vineyardUiSteps.clickFerment();
        Utils.sleep(3);

        double wineQuantity = mustQuantity / 1.5;
        vineyardUiSteps.verifyWineTypeCount(wineCount + 1);
        // vineyardUiSteps.verifyWineQuantity(wineQuantity);
        Utils.sleep(3);
    }


}
