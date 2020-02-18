package locustTask;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.junit.Assert;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class YesNoApi2 extends AbstractTask  {
	
	private static final Logger logger = LoggerFactory.getLogger(YesNoApi2.class);

	private int weight;

    public YesNoApi2(Integer weight){
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "YesNo2 Api GET";
    }


    @Override
    public void execute() {
    		Response response = null;
        try {
           
        	RestAssured.baseURI = "https://yesno.wtf/api";	 
   		 	RequestSpecification httpRequest = RestAssured.given();
   		 	response = httpRequest.request(Method.GET, "/?force=no");   	 
   		 	JsonPath answer = response.getBody().jsonPath();   
   		 	System.out.println(answer.prettyPrint());
   		 	Assert.assertEquals("Correct answer returned",  "no", answer.getString("answer"));        	
            Locust.getInstance().recordSuccess("GET", getName(), response.getTime(), 1);
        }catch (AssertionError | Exception error){
            Locust.getInstance().recordFailure("GET",getName(), response.getTime(),"No has not been returned");
            logger.info("Something went wrong in the request");
        }
    
    }

	
	
}
