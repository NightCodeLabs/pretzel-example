package stepsdefinition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.cucumber.listener.Reporter;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import graph.LocustBarChart;

public class Hooks {
	
	 @Before
	 public void BeforeSteps() {
	        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
	        Reporter.setSystemInfo("Machine", System.getProperty("os.name"));
	    }
	 
	 @After(order = 0)
	 public void AfterSteps() throws IOException {
		 LocustBarChart locustBarChart = new LocustBarChart();
	     locustBarChart.createChart(0);
		 File destinationPath = new File(Paths.get("target/cucumber-reports/locustcharts/" + locustBarChart.getReportName()).toFile().getAbsolutePath());
         Reporter.addScreenCaptureFromPath(destinationPath.toString(), "Performance Results");
	    
	 }

}
