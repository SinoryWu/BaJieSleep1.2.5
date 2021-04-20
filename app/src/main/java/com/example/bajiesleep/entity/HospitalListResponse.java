package com.example.bajiesleep.entity;

import java.util.List;

public class HospitalListResponse {

    /**
     * code : 0
     * data : [{"hospitalid":24,"name":"道奇医疗器械有限公司"}]
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
         * hospitalid : 24
         * name : 道奇医疗器械有限公司
         */

        private int hospitalid;
        private String name;

        public int getHospitalid() {
            return hospitalid;
        }

        public void setHospitalid(int hospitalid) {
            this.hospitalid = hospitalid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
