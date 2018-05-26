package com.sumzupp.backendapp.beans;

/**
 * Created by Sagar Jain on 7/23/2017.
 */
public class FileUploadBean {

    private String fileName;

    private Integer type;

    public FileUploadBean() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
