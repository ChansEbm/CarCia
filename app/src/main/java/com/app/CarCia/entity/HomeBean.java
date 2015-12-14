package com.app.CarCia.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.Serializable;

/**
 * Created by ChanZeeBm on 2015/11/26.
 */
public class HomeBean extends BaseObservable {

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

    public static class ListEntity extends BaseObservable implements Serializable {
        private String StyleID;
        private String CatID;
        private String SpaceID;
        private String Title;
        private String Image;
        private boolean isSecond;
        private boolean isCheck;

        public ListEntity(boolean isSecond) {
            this.isSecond = isSecond;
        }

        public void setStyleID(String StyleID) {
            this.StyleID = StyleID;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setImage(String Image) {
            if (Image == null)
                this.Image = "";
            else
                this.Image = Image;
        }

        public String getStyleID() {
            return StyleID;
        }

        @Bindable
        public String getTitle() {
            return Title;
        }

        public String getImage() {
            return Image;
        }

        public boolean isSecond() {
            return isSecond;
        }

        public void setIsSecond(boolean isSecond) {
            this.isSecond = isSecond;
        }

        @Bindable
        public boolean isCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public String getCatID() {
            return CatID;
        }

        public void setCatID(String catID) {
            CatID = catID;
        }

        public String getSpaceID() {
            return SpaceID;
        }

        public void setSpaceID(String spaceID) {
            SpaceID = spaceID;
        }
    }
}

