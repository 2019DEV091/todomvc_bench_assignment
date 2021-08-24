package com.todomvc.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VueTodoPage extends TodoPage {

    //I could have searched another locator that matches all implementations, then my classes would be even smaller
    //I also could have used the @FindAll annotation to provide a group of locators to match against
    @FindBy(css = "a[href='#/all']")
    private WebElement allView;

    public VueTodoPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void goToUrl() {
        get("https://todomvc.com/examples/vue/");

    }

    @Override
    void clickAllView() {
        allView.click();

    }

}
