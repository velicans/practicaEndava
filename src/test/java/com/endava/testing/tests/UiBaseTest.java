package com.endava.testing.tests;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Managed;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

import static com.endava.testing.utils.DriverFactory.getDriver;
import static com.endava.testing.utils.DriverFactory.setDriver;

public class UiBaseTest {

    public static final String APP_URL = "https://wineappui.azurewebsites.net/";

    public final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Managed
    private static RemoteWebDriver driver;

    @Rule
    public TestRule captureScreenshotOnFailure = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            try {
                String name = "screenshot-" + UUID.randomUUID();

                Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE)
                        .withName(name)
                        .save(Paths.get("target", "screenshots").toAbsolutePath().toString());

                Serenity.recordReportData()
                        .withTitle("screenshot")
                        .downloadable().fromFile(Paths.get("target", "screenshots", name + ".png"));
            } catch (Exception ex) {
                LOGGER.warning("Could not capture screenshot on failure!" + ex);
            }
        }

        @Override
        protected void finished(Description description) {
            if (driver != null) {
                driver.quit();
            }
        }
    };

    @Before
    public void openBrowser() {

        setDriver();
        driver = getDriver();

        driver.get(APP_URL);
    }

    @After
    public void close() {
        driver.quit();
    }


}
