package performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import helpers.FileOperations;
import model.LocustDistributionData;
import model.LocustRequestData;


public class LocustReportReader {

    private static final String FILEPATHREQUEST= "target/csvlocustsresults/performanceResults_requests.csv";
    private static final String FILEPATHDISTRIBUTION= "target/csvlocustsresults/performanceResults_distribution.csv";
    private static final String ERROR = "ERROR, the file can not be found in the specified path";
    
    private String absoluteFilePathRequest = Paths.get(FILEPATHREQUEST).toFile().getAbsolutePath();
	private String absoluteFilePathDistribution = Paths.get(FILEPATHDISTRIBUTION).toFile().getAbsolutePath();  
    private List<LocustRequestData> locustRequestDataList;
    private List<LocustDistributionData> locustDistributionDataList;
    
    private FileOperations fileOperations = new FileOperations();
    private Gson gson = new Gson();

    public LocustReportReader() {
        this.locustRequestDataList = getLocustRequestData();
        this.locustDistributionDataList = getLocustDistributionData();
    }

    private List<LocustRequestData> getLocustRequestData() {
        try {
            LocustRequestData[] locustData = gson.fromJson(fileOperations.CSVToJson(absoluteFilePathRequest).toJSONString(), LocustRequestData[].class);
            return Arrays.asList(locustData);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(ERROR);
        }
    }

    private List<LocustDistributionData> getLocustDistributionData() {
        try  {
            LocustDistributionData[] locustData = gson.fromJson(fileOperations.CSVToJson(absoluteFilePathDistribution).toJSONString(), LocustDistributionData[].class);
            return Arrays.asList(locustData);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(ERROR);
        }
    }

    public List<LocustRequestData> getLocustRequestDataList() {
        return this.locustRequestDataList;
    }

    public List<LocustDistributionData> getLocustDistributionDataList() {
        return this.locustDistributionDataList;
    }
	

}
