package performance;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import helpers.AuxiliarMethods;
import helpers.ConfigReader;
import helpers.FileOperations;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;

import cucumber.api.DataTable;

public class LocustOperations {

	private static final String TASKPACKAGEPATH = "locustTask";
	private static final String NAMEOFREPORT = "performanceResults";
    private String masterFilePath = FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getLocustMasterFilePath());
    private String csvReportFilePath = FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getCsvReportFolderPath());

	private String locustTask;
    private String master = "127.0.0.1";
    private int masterPort = 5557;
    private int maxUsers;
    private int usersLoadPerSecond;
    private long maxRPS;
    private int weight;
    private int testTime;

	Locust locust = Locust.getInstance();	
	/*
	 * This method set the data defined in cucumber in the private variables
	 */
	public void setTestData(DataTable testData){
        this.maxUsers=(Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData,"Max Users Load")));
        this.usersLoadPerSecond=(Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData,"Users Load Per Second")));
        this.testTime=(Integer.parseInt(AuxiliarMethods.getInstance().getDataTableValue(testData,"Test Time")));
        this.maxRPS = (Integer.parseInt((AuxiliarMethods.getInstance().getDataTableValue(testData, "Max RPS"))));
        this.weight=(Integer.parseInt((AuxiliarMethods.getInstance().getDataTableValue(testData,"Max Users Load"))));
    }
	
	/* 
	 * This method setup the slave with the private variables that contains the information of cucumber
	 */
	public void setUpSlave(){
		 locust.setMaxRPS(maxRPS);
		 locust.setMasterHost(master);
	     locust.setMasterPort(masterPort);
	    }
	 
	/*
	 * This method execute the task specified in cucumber
	 */
	public void executeTask(DataTable data) throws Exception {
       this.locustTask=TASKPACKAGEPATH+"." + AuxiliarMethods.getInstance().getDataTableValue(data,"Task");
       Class<?> nameClass = Class.forName(locustTask);
       locust.run((AbstractTask) nameClass.getConstructor(Integer.class).newInstance(this.weight));
   }

	/*
	 * This method raise the master with the parameters of the test defined in cucumber
	 */
    public void executeMaster() {
        Process process;
        String OPERATING_SYSTEM = System.getProperty("os.name").toLowerCase();
        String command="-f "+ masterFilePath +" --master --no-web --csv="+csvReportFilePath +"/"+ NAMEOFREPORT +" --expect-slaves=1 -c "+ maxUsers +" -r "+ usersLoadPerSecond+" -t"+testTime+"m";

        if (OPERATING_SYSTEM.indexOf("win") >= 0) {
        	command = "cmd.exe /c start /MIN locust.exe " + command;
        } else {
        	command= "locust " + command;
        }
        try {
        	process = Runtime.getRuntime().exec(command);
        } catch (IOException error) {
           System.out.println(error.getMessage());
        }
    }
    
    /*
     * This method execute the sequence needed for run the test
     */
    public void executePerformanceTask(DataTable testData) throws Exception {
        setTestData(testData);
        executeMaster();
        TimeUnit.SECONDS.sleep(10);
        setUpSlave();
        executeTask(testData);
        TimeUnit.MINUTES.sleep(this.testTime);
        locust.stop();
    }
    
    public Boolean checkMaxResponseTime(DataTable testData) {
    	Boolean higher = false;
    	List<String[]> data = FileOperations.getInstance().readCSV(FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getRequestReportPath()));    	
    	try {
			if (this.getMaxResponseTime(data, (data.size()-1))>Long.parseLong(AuxiliarMethods.getInstance().getDataTableValue(testData, "Expected Time"))){
				higher = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		};
    	return higher;
    }  
	
    public Long getMaxResponseTime(List<String[]> data, int testResultsIteration) throws Exception {
    	int position = 555;
    	for (int i= 2; i<data.get(0).length; i++) {
    		if((data.get(0)[i]).equals("Max response time")) {
    			position = i;
    		}   		
    	}    	
    	if(position == 555) {
    		throw new Exception ("The Max response time can't be found");
    	} else {
    		return Long.parseLong(data.get(testResultsIteration)[position]);
    	}
    }
    
    
    
	
}
