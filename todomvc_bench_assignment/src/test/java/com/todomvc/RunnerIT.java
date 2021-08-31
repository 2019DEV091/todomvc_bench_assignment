package com.todomvc;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "classpath:features", plugin = "json:target/cucumber-report/cucumber.json")
public class RunnerIT extends AbstractTestNGCucumberTests{

    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
    
}
