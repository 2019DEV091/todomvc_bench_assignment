package com.todomvc.glue;

import com.todomvc.pageobjects.PageFactory;
import com.todomvc.pageobjects.TodoPage;
import com.todomvc.pageobjects.VanillaJsTodoPage;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;

public class TodoSteps {

    private WebDriver driver;
    private TodoPage todoPage;

    private String addedTodo;

    @Before
    public void before() {

      String todoType = System.getProperty("todoType","vanillajs");
        driver = new ChromeDriver();
        todoPage = PageFactory.getTodoPage(todoType,driver);
        todoPage.goToUrl();

    }

    @Given("I have no todos yet")
    public void i_have_no_todos_yet() {
        if(todoPage.getNumberOfTodos() >0){
            todoPage.clearAllTodos();
        }
    }

    @When("I add a todo {string}")
    public void i_add_a_todo(String todoText) {

        todoPage.createTodo(todoText);
        this.addedTodo = todoText;
    }

    @Then("I should see my todo added to the list of todos")
    public void i_should_see_my_todo_added_to_the_list_of_todos() {
        assertEquals(todoPage.getNumberOfTodos(), 1);
        assertEquals(todoPage.getTodoText(0), addedTodo);
    }

    @After
    public void after(Scenario scenario) {

        try {
              scenario.attach(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES), "image/png", "failed test");
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }

}
