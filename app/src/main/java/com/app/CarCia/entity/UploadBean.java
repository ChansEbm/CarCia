package com.app.CarCia.entity;

/**
 * Created by ChanZeeBm on 2015/12/8.
 */
public class UploadBean {

    private int Status;
    private String Msg;

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public int getStatus() {
        return Status;
    }

    public String getMsg() {
        return Msg;
    }
}
