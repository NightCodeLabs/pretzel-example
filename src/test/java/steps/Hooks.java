package steps;

import java.io.IOException;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import com.github.nightcodelabs.pretzel.Pretzel;

public class Hooks {

	Pretzel pretzel = new Pretzel();
	
	 @Before
	 public void BeforeSteps() throws IOException {
		 	//Setup the Report basic information
	        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
	        Reporter.setSystemInfo("Machine", System.getProperty("os.name"));
	 }

	@After(order = 0)
	public void AfterSteps(Scenario scenario) throws IOException {
			if (scenario.getSourceTagNames().contains("@Performance")) {
			Reporter.addScreenCaptureFromPath(pretzel.getGeneratedChartFilePath(),"Performance Results");
		}

	}

}
