Feature: Adding todos

    In order to keep track of the things I need to do, as a busy person
    I want to be able to add my todos

    #A background has all the steps which need to be executed before each scenario in this feature file (all common steps)
    #There can onle be one Background per feature file (unless you use the Rule keyword)
    #see: https://cucumber.io/docs/gherkin/reference/ for more information about the Gherkin syntax
    Background:
        Given I have no todos yet


    Scenario: Creating a todo
        When I add a todo "Automation is fun!"
        Then I should see my todo added to the list of todos

    Scenario: Removing a todo
        Given I have created the following todos
            | Todo1  |
            | Todo 2 |
            | Todo 3 |
        When I remove the 2nd todo
        Then there will only be 2 todos left
        And the removed todo will not be present anymore

    Scenario: Completing a todo
        Given I have created the following todos
            | Todo1  |
            | Todo 2 |
            | Todo 3 |
        When I complete the 3rd todo
        Then that todo will show as completed
        And the number of completed todos will be 1


    Scenario: Editing a todo
        Given I have created the following todos
            | Todo1  |
            | Todo 2 |
            | Todo 3 |
        When I edit the 2nd todo to "I used to be something else"
        Then the 2nd todo will be "I used to be something else"

    #The following scenarios form the 5th test
    #The reason for splitting this up is because the tezst in fact tests 2 functionalities:
    #what is shown in the completed todos view when there are no completed todos and what is shown when there are some.
    Scenario: Viewing empty completed todos
        Given I have created the following todos
            | Todo1  |
            | Todo 2 |
            | Todo 3 |
        When I change the view to the completed todos
        Then there will be 0 todos shown
        And there will be a message saying "3 items left"

    Scenario: Viewing completed todos
        Given I have created the following todos
            | Todo1  |
            | Todo 2 |
            | Todo 3 |
        And I have completed the following todos
            | 1 |
            | 3 |
        When I change the view to the completed todos
        Then only the completed todos will be shown
        And there will be a message saying "1 item left"


