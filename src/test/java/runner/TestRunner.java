package runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import com.github.nightcodelabs.pretzel.Pretzel;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue={"steps"},
     	tags = {"~@ignore"},
        plugin = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/pretzel/report/report.html"},
        monochrome = true
)

public class TestRunner {

		private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);
		private static Pretzel pretzel = new Pretzel();

	    @BeforeClass
		public static void beforeClass() {
			pretzel.initiateReportDirectory();
		}


	    @AfterClass
	    public static void writeExtentReport() {
	        try {
	            Reporter.loadXMLConfig(new File("src/main/resources/configs/extent-config.xml"));
	        } catch (NoSuchMethodError ex){
	            logger.error("Something went wrong writting in the Extent Report");
	        }

	    }


}
