package com.app.CarCia.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ChanZeeBm on 2015/12/1.
 */
public class OrderCateBean extends BaseObservable {


    private String Status;
    private String Msg;


    private java.util.List<ListEntity> List;

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public void setList(java.util.List<ListEntity> List) {
        this.List = List;
    }

    public String getStatus() {
        return Status;
    }

    public String getMsg() {
        return Msg;
    }

    public java.util.List<ListEntity> getList() {
        return List;
    }

    public static class ListEntity extends BaseObservable{
        private String ID;
        private String Name;

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getID() {
            return ID;
        }

        @Bindable
        public String getName() {
            return Name;
        }
    }
}
