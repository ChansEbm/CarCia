package com.app.CarCia.entity;

/**
 * Created by ChanZeeBm on 2015/12/2.
 */
public class PhotoWallBean {
    private String fileUrl;
    private boolean isChoice;

    public PhotoWallBean(String fileUrl, boolean isChoice) {
        this.fileUrl = fileUrl;
        this.isChoice = isChoice;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setIsChoice(boolean isChoice) {
        this.isChoice = isChoice;
    }
}
