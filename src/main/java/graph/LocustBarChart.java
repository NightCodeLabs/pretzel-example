package graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import helpers.FileOperations;
import performance.LocustReportReader;

public class LocustBarChart {
		
	private static final String TITLE = "Performance Execution Results";
    private static final String CATEGORYAXISLABELTITLE = "Load Distribution";
    private static final String VALUEAXISLABELTITLE = "Requests";
    private static final int CHARTWIDTH= 1000;
    private static final int CHARTHEIGHT=800;
    private static final String ABSOLUTEPATHTOSTORECHART = Paths.get("target/cucumber-reports/locustcharts/").toFile().getAbsolutePath();
    private static final String ERROR = "ERROR CREATING THE GRAPH";
    
    private String reportName = "performanceChart"+System.currentTimeMillis()+".png";
    private String filePath = ABSOLUTEPATHTOSTORECHART + "/"+reportName;
    private String requests;
    private String failures;
    private String medianRT;
    private String averageRT;
    private String minRT;
    private String maxRT;
    private String requestPS;    
    private String requestResults;
    
	private JFreeChart chart;
	private DefaultCategoryDataset dataset;
	
	FileOperations fileOperations = new FileOperations();
	LocustReportReader locustReportReader = new LocustReportReader();
	


    public String getMaxRT() {
		return maxRT;
	}

    public String getReportName() {
    	return this.reportName;
    }
    
	public void createChart(int testResultsIteration) {
    	File file = new File(filePath);
        this.setRequestResults(testResultsIteration);
        chart = ChartFactory.createBarChart(TITLE,CATEGORYAXISLABELTITLE,VALUEAXISLABELTITLE, createDataset(testResultsIteration), PlotOrientation.VERTICAL,false,true,false);
        chart.addSubtitle(0, new TextTitle(" "));
        chart.addSubtitle(1, new TextTitle(getRequestResults()));
        chart.addSubtitle(2, new TextTitle(" "));
        try {
            ChartUtils.saveChartAsPNG(file, chart, CHARTWIDTH, CHARTHEIGHT);
        } catch (IOException error) {
            System.out.println(error.getMessage());
            System.out.println(ERROR);
        }
    }

    private CategoryDataset createDataset(int testResultsIteration) {
        dataset = new DefaultCategoryDataset();
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get50()), "Requests", "50%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get66()), "Requests", "66%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get75()), "Requests", "75%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get80()), "Requests", "80%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get90()), "Requests", "90%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get95()), "Requests", "95%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get98()), "Requests", "98%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get99()), "Requests", "99%");
        dataset.setValue(Integer.parseInt(locustReportReader.getLocustDistributionDataList().get(testResultsIteration).get100()), "Requests", "100%");
        return dataset;
    }

    private String getRequestResults(){
        return requestResults;  
    }
    
    private void setRequestResults(int testResultsIteration) {
    	requests= locustReportReader.getLocustRequestDataList().get(testResultsIteration).getRequests();
        failures=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getFailures();
        medianRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getMedianResponseTime();
        averageRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getAverageResponseTime();
        minRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getMinResponseTime();
        maxRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getMaxResponseTime();
        requestPS=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getRequestsS();
        requestResults = "# Requests: "+ requests + "      # Failures: " + failures +"      Median: "+medianRT+"      Average: "+averageRT+"      Min RT: "+minRT+"      Max RT: "+maxRT+"      Requests/s: "+requestPS; 
    }

}
