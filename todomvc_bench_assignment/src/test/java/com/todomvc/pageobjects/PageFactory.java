package com.todomvc.pageobjects;

import org.openqa.selenium.WebDriver;

public class PageFactory {

    /**
     * This method was created because I wanted to decouple the creation of my
     * TodoPage instance from my tests.
     * 
     * @param todoType
     * @param driver
     * @return an instance of TodoPage, depending on the given todoType string
     */
    public static TodoPage getTodoPage(String todoType, WebDriver driver) {

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

    /**
     * This method was created because I wanted to decouple the creation of my
     * TodoPage instance from my tests.
     * 
     * @param driver
     * @return an instance of TodoPage, depending on the given todoType string
     */
    public static TodoPage getTodoPage(WebDriver driver) {

        String todoType = System.getProperty("todoType");
        return getTodoPage(todoType, driver);
    }
}
