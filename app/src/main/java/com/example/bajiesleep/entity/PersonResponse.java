package com.example.bajiesleep.entity;

public class PersonResponse {

    /**
     * code : 0
     * msg : ok
     * data : {"truename":"志明鸣","hospitalname":"道奇医疗器械有限公司"}
     */


    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PersonResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "truename='" + truename + '\'' +
                    ", hospitalname='" + hospitalname + '\'' +
                    '}';
        }

        /**
         * truename : 志明鸣
         * hospitalname : 道奇医疗器械有限公司
         */

        private String truename;
        private String hospitalname;

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getHospitalname() {
            return hospitalname;
        }

        public void setHospitalname(String hospitalname) {
            this.hospitalname = hospitalname;
        }
    }
}
