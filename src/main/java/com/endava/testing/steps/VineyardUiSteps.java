package com.endava.testing.steps;

import com.endava.testing.pages.GrapesPO;
import com.endava.testing.pages.MenuPO;
import com.endava.testing.pages.MustPO;
import com.endava.testing.pages.WinesPO;
import net.thucydides.core.annotations.Step;

import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class VineyardUiSteps {

    public final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Step("I select {0} from menu.")
    public void selectFromMenu(String must) {

        MenuPO menuPO = new MenuPO();
        menuPO.selectFromMenu(must);
    }

    @Step("I get wine type count.")
    public int getWineTypeCount() {
        WinesPO po = new WinesPO();
        return po.getTypeCount();
    }

    @Step("I get must type count.")
    public int getMustTypeCount() {

        MustPO po = new MustPO();
        return po.getTypeCount();
    }

    @Step("I get grapes type count.")
    public int getGrapeTypeCount() {

        GrapesPO po = new GrapesPO();
        return po.getTypeCount();
    }

    @Step("I get grapes volume.")
    public float getGrapeVolume() {

        GrapesPO po = new GrapesPO();
        return po.getVolume();
    }

    @Step("I get must volume.")
    public float getMustVolume() {

        MustPO po = new MustPO();
        return po.getVolume();
    }

    @Step("I get wine volume.")
    public float getWineVolume() {

        WinesPO po = new WinesPO();
        return po.getVolume();
    }

    @Step("I click crush button.")
    public void clickCrushButton(String grapeName) {

        GrapesPO po = new GrapesPO();
        po.clickCrushButton(grapeName);
    }

    @Step("I select must.")
    public void selectMust(String grapeName) {

        MustPO po = new MustPO();
        po.selectMust(grapeName);
    }

    @Step("I click ferment button.")
    public void clickFerment() {

        MustPO po = new MustPO();
        po.clickFerment();
    }

    @Step("I verify must count.")
    public void verifyMustCount(int expectedCount) {

        assertThat(getMustTypeCount(), is(expectedCount));
        LOGGER.info("Must type count is: " + expectedCount);
    }

    @Step("I verify must Volume")
    public void verifyMustVolume(float expectedVolume) {

        assertThat(getMustVolume(), is(expectedVolume));
        LOGGER.info("Must type volume is: " + expectedVolume);
    }

    @Step("I verify wine count.")
    public void verifyWineTypeCount(int expectedCount) {

        assertThat(getWineTypeCount(), is(expectedCount));
        LOGGER.info("Wine type count is: " + expectedCount);
    }

    @Step("I verify wine volume.")
    public void verifyWineQuantity(double expectedWineQuantity) {

        assertThat((double) getWineVolume(), is(closeTo(expectedWineQuantity, 0.03)));
        LOGGER.info("Wine volume is: " + expectedWineQuantity);

    }
}
