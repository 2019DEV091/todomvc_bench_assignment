package com.todomvc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;



@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/ctg/cucumber_selenium")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key=GLUE_PROPERTY_NAME, value="com/ctg/cucumber_selenium")

public class RunnerTest{

    @BeforeAll
    public void beforeClass(){
        System.setProperty("todoType", "vanillajs");
    }

    
}
