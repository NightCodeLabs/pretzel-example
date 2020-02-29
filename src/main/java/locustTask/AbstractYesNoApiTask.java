package locustTask;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractYesNoApiTask extends AbstractTask {

    private static final Logger logger = LoggerFactory.getLogger(YesNoApi.class);

    protected static final String baseURI = "https://yesno.wtf/api";

    protected void commonTask(String URLGetParam, String expected) {
        Response response = null;
        try {

            RestAssured.baseURI = baseURI;
            RequestSpecification httpRequest = RestAssured.given();
            response = httpRequest.request(Method.GET, URLGetParam);
            JsonPath answer = response.getBody().jsonPath();
            System.out.println(answer.prettyPrint());
            Assert.assertEquals("Correct answer returned",  expected, answer.getString("answer"));
            Locust.getInstance().recordSuccess("GET", getName(), response.getTime(), 1);
        } catch (AssertionError | Exception error){
            Locust.getInstance().recordFailure("GET",getName(), response.getTime(),expected + " has not been returned");
            logger.info("Something went wrong in the request");
        }
    }
}
