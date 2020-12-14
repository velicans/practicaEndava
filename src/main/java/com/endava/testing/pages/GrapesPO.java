package com.endava.testing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class GrapesPO extends PageObjectBase {

    public void clickCrushButton(String grapeName) {

        WebElement table = driver.findElement(TABLE);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            if (row.findElement(By.cssSelector("td:nth-child(1)")).getText().contains(grapeName)) {
                if (row.findElement(By.tagName("button")).isDisplayed()) {
                    row.findElement(By.tagName("button")).click();
                }
            }
        }
    }
}
