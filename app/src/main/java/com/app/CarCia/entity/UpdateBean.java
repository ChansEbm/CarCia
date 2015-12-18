package com.app.CarCia.entity;

/**
 * Created by ChanZeeBm on 2015/12/18.
 */
public class UpdateBean {

    private String Status;
    private String Msg;
    private String Link;
    private String Package;

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }

    public void setPackage(String Package) {
        this.Package = Package;
    }

    public String getStatus() {
        return Status;
    }

    public String getMsg() {
        return Msg;
    }

    public String getLink() {
        return Link;
    }

    public String getPackage() {
        return Package;
    }
}
