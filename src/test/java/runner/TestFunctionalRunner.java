package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue={"stepsDefinitions"},
        tags = {"@Functional"},
        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        monochrome = true
)

public class TestFunctionalRunner {

}
