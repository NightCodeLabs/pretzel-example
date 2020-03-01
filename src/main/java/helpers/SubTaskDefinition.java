package helpers;

import com.github.myzhan.locust4j.Locust;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubTaskDefinition {

    private static final Logger logger = LoggerFactory.getLogger(SubTaskDefinition.class);

    private String baseURI;
    private Method method;
    private String requestParams;
    private String expected;
    private String valuePath;
    private String name;
    private Long contentLength;

    public SubTaskDefinition() { }

    public SubTaskDefinition(String baseURI, Method method, String requestParams, String expected, String valuePath, String name, Long contentLength) {
        this.baseURI = baseURI;
        this.method = method;
        this.requestParams = requestParams;
        this.expected = expected;
        this.valuePath = valuePath;
        this.name = name;
        this.contentLength = contentLength;
    }

    public String getBaseURI() {
        return baseURI;
    }

    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getValuePath() {
        return valuePath;
    }

    public void setValuePath(String valuePath) {
        this.valuePath = valuePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public void doStep() {
        Response response = null;
        try {

            RestAssured.baseURI = baseURI;
            RequestSpecification httpRequest = RestAssured.given();
            response = httpRequest.request(method, requestParams);
            JsonPath answer = response.getBody().jsonPath();
            System.out.println(answer.prettyPrint());
            Assert.assertEquals("Correct answer returned",  expected, answer.getString(valuePath));
            Locust.getInstance().recordSuccess(method.toString(), getName(), response.getTime(), contentLength);
        } catch (AssertionError | Exception error){
            Locust.getInstance().recordFailure(method.toString(), getName(), response.getTime(),expected + " has not been returned");
            logger.info("Something went wrong in the request");
        }
    }

}
