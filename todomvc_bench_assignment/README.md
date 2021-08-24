# TodoMVC bench assignment

## How to run the tests

`mvn clean test`

This will start all tests 3 times: once for each todoMVC app implementation

### How does it work?

In the [pom.xml](pom.xml) file there is a plugin called 'maven-surefire-plugin'. This plugin is linked to the Maven test phase.\
In this plugin we have linked an xml file called [testng.xml](src/test/resources/testng.xml). In this file we can define which tests run, set parameters and other stuff.\
This xml has 3 tests defined, which are linked to the [TodoTest](src/test/java/com/todomvc/TodoTest.java) class. In that class there are 5 tests.\
Based on the 'todoType' parameter the [Pagefactory](src/test/java/com/todomvc/pageobjects/PageFactory.java) will create the correct TodoPageObject.\
If you want to add more implementations you just create them and add a line in the switch.
