package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 05-Jul-17.
 */
public class UserAddressBean {

    private Double addressLine;

    private String landmark;

    private String locality;

    private Double latitude;

    private Double longitude;

    public UserAddressBean() {

    }

    public Double getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(Double addressLine) {
        this.addressLine = addressLine;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
