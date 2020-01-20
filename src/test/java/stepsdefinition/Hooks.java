package stepsdefinition;

import com.cucumber.listener.Reporter;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	 @Before
	 public void BeforeSteps() {
	        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
	        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
	        Reporter.setSystemInfo("Machine", System.getProperty("os.name"));
	    }
	 
	 @After(order = 0)
	 public void AfterSteps() {
	    
	 }

}
