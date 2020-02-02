package runner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
	
		@BeforeClass
		public static void cleanLocustChartsDirectory() throws IOException {
			//Delete and create the locustcharts folder in order to ensure that exists in every execution
			FileUtils.deleteDirectory(new File("target/cucumber-reports/locustcharts"));			
			FileUtils.forceMkdir(new File("target/cucumber-reports/locustcharts"));			
		}

	    @AfterClass
	    public static void writeExtentReport() {
	        try {
	            Reporter.loadXMLConfig(new File("src/main/resources/configs/extent-config.xml"));

	        } catch (NoSuchMethodError ex){
	            System.out.println(ex.getMessage());
	        }

	    }	
}
