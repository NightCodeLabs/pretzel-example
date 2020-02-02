package stepsdefinition;

import cucumber.api.DataTable;
//import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import performance.LocustOperations;

public class Example {

	LocustOperations locustOperations;

	@Given("^Multiple users are requesting for a forced answer$")
	public void multiple_users_are_requesting_for_a_forced_answer(DataTable arg1) throws Throwable {
		locustOperations = new LocustOperations();
		locustOperations.executePerformanceTask(arg1);
	}

	@Then("^The answer is returned within the expected time$")
	public void the_answer_is_returned_within_the_expected_time(DataTable testData) throws Throwable {
		Assert.assertFalse(locustOperations.checkMinResponseTime(testData));
	}





}
