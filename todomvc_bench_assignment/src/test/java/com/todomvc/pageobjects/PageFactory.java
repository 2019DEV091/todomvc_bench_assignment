package com.todomvc.pageobjects;

import org.openqa.selenium.WebDriver;

public class PageFactory {

    public static TodoPage getTodoPage(WebDriver driver) {
        String todoType = System.getProperty("todoType", "vanillajs");

        switch (todoType) {
            case "vue":

                return new VueTodoPage(driver);
            case "jquery":
                return new JQueryTodoPage(driver);
            case "vanillajs":
            default:
                return new VanillaJsTodoPage(driver);
        }
    }
}
