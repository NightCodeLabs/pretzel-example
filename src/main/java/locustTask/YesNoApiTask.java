package locustTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;
import helpers.SubTaskDefinition;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class YesNoApiTask extends AbstractTask {

    private static final Logger logger = LoggerFactory.getLogger(YesNoApiTask.class);

    private int weight;
    private String name;
    private List<SubTaskDefinition> subTaskDefinitionList;

    public YesNoApiTask(Integer weight) {
        this.weight = weight;
        this.name = "Multiple subTasks";
        subTaskDefinitionList = loadSubTasksFromFile();
        if (subTaskDefinitionList == null) {
            System.out.println("LISTA VACÍA");
        }
    }

    private List<SubTaskDefinition> loadSubTasksFromFile() {
        List<SubTaskDefinition> subTaskDefinitionList = null;
        ObjectMapper obj = new ObjectMapper();
        try {
            subTaskDefinitionList = Arrays.asList(obj.readValue(getClass().getClassLoader().getResourceAsStream("subTasks.json"), SubTaskDefinition[].class));
        } catch (IOException e) {
            logger.error("Something went wrong loading the subtasks");
            logger.error(e.getMessage());
            String stackTrace = "";
            for (int i = 0; i < e.getStackTrace().length; i++)
                stackTrace += e.getStackTrace()[i].toString() + "\n";
            logger.error(stackTrace);
        }
        return subTaskDefinitionList;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public List<SubTaskDefinition> getSubTaskDefinitionList() {
        return subTaskDefinitionList;
    }

    public void setSubTaskDefinitionList(List<SubTaskDefinition> subTaskDefinitionList) {
        this.subTaskDefinitionList = subTaskDefinitionList;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() throws Exception {
        for (SubTaskDefinition subTaskDef : subTaskDefinitionList) {
            this.name = subTaskDef.getName();
            Response response = null;
            try {
                RestAssured.baseURI = subTaskDef.getBaseURI();
                RequestSpecification httpRequest = RestAssured.given();
                response = httpRequest.request(subTaskDef.getMethod(), subTaskDef.getRequestParams());
                JsonPath answer = response.getBody().jsonPath();
                System.out.println(answer.prettyPrint());
                Assert.assertEquals("Correct answer returned",  subTaskDef.getExpected(), answer.getString(subTaskDef.getValuePath()));
                Locust.getInstance().recordSuccess(subTaskDef.getMethod().toString(), getName(), response.getTime(), subTaskDef.getContentLength());
            } catch (AssertionError | Exception error){
                Locust.getInstance().recordFailure(subTaskDef.getMethod().toString(),getName(), response.getTime(),subTaskDef.getExpected() + " has not been returned");
                logger.error("Something went wrong in the request");
            }
        }
    }
}
