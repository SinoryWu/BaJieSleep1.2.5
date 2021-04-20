package com.example.bajiesleep.entity;

public class AppointReportResponse {

    /**
     * code : 0
     * data : {"truename":"","examine":"","id":213}
     * msg : ok
     */

    private int code;
    private DataBean data;
    private String msg;

    @Override
    public String toString() {
        return "AppointReportResponse{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

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
                    "truename='" + truename + '\'' +
                    ", examine='" + examine + '\'' +
                    ", id=" + id +
                    '}';
        }

        /**
         * truename :
         * examine :
         * id : 213
         */


        private String truename;
        private String examine;
        private int id;

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getExamine() {
            return examine;
        }

        public void setExamine(String examine) {
            this.examine = examine;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
