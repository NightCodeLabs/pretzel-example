package helpers;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	private static ConfigReader configReader = new ConfigReader();
	private final Properties properties = new Properties();
	
	private ConfigReader() {
		this.loadData();
	}
	
	public static ConfigReader getInstance() {
		return configReader;
	}	
	
	private void loadData(){
		try {
			properties.load(this.getClass().getResourceAsStream("/configs/config.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Configuration properties file cannot be found");
		}
	}
	
	public String getRequestReportPath() {
		return properties.getProperty("requestsReportLocation");
	}
	
	public String getDistributionReportPath() {
		return properties.getProperty("distributionReportLocation");
	}

	public String getChartPath() {
		return properties.getProperty("chartLocation");
	}
	
	public String getLocustMasterFilePath() {
		return properties.getProperty("locustMasterFile");
	}
	
	public String getCsvReportFolderPath() {
		return properties.getProperty("csvReportFolderLocation");
	}
	
	public String getExtentReportConfigPath() {
		return properties.getProperty("extentReportConfigLocation");
	}

}
