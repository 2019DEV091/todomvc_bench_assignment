package com.todomvc.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JQueryTodoPage extends TodoPage {

    @FindBy(css="a[href='#/all']")
    private WebElement allView;


    public JQueryTodoPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void goToUrl() {
        get("https://todomvc.com/examples/jquery/#/all");
    }

    @Override
    void clickAllView() {
      allView.click();
        
    }

}
