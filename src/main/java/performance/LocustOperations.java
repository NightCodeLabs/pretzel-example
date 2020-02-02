package performance;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import helpers.AuxiliarMethods;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;

import cucumber.api.DataTable;

public class LocustOperations {

	private static final String TASKPACKAGEPATH = "locustTask";
	private static final String NAMEOFREPORT = "performanceResults";
	private static final String MASTERFILELOCATION = "src/main/resources/performance/locust-master.py";
    private static final String CSVLOCATION = "target/csvlocustsresults/";
    private String masterFilePath = Paths.get(MASTERFILELOCATION).toFile().getAbsolutePath();
    private String csvReportFilePath = Paths.get(CSVLOCATION).toFile().getAbsolutePath();

	private String locustTask;
    private String master = "127.0.0.1";
    private int masterPort = 5557;
    private int maxUsers;
    private int usersLoadPerSecond;
    private long maxRPS;
    private int weight;
    private int testTime;

	Locust locust = Locust.getInstance();
	AuxiliarMethods auxiliar = new AuxiliarMethods();
	
	/*
	 * This method set the data defined in cucumber in the private variables
	 */
	public void setTestData(DataTable testData){
        this.maxUsers=(Integer.parseInt(auxiliar.getDataTableValue(testData,"Max Users Load")));
        this.usersLoadPerSecond=(Integer.parseInt(auxiliar.getDataTableValue(testData,"Users Load Per Second")));
        this.testTime=(Integer.parseInt(auxiliar.getDataTableValue(testData,"Test Time")));
        this.maxRPS = (Integer.parseInt((auxiliar.getDataTableValue(testData, "Max RPS"))));
        this.weight=(Integer.parseInt((auxiliar.getDataTableValue(testData,"Max Users Load"))));
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
       this.locustTask=TASKPACKAGEPATH+"." + auxiliar.getDataTableValue(data,"Task");
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
    
    public Boolean checkMinResponseTime(DataTable testData) {
    	LocustReportReader locustReportReader = new LocustReportReader();
    	Boolean higher = false;
    	if (Long.parseLong(locustReportReader.getLocustRequestDataList().get(0).getMaxResponseTime())>Long.parseLong(auxiliar.getDataTableValue(testData, "Expected Time"))){
    		higher = true;
    	};
    	return higher;
    }  
	
    
    
	
}
