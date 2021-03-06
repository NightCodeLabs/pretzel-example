package steps;


import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import com.github.nightcodelabs.pretzel.Pretzel;
import serviceobjects.ForcedAnswer;
import utils.AuxiliarMethods;

public class Definitions {

	Pretzel pretzel = new Pretzel();
	ForcedAnswer forcedAnswer = new ForcedAnswer();

	@Given("^Multiple users are requesting for a forced answer$")
	public void multiple_users_are_requesting_for_a_forced_answer(DataTable testData) throws Throwable {
		Integer maxUsers = Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData,"Max Users Load"));
		Integer usersLoadPerSecond = Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData,"Users Load Per Second"));
		Integer testTime = Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData,"Test Time"));
		Integer maxRPS = Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData, "Max RPS"));
		Integer weight = Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData,"Weight"));
		String nameTask = AuxiliarMethods.getInstance().getDataTableValue(testData,"Task");
		pretzel.doPretzel(maxUsers,usersLoadPerSecond, testTime, maxRPS, weight, nameTask);
	}

	@Then("^The answer is returned within the expected time$")
	public void the_answer_is_returned_within_the_expected_time(DataTable testData) throws Throwable {
		Long expectedTime = Long.parseLong(AuxiliarMethods.getInstance().getDataTableValue(testData, "Expected Time"));
		Assert.assertFalse(pretzel.checkMaxResponseTimeAboveExpected(expectedTime));
	}


	@When("^a forced (.+) is requested$")
	public void aForcedAnswerTypeIsRequested(String answerType) {
		forcedAnswer.aForcedAnswerTypeIsRequested(answerType);
	}

	@Then("^the corresponding (.+) is returned$")
	public void theCorrespondingAnswerTypeIsReturned(String answerType) {
		forcedAnswer.theCorrespondingAnswerTypeIsReturned(answerType);
	}

	@When("^(.+) users request a forced yes at (.+) users/second for (.+) min$")
	public void usersRequestForcedYesAnswerAtRateMinute(Integer maxUsers, Integer usersLoadPerSecond, Integer testTime) throws Throwable {
		pretzel.doPretzel(maxUsers,usersLoadPerSecond, testTime, maxUsers, maxUsers, "ForcedYes");
	}

	@Then("^the answer is returned within (.+) milliseconds$")
	public void theAnswerIsReturnedWithingMilliseconds(Long expectedTime) {
		Assert.assertFalse(pretzel.checkMaxResponseTimeAboveExpected(expectedTime));
	}

}
