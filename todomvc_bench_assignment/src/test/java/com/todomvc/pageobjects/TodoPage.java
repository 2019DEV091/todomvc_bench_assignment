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

public abstract class TodoPage extends PageObject {


    @FindBy(css = ".new-todo")
    protected WebElement todoInput;
   
    @FindBy(css = ".todo-list li")
 //   @FindBy(css = "li[data-id]")
    protected List<WebElement> todos;

    @FindBy(css = ".todo-list li.completed")
    protected List<WebElement> completedTodos;

    @FindBy(css="a[href='#/completed']")
    protected WebElement completedView;

    @FindBy(css=".todo-count")
    protected WebElement todoCount;
    
    public TodoPage(WebDriver driver) {
        super(driver);
    }

    protected void get(String url){
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

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(todoToEdit.findElement(By.cssSelector(".edit"))));
        todoToEdit.findElement(By.cssSelector(".edit"))
                .sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE + editText + Keys.ENTER);
    }

    public String getTodoCountText() {
        return todoCount.getText();
    }

    
    public void clickCompletedView() {
        completedView.click();
    }



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

}
