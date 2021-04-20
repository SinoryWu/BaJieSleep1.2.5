package com.example.bajiesleep.entity;

import java.util.List;

public class SearchDeviceScanResponse {


    /**
     * code : 0
     * data : [{"sn":"S01D200300005C","truename":"","devStatus":"闲置"}]
     * msg : ok
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "SearchDeviceScanResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        public DataBean(String sn, String truename,String devStatus) {
            this.sn = sn;
            this.truename = truename;
            this.devStatus =devStatus;
        }

        public DataBean() {
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "sn='" + sn + '\'' +
                    ", truename='" + truename + '\'' +
                    ", devStatus='" + devStatus + '\'' +
                    '}';
        }

        /**
         * sn : S01D200300005C
         * truename :
         * devStatus : 闲置
         */


        private String sn;
        private String truename;
        private String devStatus;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getDevStatus() {
            return devStatus;
        }

        public void setDevStatus(String devStatus) {
            this.devStatus = devStatus;
        }
    }
}
