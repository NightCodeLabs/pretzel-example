package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocustDistributionData {
	
    @SerializedName("100%")
    @Expose
    private String _100;
    @SerializedName("66%")
    @Expose
    private String _66;
    @SerializedName("99%")
    @Expose
    private String _99;
    @SerializedName("# requests")
    @Expose
    private String requests;
    @SerializedName("98%")
    @Expose
    private String _98;
    @SerializedName("75%")
    @Expose
    private String _75;
    @SerializedName("95%")
    @Expose
    private String _95;
    @SerializedName("50%")
    @Expose
    private String _50;
    @SerializedName("80%")
    @Expose
    private String _80;
    @SerializedName("90%")
    @Expose
    private String _90;
    @SerializedName("Name")
    @Expose
    private String name;

    public String get100() {
        return _100;
    }

    public void set100(String _100) {
        this._100 = _100;
    }

    public String get66() {
        return _66;
    }

    public void set66(String _66) {
        this._66 = _66;
    }

    public String get99() {
        return _99;
    }

    public void set99(String _99) {
        this._99 = _99;
    }

    public String getRequests() {
        return requests;
    }

    public void setRequests(String requests) {
        this.requests = requests;
    }

    public String get98() {
        return _98;
    }

    public void set98(String _98) {
        this._98 = _98;
    }

    public String get75() {
        return _75;
    }

    public void set75(String _75) {
        this._75 = _75;
    }

    public String get95() {
        return _95;
    }

    public void set95(String _95) {
        this._95 = _95;
    }

    public String get50() {
        return _50;
    }

    public void set50(String _50) {
        this._50 = _50;
    }

    public String get80() {
        return _80;
    }

    public void set80(String _80) {
        this._80 = _80;
    }

    public String get90() {
        return _90;
    }

    public void set90(String _90) {
        this._90 = _90;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
