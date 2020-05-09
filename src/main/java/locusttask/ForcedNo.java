package locusttask;

import com.github.cucumberlocust4j.pretzel.performance.PerformanceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import serviceobjects.ForcedAnswer;


public class ForcedNo extends PerformanceTask {
	
	private static final Logger logger = LoggerFactory.getLogger(ForcedNo.class);

	private int weight;

    ForcedAnswer forcedAnswer = new ForcedAnswer();


    public ForcedNo(Integer weight){
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "Forced No";
    }


    @Override
    public void execute() {
        try {
            forcedAnswer.aForcedAnswerTypeIsRequested("no");
            performance.recordSuccess("GET", getName(), forcedAnswer.getResponseTime(), 1);
        }catch (AssertionError | Exception error){
            performance.recordFailure("GET",getName(), forcedAnswer.getResponseTime(),"No has not been returned");
            logger.info("Something went wrong in the request");
        }
    
    }

	
	
}
