# TodoMVC bench assignment

## How to run the tests

`mvn clean verify`

This will start all tests 3 times: once for each todoMVC app implementation

### How does it work?

There are 3 Runner classes defined (one for each implementation). In these runners you can configure Cucumber the way you want.<br>
The difference in these runners is simple: they all set the todoType property to their respective implementation. This is just so I can show you all implementations running at the same time. If, for example you have a project where you need to run your scenarios on different browsers, it is better to have on configurable set of tests that you run in separate jobs, consolidating your results afterwards.
Based on the 'todoType' parameter the [Pagefactory](src/test/java/com/todomvc/pageobjects/PageFactory.java) will create the correct TodoPageObject.\
If you want to add more implementations you just create them and add a line in the switch.