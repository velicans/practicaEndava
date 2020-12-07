package com.endava.testing.pages;

import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

public abstract class PageObjectBase {

    @Managed(driver = "chrome")
    WebDriver driver;
}
