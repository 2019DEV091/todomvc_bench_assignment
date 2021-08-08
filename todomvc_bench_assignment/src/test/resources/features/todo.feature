Feature: Adding todos

    In order to keep track of the things I need to do, as a busy person
    I want to be able to add my todos

    Scenario: Adding a todo
        Given I have no todos yet
        When I add a todo "Automation is fun!"
        Then I should see my todo added to the list of todos