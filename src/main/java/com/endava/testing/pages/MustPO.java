package com.endava.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MustPO extends PageObjectBase {

    public void clickFerment() {
        if (driver.findElement(By.cssSelector("button")).isDisplayed()) {
            driver.findElement(By.cssSelector("button")).click();
        }
    }

    public void selectMust(String grapeName) {

        WebElement table = driver.findElement(TABLE);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-child(2)")).getText().contains(grapeName)) {
                if (row.findElement(By.cssSelector("input[type=checkbox]")).isDisplayed()) {
                    row.findElement(By.cssSelector("input[type=checkbox]")).click();
                }
            }
        }
    }
}
