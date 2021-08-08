package com.todomvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

import java.util.List;

import com.todomvc.pageobjects.TodoPage;
import com.todomvc.pageobjects.VanillaJsTodoPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * All tests related to the todomvc app.
 * 
 */
public class TodoTest {

    private WebDriver driver;
    private TodoPage todoPage;

    @BeforeMethod
    public void before() {

        driver = new ChromeDriver();
        todoPage = new VanillaJsTodoPage(driver);
        todoPage.goToUrl();
    }

    @DataProvider
    public Object[][] createTodoProvider() {
        return new Object[][] { { "Automation is fun!"}};
    }

    @DataProvider
    public Object[][] removeTodoProvider() {

        List<String> todos = List.of("Todo 1", "Todo 2", "Todo 3");

        return new Object[][] { { todos, "Todo 2" } };
    }

    @DataProvider
    public Object[][] completeTodoProvider() {

        List<String> todos = List.of("Todo 1", "Todo 2", "Todo 3");

        return new Object[][] { { todos } };
    }

    @DataProvider
    public Object[][] editTodoProvider() {

        List<String> todos = List.of("Todo 1", "Todo 2", "Todo 3");

        return new Object[][] { { todos, "I used to be something else" } };
    }

    @DataProvider
    public Object[][] viewCompletedTodosProvider() {

        List<String> todos = List.of("Todo 1", "Todo 2", "Todo 3");

        return new Object[][] { { todos, "Todo 2", "Todo 3" } };
    }

    @Test(dataProvider = "createTodoProvider")
    public void createTodo(String todo) {

        todoPage.createTodo(todo);
        assertEquals(todoPage.getNumberOfTodos(), 1);
        assertEquals(todoPage.getTodoText(0), todo);
    }

    @Test(dataProvider = "removeTodoProvider")
    public void removeTodo(List<String> todos, String removedTodo) throws InterruptedException {

        todoPage.createTodos(todos);
        assertThat(todoPage.getAllTodosText()).as("List of todos should have value %s", removedTodo)
                .contains(removedTodo);
        todoPage.removeTodo(1);
        assertEquals(todoPage.getNumberOfTodos(), 2);
        assertThat(todoPage.getAllTodosText()).as("List of todos should not have value %s", removedTodo)
                .doesNotContain(removedTodo);

    }

    @Test(dataProvider = "completeTodoProvider")
    public void completeTodo(List<String> todos) {
        todoPage.createTodos(todos);
        todoPage.completeTodo(2);

        assertThat(todoPage.isTodoCompleted(2)).as("Confirm that todo is in fact completed").isTrue();
        assertThat(todoPage.getNumberOfCompletedTodos()).isEqualTo(1);
    }

    @Test(dataProvider = "editTodoProvider")
    public void editTodo(List<String> todos, String editText) {
        todoPage.createTodos(todos);

        todoPage.editTodo(1, editText);

        assertEquals(todoPage.getTodoText(1), editText);
    }

    @Test(dataProvider = "viewCompletedTodosProvider")
    public void viewCompletedTodos(List<String> todos, String completedTodo1, String completedTodo2) {
        todoPage.createTodos(todos);

        todoPage.changeViewTo(Views.COMPLETED);
        assertEquals(todoPage.getNumberOfTodos(), 0);
        assertThat(todoPage.getTodoCountText()).isEqualTo("3 items left");
        todoPage.changeViewTo(Views.ALL);
        todoPage.completeTodo(1);
        todoPage.completeTodo(2);
        todoPage.changeViewTo(Views.COMPLETED);
        assertThat(todoPage.getTodoCountText()).isEqualTo("1 item left");
        assertEquals(todoPage.getNumberOfTodos(), 2);
        assertThat(todoPage.getAllTodosText()).contains(completedTodo1, completedTodo2);
    }

    @AfterMethod
    public void after() {

        if (driver != null) {
            driver.quit();
        }
    }
}
