package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocustRequestData {


	    @SerializedName("Average Content Size")
	    @Expose
	    private String averageContentSize;
	    @SerializedName("Requests/s")
	    @Expose
	    private String requestsS;
	    @SerializedName("# failures")
	    @Expose
	    private String failures;
	    @SerializedName("# requests")
	    @Expose
	    private String requests;
	    @SerializedName("Average response time")
	    @Expose
	    private String averageResponseTime;
	    @SerializedName("Median response time")
	    @Expose
	    private String medianResponseTime;
	    @SerializedName("Min response time")
	    @Expose
	    private String minResponseTime;
	    @SerializedName("Max response time")
	    @Expose
	    private String maxResponseTime;
	    @SerializedName("Method")
	    @Expose
	    private String method;
	    @SerializedName("Name")
	    @Expose
	    private String name;

	    public String getAverageContentSize() {
	        return averageContentSize;
	    }

	    public void setAverageContentSize(String averageContentSize) {
	        this.averageContentSize = averageContentSize;
	    }

	    public String getRequestsS() {
	        return requestsS;
	    }

	    public void setRequestsS(String requestsS) {
	        this.requestsS = requestsS;
	    }

	    public String getFailures() {
	        return failures;
	    }

	    public void setFailures(String failures) {
	        this.failures = failures;
	    }

	    public String getRequests() {
	        return requests;
	    }

	    public void setRequests(String requests) {
	        this.requests = requests;
	    }

	    public String getAverageResponseTime() {
	        return averageResponseTime;
	    }

	    public void setAverageResponseTime(String averageResponseTime) {
	        this.averageResponseTime = averageResponseTime;
	    }

	    public String getMedianResponseTime() {
	        return medianResponseTime;
	    }

	    public void setMedianResponseTime(String medianResponseTime) {
	        this.medianResponseTime = medianResponseTime;
	    }

	    public String getMinResponseTime() {
	        return minResponseTime;
	    }

	    public void setMinResponseTime(String minResponseTime) {
	        this.minResponseTime = minResponseTime;
	    }

	    public String getMaxResponseTime() {
	        return maxResponseTime;
	    }

	    public void setMaxResponseTime(String maxResponseTime) {
	        this.maxResponseTime = maxResponseTime;
	    }

	    public String getMethod() {
	        return method;
	    }

	    public void setMethod(String method) {
	        this.method = method;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	

}
