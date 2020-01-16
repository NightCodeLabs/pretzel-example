package locustTask;

import org.junit.Assert;

//import org.junit.Assert;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class YesNoApi extends AbstractTask  {

	private int weight;

    public YesNoApi(Integer weight){
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "YesNo Api GET";
    }


    @Override
    public void execute() {
    		Response response = null;
        try {
           
        	RestAssured.baseURI = "https://yesno.wtf/api";	 
   		 	RequestSpecification httpRequest = RestAssured.given();
   		 	response = httpRequest.request(Method.GET, "/?force=yes");   	 
   		 	JsonPath answer = response.getBody().jsonPath();   
   		 	System.out.println(answer.prettyPrint());
   		 	Assert.assertEquals("Correct answer returned",  "yes", answer.getString("answer"));        	
            Locust.getInstance().recordSuccess("GET", getName(), response.getTime(), 1);
        }catch (AssertionError | Exception error){
            error.getMessage();
            Locust.getInstance().recordFailure("GET",getName(), response.getTime(),"Yes has not been returned");
        }
    
    }

	
	
}
