package com.example.bajiesleep.entity;

public class PushSetResponse {


    /**
     * code : 0
     * data : {"devOnline":1,"ringOnline":1,"getReport":1,"hosHeadpic":""}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * devOnline : 1
         * ringOnline : 1
         * getReport : 1
         * hosHeadpic :
         */

        private int devOnline;
        private int ringOnline;
        private int getReport;
        private String hosHeadpic;

        public int getDevOnline() {
            return devOnline;
        }

        public void setDevOnline(int devOnline) {
            this.devOnline = devOnline;
        }

        public int getRingOnline() {
            return ringOnline;
        }

        public void setRingOnline(int ringOnline) {
            this.ringOnline = ringOnline;
        }

        public int getGetReport() {
            return getReport;
        }

        public void setGetReport(int getReport) {
            this.getReport = getReport;
        }

        public String getHosHeadpic() {
            return hosHeadpic;
        }

        public void setHosHeadpic(String hosHeadpic) {
            this.hosHeadpic = hosHeadpic;
        }
    }
}
