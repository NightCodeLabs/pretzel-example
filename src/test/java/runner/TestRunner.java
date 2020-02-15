package runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import helpers.ConfigReader;
import helpers.FileOperations;

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
		public static void cleanLocustChartsDirectory() {
			//Delete and create the locustcharts folder in order to ensure that exists in every execution
			FileOperations.getInstance().initialiseChartsFolder(FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getChartPath()));
		}

	    @AfterClass
	    public static void writeExtentReport() {
	        try {
	            Reporter.loadXMLConfig(new File(FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getExtentReportConfigPath())));

	        } catch (NoSuchMethodError ex){
	            System.out.println(ex.getMessage());
	        }

	    }	
}
