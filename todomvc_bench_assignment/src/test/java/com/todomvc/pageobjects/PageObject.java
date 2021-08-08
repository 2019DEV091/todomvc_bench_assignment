package com.todomvc.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class PageObject {

protected WebDriver driver;

    public PageObject(WebDriver driver){

        this.driver = driver;
        /*
        Pagefactory method will take care of instantiating al you WebElements in the PageObjects which extend from the PageObkject class.
        The AjaxElementLocatorFactory will try to find each element you call on for a maximum of 10 seconds (this can be changed to another timeout)
        */
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
    

    protected void hoverOver(WebElement element){      
         Actions actions = new Actions(driver);
         actions.moveToElement(element).perform();
    }

    protected void doubleClick(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).doubleClick().perform();

    }
}
