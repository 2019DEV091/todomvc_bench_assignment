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

public class VanillaJsTodoPage extends TodoPage {

    @FindBy(css = ".new-todo")
    private WebElement todoInput;

    @FindBy(css = "li[data-id]")
    private List<WebElement> todos;

    @FindBy(css = "li[data-id].completed")
    private List<WebElement> completedTodos;

    @FindBy(css="a[href='#/completed']")
    private WebElement completedView;

    @FindBy(css=".todo-count")
    private WebElement todoCount;

    @FindBy(css="a[href='#/']")
    private WebElement allView;

    public VanillaJsTodoPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void goToUrl() {
        driver.get("https://todomvc.com/examples/vanillajs/");
    }

    @Override
    public void createTodo(String todo) {
        todoInput.sendKeys(todo + Keys.ENTER);
    }

    @Override
    public String getTodoText(int todoIndex) {
        return getTodo(todoIndex).findElement(By.cssSelector("label")).getText();
    }

    private WebElement getTodo(int todoIndex) {
        return todos.get(todoIndex);

    }

    @Override
    public int getNumberOfTodos() {
        return todos.size();
    }

    @Override
    public void removeTodo(int todoIndex) {

        WebElement todoToRemove = getTodo(todoIndex);
        removeTodo(todoToRemove);
    }

    private void removeTodo(WebElement todoToRemove){
        hoverOver(todoToRemove);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(todoToRemove.findElement(By.cssSelector(".destroy"))))
                .click();
    }

    @Override
    public void createTodos(List<String> todos) {
        for (String todo : todos) {
            createTodo(todo);
        }
    }

    @Override
    public List<String> getAllTodosText() {
        return todos.stream().map(todo -> todo.getText()).collect(Collectors.toList());
    }

    @Override
    public void completeTodo(int todoIndex) {
        clickComplete(getTodo(todoIndex));
    }

    private void clickComplete(WebElement todo) {
        todo.findElement(By.cssSelector("input.toggle")).click();
    }

    @Override
    public Boolean isTodoCompleted(int todoIndex) {

        return getTodo(todoIndex).getAttribute("class").equals("completed");
    }

    @Override
    public int getNumberOfCompletedTodos() {
        return completedTodos.size();
    }

    @Override
    public void editTodo(int todoIndex, String editText) {
        WebElement todoToEdit = getTodo(todoIndex);
        doubleClick(todoToEdit);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(todoToEdit.findElement(By.cssSelector(".edit"))));
        todoToEdit.findElement(By.cssSelector(".edit"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE + editText + Keys.ENTER);
    }

    @Override
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

    private void clickAllView() {
        allView.click();
    }

    private void clickCompletedView() {
        completedView.click();
    }

    @Override
    public String getTodoCountText() {
        return todoCount.getText();
    }

    @Override
    public void clearAllTodos() {
      for (WebElement todo : todos) {
          removeTodo(todo);
      }
    }

}
