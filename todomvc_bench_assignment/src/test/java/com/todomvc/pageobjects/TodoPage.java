package com.todomvc.pageobjects;

import java.util.List;
import java.util.stream.Collectors;

import com.todomvc.Views;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Parent of all TodoPage implementations.<br>
 * methods and fields which are the same for each implementations are kept here
 * so I have less duplication.
 */
public abstract class TodoPage extends PageObject {

    @FindBy(css = ".new-todo")
    protected WebElement todoInput;

    @FindBy(css = ".todo-list li")
    protected List<WebElement> todos;

    @FindBy(css = ".todo-list li.completed")
    protected List<WebElement> completedTodos;

    @FindBy(css = "a[href='#/completed']")
    protected WebElement completedView;

    @FindBy(css = ".todo-count")
    protected WebElement todoCount;

    public TodoPage(WebDriver driver) {
        super(driver);
    }

    protected void get(String url) {
        driver.get(url);
    }

    public abstract void goToUrl();

    abstract void clickAllView();

    public void createTodo(String todo) {
        todoInput.sendKeys(todo + Keys.ENTER);
    }

    public String getTodoText(int todoIndex) {
        return getTodo(todoIndex).findElement(By.cssSelector("label")).getText();
    }

    private WebElement getTodo(int todoIndex) {
        return todos.get(todoIndex);

    }

    public int getNumberOfTodos() {
        return todos.size();
    }

    public void removeTodo(int todoIndex) {

        WebElement todoToRemove = getTodo(todoIndex);
        hoverOver(todoToRemove);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(todoToRemove.findElement(By.cssSelector(".destroy"))))
                .click();
    }

    public void createTodos(List<String> todos) {
        for (String todo : todos) {
            createTodo(todo);
        }
    }

    public List<String> getAllTodosText() {
        return todos.stream().map(todo -> todo.getText()).collect(Collectors.toList());
    }

    public void completeTodo(int todoIndex) {
        clickComplete(getTodo(todoIndex));
    }

    private void clickComplete(WebElement todo) {
        todo.findElement(By.cssSelector("input.toggle")).click();
    }

    public Boolean isTodoCompleted(int todoIndex) {

        return getTodo(todoIndex).getAttribute("class").contains("completed");
    }

    public int getNumberOfCompletedTodos() {
        return completedTodos.size();
    }

    public void editTodo(int todoIndex, String editText) {
        WebElement todoToEdit = getTodo(todoIndex);
        doubleClick(todoToEdit);

        // Use waits to make sure certain conditions have happened before continuing.
        // Selenium provides many ExpectedCondition Functions in the ExpectedConditions
        // class, but you can always write more yourself.
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(todoToEdit.findElement(By.cssSelector(".edit"))));
        //
        // Chording keys means typing them together, for instance when you need to
        // simulate ctrl+a
        todoToEdit.findElement(By.cssSelector(".edit"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE + editText + Keys.ENTER);
        //
    }

    public String getTodoCountText() {
        return todoCount.getText();
    }

    public void clickCompletedView() {
        completedView.click();
    }

    // I used an enum(eration) here as input because this prevents typos (compared
    // to using String) and an enumeration is a finite list, which works well with a
    // switch
    public void changeViewTo(Views view) {

        switch (view) {
            case COMPLETED:
                clickCompletedView();
                break;

            case ALL:
                clickAllView();
            default:
                break;
        }

    }

    public void clearAllTodos() {
        for (WebElement todo : todos) {
            removeTodo(todo);
        }
      }

      private void removeTodo(WebElement todoToRemove){
        hoverOver(todoToRemove);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(todoToRemove.findElement(By.cssSelector(".destroy"))))
                .click();
    }

    public void completeTodos(List<Integer> todos) {
        for (int todo : todos) {
            completeTodo(todo-1); 
        }
    }

}
