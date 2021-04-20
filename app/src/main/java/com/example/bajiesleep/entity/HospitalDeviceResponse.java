package com.example.bajiesleep.entity;

public class HospitalDeviceResponse {

    /**
     * code : 0
     * data : {"free":5,"total":9,"getReport":8,"needReport":0,"totalReport":323}
     * msg : ok
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


        @Override
        public String toString() {
            return "DataBean{" +
                    "free=" + free +
                    ", total=" + total +
                    ", getReport=" + getReport +
                    ", needReport=" + needReport +
                    ", totalReport=" + totalReport +
                    ", male=" + male +
                    ", female=" + female +
                    ", totalMember=" + totalMember +
                    '}';
        }

        /**
         * free : 5
         * total : 9
         * getReport : 8
         * needReport : 0
         * totalReport : 323
         */

        private int free;
        private int total;
        private int getReport;
        private int needReport;
        private int totalReport;
        private int male;
        private int female;
        private int totalMember;

        public int getMale() {
            return male;
        }

        public void setMale(int male) {
            this.male = male;
        }

        public int getFemale() {
            return female;
        }

        public void setFemale(int female) {
            this.female = female;
        }

        public int getTotalMember() {
            return totalMember;
        }

        public void setTotalMember(int totalMember) {
            this.totalMember = totalMember;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getGetReport() {
            return getReport;
        }

        public void setGetReport(int getReport) {
            this.getReport = getReport;
        }

        public int getNeedReport() {
            return needReport;
        }

        public void setNeedReport(int needReport) {
            this.needReport = needReport;
        }

        public int getTotalReport() {
            return totalReport;
        }

        public void setTotalReport(int totalReport) {
            this.totalReport = totalReport;
        }
    }
}
