package com.example.bajiesleep.entity;

import java.util.List;

public class HospitalListResponsePushSet {

    @Override
    public String toString() {
        return "TestResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public HospitalListResponsePushSet(int code, String msg, List<DataBean> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public HospitalListResponsePushSet() {

    }

    /**
     * code : 0
     * data : [{"hospitalid":"24","name":"道奇医疗器械有限公司"},{"hospitalid":"25","name":"万康体检中心1"}]
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
        public DataBean(String hospitalid, String name) {
            this.hospitalid = hospitalid;
            this.name = name;
        }

        public DataBean() {
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "hospitalid='" + hospitalid + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * hospitalid : 24
         * name : 道奇医疗器械有限公司
         */




        private String hospitalid;
        private String name;

        public String getHospitalid() {
            return hospitalid;
        }

        public void setHospitalid(String hospitalid) {
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
