package performance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import helpers.AuxiliarMethods;
import helpers.ConfigReader;
import helpers.FileOperations;
import ssh.SSHConnector;
import ssh.SUserInfo;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

import cucumber.api.DataTable;

public class LocustOperations {
	
	private static final String TASKPACKAGEPATH = "locusttask";
	private static final String NAMEOFREPORT = "performanceResults";
	private static final Logger logger = LoggerFactory.getLogger(LocustOperations.class);
	private static String masterFilePath = ConfigReader.getInstance().getLocustMasterFilePath();
	private static String csvReportFilePath = ConfigReader.getInstance().getCsvReportFolderPath();
    private static String operatingSystem = System.getProperty("os.name").toLowerCase();

	private String locustTask;
    private String master = ConfigReader.getInstance().getIpLocustMaster();
    private int masterPort = 5557;
    private int maxUsers;
    private int usersLoadPerSecond;
    private long maxRPS;
    private int weight;
    private int testTime;

    private Process locustMasterProcess;
	private Locust locust = Locust.getInstance();	
	private ChannelExec channelExec;
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
        String command="-f "+ masterFilePath +" --master --no-web --csv="+ csvReportFilePath + NAMEOFREPORT +" --expect-slaves=1 -c "+ maxUsers +" -r "+ usersLoadPerSecond+" -t"+testTime+"m";
        if (operatingSystem.indexOf("win") >= 0) {
        	command = "cmd.exe /c start /MIN locust.exe " + command;
        } else {
        	command= "locust " + command;
        }
        try {
        	locustMasterProcess = Runtime.getRuntime().exec(command);
        } catch (IOException error) {
        	logger.error("Something went wrong executing the master");
        }
    }
	
	public void executeRemoteMaster() throws JSchException {
		JSch jsch = new JSch();
		Session session = jsch.getSession("david", "192.168.1.93", 22);
		UserInfo ui = new SUserInfo("david", null);
        session.setUserInfo(ui);		
		session.setPassword("david");
		//SSHConnector sshConnector = new SSHConnector();
         
        String command="-f "+ masterFilePath +" --master --no-web --csv="+ csvReportFilePath + NAMEOFREPORT +" --expect-slaves=1 -c "+ maxUsers +" -r "+ usersLoadPerSecond+" -t"+testTime+"m";
        if (ConfigReader.getInstance().getRemoteLocustMasterOS()=="windows") {
        	command = "winrs -r:"+this.master+" cmd.exe /c start /MIN locust.exe " + command;
        } else {
        	if (operatingSystem.indexOf("win") >= 0) {
            String command2="-f /home/david/locust-master.py --master --no-web --csv=/home/david/performance/"+ NAMEOFREPORT +" --expect-slaves=1 -c "+ maxUsers +" -r "+ usersLoadPerSecond+" -t"+testTime+"m";
        	//The user should be added to get access to the remote system in order to avoid to write the password
        	//command= "ssh david@"+this.master+" /home/david/.local/bin/locust " + command2;
        	command= "/home/david/.local/bin/locust " + command2;
        	System.out.println(command);
        	}else {
        		
        	}
        }
        try {
        	session.connect();
        	channelExec = (ChannelExec)session.openChannel("exec");
        	channelExec.setCommand(command);        	
        	channelExec.connect();
        	
        	//sshConnector.connect("david", "david", this.master, 22);
        	//sshConnector.executeCommand(command);
        	//channelExec.disconnect();
            //session.disconnect();
        	//sshConnector.disconnect();
        	//System.out.println(result);
        	//
        	//locustMasterProcess = Runtime.getRuntime().exec(command);
        } catch (JSchException error) {
        	logger.error("Something went wrong executing the master");
        }
    }
	
	public void importCsvFileFromServer() throws JSchException, SftpException{
		JSch jsch = new JSch();
		Session session = jsch.getSession("david", "192.168.1.93", 22);
        session.setUserInfo(new SUserInfo("david", null));
		
		session.setPassword("david");
        String command="";
        if (ConfigReader.getInstance().getRemoteLocustMasterOS()=="windows") {
        	//command = "winrs -r:"+this.master+" cmd.exe /c start /MIN locust.exe " + command;
        } else {
        	if (operatingSystem.indexOf("win") >= 0) {
            String command2="scp /home/david/performance/performanceResults_stats.csv "+ this.csvReportFilePath;
        	//The user should be added to get access to the remote system in order to avoid to write the password
        	//command= "ssh david@"+this.master+" /home/david/.local/bin/locust " + command2;
        	command= "/performance/performanceResults_stats.csv";
        	System.out.println(command);
        	}else {
        		
        	}
        }
        try {
        	System.out.println("1 "+FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getStatsReportPath()));
        	//session.connect();
        	System.out.println("2 "+FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getStatsReportPath()));
        	//ChannelExec exec = (ChannelExec)session.openChannel("exec");        	
        	//ChannelScp scp = (ChannelScp) session.openChannel("scp");
        	session.connect();
        	Channel channel = session.openChannel("sftp");
        	channel.connect();
        	ChannelSftp sftp = (ChannelSftp) channel;
        	//sftp.cd("/home/david/performance/");
        	sftp.get("/home/david/performance/performanceResults_stats.csv", ConfigReader.getInstance().getStatsReportPath());//FileOperations.getInstance().getAbsolutePath(ConfigReader.getInstance().getStatsReportPath()));
        	//exec.connect();
        	sftp.exit();
        	sftp.disconnect();
        	//session.disconnect();
        } catch (JSchException error) {
        	logger.error("Something went wrong importing report");
        }
		
		
		/*
        Process procesCopyRequests;
        Process procesCopyDistribution;
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        String copyRequestReport="scp qareports@172.30.25.57:/home/qareports/performance_requests.csv "+locustCsvReportFileLocalPath;
        String copyDistributionReport="scp qareports@172.30.25.57:/home/qareports/performance_distribution.csv "+locustCsvReportFileLocalPath;
        if (!isWindows){
            try {
                procesCopyRequests = Runtime.getRuntime().exec(copyRequestReport);
                procesCopyDistribution = Runtime.getRuntime().exec(copyDistributionReport);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
	
	private boolean closeRemoteMaster() {		 
		InputStream in;
		BufferedReader reader;
		try {
			in = this.channelExec.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
		    String linea = null;
		    int index = 0;
		    while ((linea = reader.readLine()) != null) {
		       System.out.println(++index + " : " + linea);
		    }		 
		    channelExec.disconnect();
		    return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	    //session.disconnect();
	}
    
    //This method clear all the values;
	private void clearValues() {
		this.locustTask = "";
		this.maxUsers = 0;
		this.usersLoadPerSecond = 0;
		this.maxRPS = 0;
		this.weight = 0;
		this.testTime=0;
	}
    
    /*
     * This method execute the sequence needed for run the test
     */
    public void executePerformanceTask(DataTable testData) throws Exception {
		this.setTestData(testData);
		this.executeRemoteMaster();
		/*if (operatingSystem.indexOf("win") >= 0) {
			while (!checkWindowsLocustService()) {
				logger.info("Waiting to locust master service to start");
			}
		}*/
		this.setUpSlave();
		System.out.println("goes");
		this.executeTask(testData);
		TimeUnit.MINUTES.sleep(this.testTime);
		this.locust.stop();
		this.clearValues();
		this.importCsvFileFromServer();
		this.closeRemoteMaster();
		/*if (operatingSystem.indexOf("win") >= 0) {			
			while (checkWindowsLocustService()) {
				logger.info("Waiting to locust master service to be stopped");
			}
		} else {
			locustMasterProcess.onExit().get();
		}*/
	}
    
    public Boolean checkWindowsLocustService() {
         try {               	 
        	 Process process = Runtime.getRuntime().exec("tasklist");
        	 Scanner reader = new Scanner(process.getInputStream(), "UTF-8");
        	 while(reader.hasNextLine()) {
                 if(reader.nextLine().contains("locust")) {
                	 reader.close();
                     return true;
                 }
         	}    
        	 reader.close(); 
         } catch (IOException error) {
         	logger.error("Something went wrong checking locust service in windows system");
         }
    	return false;
    }
    
    //It returns true or false if the Max response time is higher or not than the expected 
    public Boolean checkMaxResponseTime(DataTable testData) {
    	Boolean higher = false;
    	List<String[]> data = FileOperations.getInstance().readCSV(ConfigReader.getInstance().getStatsReportPath());    	
    	try {
			if (this.getMaxResponseTime(data, (data.size()-1))>Long.parseLong(AuxiliarMethods.getInstance().getDataTableValue(testData, "Expected Time"))){
				higher = true;
			}
		} catch (Exception e) {
			logger.error("Something went wrong cheking the MaxResponseTime");
		};
    	return higher;
    }  
	
    //It returns the Max Response Time of the report
    private Long getMaxResponseTime(List<String[]> data, int testResultsIteration) throws Exception {
    	int position = 0;
    	for (int i= 2; i<data.get(0).length; i++) {
    		if((data.get(0)[i]).equals("Max response time")) {
    			position = i;
    		}   		
    	}    	
    	if(position == 0) {
    		logger.warn("The Max response time can't be found");
    		throw new Exception ("The Max response time can't be found");
    	} else {
    		return Long.parseLong(data.get(testResultsIteration)[position]);
    	}
    }
    
    
    
	
}
