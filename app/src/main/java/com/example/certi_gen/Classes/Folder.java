package com.example.certi_gen.Classes;

import java.util.List;

public class Folder {
    public  String  name;
    public String noOfItem;
    public String size;
    public String dateCreated;
    public String description;
    public List<Certificate> certificateList;
    public String tempPath;
    public String signPath;
    public String excelPath;
    public String certiDate;

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getSignPath() {
        return signPath;
    }

    public void setSignPath(String signPath) {
        this.signPath = signPath;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getCertiDate() {
        return certiDate;
    }

    public void setCertiDate(String certiDate) {
        this.certiDate = certiDate;
    }

    public Folder() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoOfItem() {
        return noOfItem;
    }

    public void setNoOfItem(String noOfItem) {
        this.noOfItem = noOfItem;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Certificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    public Folder(String name, String noOfItem, String size, String dateCreated, String description, List<Certificate> certificateList) {
        this.name = name;
        this.noOfItem = noOfItem;
        this.size = size;
        this.dateCreated = dateCreated;
        this.description = description;
        this.certificateList = certificateList;
    }
}
