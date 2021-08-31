package com.todomvc;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "classpath:features", plugin = "json:target/cucumber-report/cucumber.json")
public class VueRunnerIT extends AbstractTestNGCucumberTests{

    @BeforeClass
    public void beforeClass(){
        System.setProperty("todoType", "vue");
    }
    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
    
}
