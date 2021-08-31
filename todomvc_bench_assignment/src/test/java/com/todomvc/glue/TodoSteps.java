package com.todomvc.glue;

import com.todomvc.Views;
import com.todomvc.pageobjects.TodoPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.todomvc.pageobjects.PageFactory.getTodoPage;
import static org.testng.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

public class TodoSteps {

    private WebDriver driver;
    private TodoPage todoPage;

    private String addedTodo;
    private String removedTodo;
    private int completedTodoIndex;
    private List<String> completedTodos;

    @Before
    public void before() {
        driver = new ChromeDriver();
        todoPage = getTodoPage(driver);
        todoPage.goToUrl();

    }

    /**
     * A parameterType allows you to extend the built in cucumber expression
     * parameterTypes. For example, here I have created a parameterType that matches
     * abbreviated numbers and converts it to an integer. How does it work? In the
     * value parameter I define a regular expression to define the text that must
     * match in the Gherkin Step. In this case the regex means: At least one digit
     * followed by one of the following: st,nd,rd,th. Once we have the matched part
     * I'm only interested in the digits, so I capture those in a capture group and
     * pass it as parameter in my method. Now I have a parameterType that I can use
     * in any SteDefinition (see below for some examples) in the glue package (so
     * not only this class)
     * 
     * @param abbreviation
     * @return
     */
    @ParameterType(value = "(\\d+)(?:st|nd|rd|th)")
    public Integer numberAbbreviation(String abbreviation) {
        return Integer.parseInt(abbreviation);
    }

    @Given("I have no todos yet")
    public void i_have_no_todos_yet() {
        if (todoPage.getNumberOfTodos() > 0) {
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

    @Given("I have created the following todos")
    public void i_have_created_the_following_todos(List<String> todos) {
        todoPage.createTodos(todos);
    }

    @When("I remove the {numberAbbreviation} todo")
    public void i_remove_the_nth_todo(int todo) {
        removedTodo = todoPage.getTodoText(todo - 1);
        todoPage.removeTodo(todo - 1);
    }

    @Then("there will only be {int} todos left")
    public void there_will_only_be_todos_left(int numberOfTodos) {
        assertEquals(todoPage.getNumberOfTodos(), numberOfTodos);
    }

    @Then("the removed todo will not be present anymore")
    public void the_removed_todo_will_not_be_present_anymore() {
        assertThat(todoPage.getAllTodosText()).as("List of todos should not have value %s", removedTodo)
                .doesNotContain(removedTodo);
    }

    @When("I complete the {numberAbbreviation} todo")
    public void i_complete_the_nth_todo(int todo) {
        completedTodoIndex = todo;
        todoPage.completeTodo(todo - 1);
    }

    @Then("that todo will show as completed")
    public void that_todo_will_show_as_completed() {
        assertThat(todoPage.isTodoCompleted(completedTodoIndex - 1)).as("Confirm that todo is in fact completed")
                .isTrue();
    }

    @Then("the number of completed todos will be {int}")
    public void the_number_of_completed_todos_will_be(int completedTodos) {
        assertThat(todoPage.getNumberOfCompletedTodos()).isEqualTo(completedTodos);
    }

    @When("I edit the {numberAbbreviation} todo to {string}")
    public void i_edit_the_nth_todo_to(int todo, String editText) {
        todoPage.editTodo(todo - 1, editText);
    }

    @Then("the {numberAbbreviation} todo will be {string}")
    public void the_nth_todo_will_be(int todo, String editText) {
        assertEquals(todoPage.getTodoText(todo - 1), editText);
    }

    @When("I change the view to the completed todos")
    public void i_change_the_view_to_the_completed_todos() {
        todoPage.changeViewTo(Views.COMPLETED);
    }

    @Then("there will be {int} todos shown")
    public void there_will_be_todos_shown(int shownTodos) {
        assertEquals(todoPage.getNumberOfTodos(), shownTodos);
    }

    @Then("there will be a message saying {string}")
    public void there_will_be_a_message_saying(String message) {
        assertThat(todoPage.getTodoCountText()).isEqualTo(message);
    }

    @Given("I have completed the following todos")
    public void i_have_completed_the_following_todos(List<Integer> todos) {
        completedTodos = new ArrayList<>();
        for (Integer todo : todos) {
            completedTodos.add(todoPage.getTodoText(todo - 1));
        }
        todoPage.completeTodos(todos);
    }

    @Then("only the completed todos will be shown")
    public void only_the_completed_todos_will_be_shown() {
        assertThat(todoPage.getAllTodosText()).containsExactlyInAnyOrderElementsOf(completedTodos);
    }

    // The Scenario object is optional. It provides metadata about the scenario this
    // method applies to.
    @After
    public void after(Scenario scenario) {

        // This is optional. Usually a screenshot is taken when there is a failed
        // scenario. To know if a scenario has failed -> scenario.isFailed();
        try {
            scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png", "failed test");
        }
        // used try finally because we want to make sure our driver will be cleaned up.
        // If we don't use it and there is an exception, we run the risk of having an
        // open browser/driver.
        finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }

}
