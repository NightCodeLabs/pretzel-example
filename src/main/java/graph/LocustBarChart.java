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

import performance.LocustReportReader;

public class LocustBarChart {
		
	private static final String TITLE = "Performance Execution Results";
    private static final String CATEGORYAXISLABELTITLE = "Load Distribution";
    private static final String VALUEAXISLABELTITLE = "Requests";
    private static final int CHARTWIDTH= 1000;
    private static final int CHARTHEIGHT=800;
    private static final String ABSOLUTEPATHTOSTORECHART = Paths.get("target/cucumber-reports/locustcharts/").toFile().getAbsolutePath();
    private static final String ERROR = "ERROR CREATING THE GRAPH";
    
    private String fileName;

    public String getFileName() {
    	return this.fileName;
    }    
    
    /**
     * Create the chart according to the csv perfromance results
     * @param testResultsIteration the index of the csv results in case of the csv contains more than one line results
     */
	public void createChart(int testResultsIteration) {
		this.fileName = "performanceChart"+System.currentTimeMillis()+".png";
    	File file = new File(ABSOLUTEPATHTOSTORECHART+"/"+this.fileName);
    	System.out.println("inside createChart: "+ file.getAbsolutePath());
		LocustReportReader locustReportReader = new LocustReportReader();
        //this.setRequestResults(locustReportReader, testResultsIteration);
        JFreeChart chart = ChartFactory.createBarChart(TITLE,CATEGORYAXISLABELTITLE,VALUEAXISLABELTITLE, createDataset(locustReportReader, testResultsIteration), PlotOrientation.VERTICAL,false,true,false);
        chart.addSubtitle(0, new TextTitle(" "));
        chart.addSubtitle(1, new TextTitle(this.createRequestResults(locustReportReader, testResultsIteration)));
        chart.addSubtitle(2, new TextTitle(" "));
        try {
            ChartUtils.saveChartAsPNG(file, chart, CHARTWIDTH, CHARTHEIGHT);
        } catch (IOException error) {
            System.out.println(error.getMessage());
            System.out.println(ERROR);
        }
    }
	
	/**
	 * Creates the dataset for the distribution of load
	 * @param locustReportReader the instance of the csv reader
	 * @param testResultsIteration the index of the csv results in case of the csv contains more than one line results
	 * @return it returns the dataset to be used in the public createChart method
	 */
    private CategoryDataset createDataset(LocustReportReader locustReportReader, int testResultsIteration) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
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
    
    /**
     * It generate the information of the performance results
     * @param locustReportReader the instance of the csv reader
     * @param testResultsIteration the index of the csv results in case of the csv contains more than one line results
     */
    private String createRequestResults(LocustReportReader locustReportReader, int testResultsIteration) {
    	String requests= locustReportReader.getLocustRequestDataList().get(testResultsIteration).getRequests();
        String failures=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getFailures();
        String medianRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getMedianResponseTime();
        String averageRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getAverageResponseTime();
        String minRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getMinResponseTime();
        String maxRT=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getMaxResponseTime();
        String requestPS=locustReportReader.getLocustRequestDataList().get(testResultsIteration).getRequestsS();
        String requestResults = "# Requests: "+ requests + "      # Failures: " + failures +"      Median: "+medianRT+"      Average: "+averageRT+"      Min RT: "+minRT+"      Max RT: "+maxRT+"      Requests/s: "+requestPS; 
        return requestResults;
    }


}
