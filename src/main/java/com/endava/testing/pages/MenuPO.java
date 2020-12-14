package com.endava.testing.pages;

import org.openqa.selenium.By;

public class MenuPO extends PageObjectBase {

    public void selectFromMenu(String linkText) {

        driver.findElement(MENU)
                .findElement(By.linkText(linkText)).click();
    }
}
