package com.app.CarCia.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/11/27.
 */
public class ItemProductBean {


    private String Status;


    private CondEntity Cond;
    private String total;
    private String isNext;
    private String pageNO;
    private String Msg;


    private java.util.List<ListEntity> List;

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setCond(CondEntity Cond) {
        this.Cond = Cond;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setIsNext(String isNext) {
        this.isNext = isNext;
    }

    public void setPageNO(String pageNO) {
        this.pageNO = pageNO;
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

    public CondEntity getCond() {
        return Cond;
    }

    public String getTotal() {
        return total;
    }

    public String getIsNext() {
        return isNext;
    }

    public String getPageNO() {
        return pageNO;
    }

    public String getMsg() {
        return Msg;
    }

    public java.util.List<ListEntity> getList() {
        return List;
    }

    public static class CondEntity {
        private String catid;
        private String spaceid;
        private String styleid;
        private String keywords;

        public void setCatid(String catid) {
            this.catid = catid;
        }

        public void setSpaceid(String spaceid) {
            this.spaceid = spaceid;
        }

        public void setStyleid(String styleid) {
            this.styleid = styleid;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getCatid() {
            return catid;
        }

        public String getSpaceid() {
            return spaceid;
        }

        public String getStyleid() {
            return styleid;
        }

        public String getKeywords() {
            return keywords;
        }
    }

    public static class ListEntity extends BaseObservable implements Serializable {
        private String ID;
        private String Title;
        private String CatPos;
        private String Thumb;
        private String Image;
        /**
         * ProID : 687
         * ProNo : 1CG803007F
         * ProSpec : 800x800mm
         * ProData :
         * ProImg : http://diy.appbaba.com/garcia/uploadfile/2015/1203/20151203043547583.jpg
         * DetailUrl : http://diy.appbaba.com/garcia/index
         * .php?m=content&c=app&a=product_detail&id=687
         */

        private java.util.List<LinkProductEntity> LinkProduct;

        public void setID(String ID) {
            this.ID = ID;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public void setCatPos(String CatPos) {
            this.CatPos = CatPos;
        }

        public void setThumb(String Thumb) {
            this.Thumb = Thumb;
        }

        public void setImage(String Image) {
            this.Image = Image;
        }

        public void setLinkProduct(java.util.List<LinkProductEntity> LinkProduct) {
            this.LinkProduct = LinkProduct;
        }

        public String getID() {
            return ID;
        }

        @Bindable
        public String getTitle() {
            return Title;
        }

        public String getCatPos() {
            return CatPos;
        }

        public String getThumb() {
            return Thumb;
        }

        public String getImage() {
            return Image;
        }

        public java.util.List<LinkProductEntity> getLinkProduct() {
            return LinkProduct;
        }

        public static class LinkProductEntity extends BaseObservable implements Serializable {
            private String ProID;
            private String ProNo;
            private String ProSpec;
            private String ProData;
            private String ProImg;
            private String DetailUrl;

            public void setProID(String ProID) {
                this.ProID = ProID;
            }

            public void setProNo(String ProNo) {
                this.ProNo = ProNo;
            }

            public void setProSpec(String ProSpec) {
                this.ProSpec = ProSpec;
            }

            public void setProData(String ProData) {
                this.ProData = ProData;
            }

            public void setProImg(String ProImg) {
                this.ProImg = ProImg;
            }

            public void setDetailUrl(String DetailUrl) {
                this.DetailUrl = DetailUrl;
            }

            public String getProID() {
                return ProID;
            }

            @Bindable
            public String getProNo() {
                return ProNo;
            }

            @Bindable
            public String getProSpec() {
                return ProSpec;
            }

            public String getProData() {
                return ProData;
            }

            public String getProImg() {
                return ProImg;
            }

            public String getDetailUrl() {
                return DetailUrl;
            }
        }
    }
}
