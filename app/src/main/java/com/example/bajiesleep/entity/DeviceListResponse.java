package com.example.bajiesleep.entity;

import java.util.List;

public class DeviceListResponse {

    /**
     * code : 0
     * data : [{"sn":"S01D1907000067","truename":"明知李","devStatus":"待回收"},{"sn":"S01D1907000052","truename":"","devStatus":"闲置"},{"sn":"S01D1907000050","truename":"张三三","devStatus":"已借出"},{"sn":"S01D190700005A","truename":"测试1","devStatus":"已借出"},{"sn":"S01D200300005C","truename":"李志明","devStatus":"逾期1天"},{"sn":"S01D1907000064","truename":"","devStatus":"闲置"},{"sn":"S01D1907000068","truename":"吴利华","devStatus":"逾期56天"},{"sn":"S01D190700006A","truename":"李倩","devStatus":"逾期23天"}]
     * msg : ok
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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
        /**
         * sn : S01D1907000067
         * truename : 明知李
         * devStatus : 待回收
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
