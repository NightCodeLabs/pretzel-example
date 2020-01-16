package runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue={"stepsdefinition"},
        //tags = {""},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        monochrome = true
)

public class TestRunner {

	    @AfterClass
	    public static void writeExtentReport() {
	        try {
	            Reporter.loadXMLConfig(new File("src/main/resources/configs/extent-config.xml"));

	        } catch (NoSuchMethodError ex){
	            System.out.println(ex.getMessage());
	        }

	    }	
}
