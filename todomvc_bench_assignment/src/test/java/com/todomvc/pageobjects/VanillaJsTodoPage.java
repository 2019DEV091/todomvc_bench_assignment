package com.todomvc.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VanillaJsTodoPage extends TodoPage {


    @FindBy(css="a[href='#/']")
    private WebElement allView;

    public VanillaJsTodoPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void goToUrl() {
        get("https://todomvc.com/examples/vanillajs/");
    }


    public void clickAllView() {
        allView.click();
    }

}
