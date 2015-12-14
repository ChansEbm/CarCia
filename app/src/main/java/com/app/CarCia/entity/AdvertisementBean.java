package com.app.CarCia.entity;

/**
 * Created by ChanZeeBm on 2015/12/4.
 */
public class AdvertisementBean {

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

    public static class ListEntity {
        private String Url;
        private String Name;
        private String ID;
        private String Image;

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public String getUrl() {
            return Url;
        }

        public String getName() {
            return Name;
        }

        public String getID() {
            return ID;
        }

        public String getImage() {
            return Image;
        }
    }
}
