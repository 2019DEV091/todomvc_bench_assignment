package com.todomvc.pageobjects;

import java.util.List;

import com.todomvc.Views;

import org.openqa.selenium.WebDriver;

public abstract class TodoPage extends PageObject {
    
    public TodoPage(WebDriver driver) {
        super(driver);
    }

    public abstract void goToUrl();

    public abstract void createTodo(String todo);

    public abstract String getTodoText(int todoIndex);

    public abstract int getNumberOfTodos();

    public abstract void removeTodo(int todoIndex);

    public abstract void createTodos(List<String> todos);

    public abstract List<String> getAllTodosText();

    public abstract void completeTodo(int todoIndex);

    public abstract Boolean isTodoCompleted(int i);

    public abstract int getNumberOfCompletedTodos();

    public abstract void editTodo(int todoIndex, String editText);

    public abstract void changeViewTo(Views completed);

    public abstract String getTodoCountText();

}
