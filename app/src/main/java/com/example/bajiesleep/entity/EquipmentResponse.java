package com.example.bajiesleep.entity;

import java.util.List;

public class EquipmentResponse {


    /**
     * code : 0
     * data : [{"breathrate":12,"heartrate":67,"bloodoxygen":0,"tempetature":22,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":""},"ringStatus":"未连接","truename":"大抽奖","telephone":"12393929123","sn":"S01D1907000066","status":20,"id":52},{"breathrate":12,"heartrate":63,"bloodoxygen":0,"tempetature":23,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":"闲置"},"ringStatus":"未连接","truename":"","telephone":"","sn":"S01D1907000060","status":1,"id":51},{"breathrate":11,"heartrate":77,"bloodoxygen":0,"tempetature":27,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":""},"ringStatus":"未连接","truename":"哦我看的","telephone":"12030303992","sn":"S01D200300005C","status":20,"id":50},{"breathrate":13,"heartrate":66,"bloodoxygen":0,"tempetature":21,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":"闲置"},"ringStatus":"未连接","truename":"","telephone":"","sn":"S01D1907000064","status":1,"id":49},{"breathrate":16,"heartrate":67,"bloodoxygen":0,"tempetature":23,"ringsn":"","battery":0,"devStatus":{"content":"逾期31","dev_status":"未连接"},"ringStatus":"未连接","truename":"李家乐","telephone":"13569888961","sn":"S01D1907000068","status":15,"id":48},{"breathrate":24,"heartrate":64,"bloodoxygen":0,"tempetature":25,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":"闲置"},"ringStatus":"未连接","truename":"","telephone":"","sn":"S01D190700006A","status":1,"id":47},{"breathrate":20,"heartrate":72,"bloodoxygen":0,"tempetature":23,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":""},"ringStatus":"未连接","truename":"吴锦新","telephone":"13606666255","sn":"S01D190700006C","status":20,"id":43},{"breathrate":17,"heartrate":62,"bloodoxygen":0,"tempetature":27,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":"闲置"},"ringStatus":"未连接","truename":"","telephone":"","sn":"S01D1907000069","status":1,"id":42},{"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":{"dev_status":"未连接","content":"闲置"},"ringStatus":"未连接","truename":"","telephone":"","sn":"S01D190700006D","status":1,"id":40}]
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

    @Override
    public String toString() {
        return "EquipmentResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * breathrate : 12
         * heartrate : 67
         * bloodoxygen : 0
         * tempetature : 22
         * ringsn :
         * battery : 0
         * devStatus : {"dev_status":"未连接","content":""}
         * ringStatus : 未连接
         * truename : 大抽奖
         * telephone : 12393929123
         * sn : S01D1907000066
         * status : 20
         * id : 52
         */

        private int breathrate;
        private int heartrate;
        private int bloodoxygen;
        private int tempetature;
        private String ringsn;
        private int battery;
        private DevStatusBean devStatus;
        private String ringStatus;
        private String truename;
        private String telephone;
        private String sn;
        private int status;
        private int id;

        @Override
        public String toString() {
            return "DataBean{" +
                    "breathrate=" + breathrate +
                    ", heartrate=" + heartrate +
                    ", bloodoxygen=" + bloodoxygen +
                    ", tempetature=" + tempetature +
                    ", ringsn='" + ringsn + '\'' +
                    ", battery=" + battery +
                    ", devStatus=" + devStatus +
                    ", ringStatus='" + ringStatus + '\'' +
                    ", truename='" + truename + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", sn='" + sn + '\'' +
                    ", status=" + status +
                    ", id=" + id +
                    '}';
        }

        public int getBreathrate() {
            return breathrate;
        }

        public void setBreathrate(int breathrate) {
            this.breathrate = breathrate;
        }

        public int getHeartrate() {
            return heartrate;
        }

        public void setHeartrate(int heartrate) {
            this.heartrate = heartrate;
        }

        public int getBloodoxygen() {
            return bloodoxygen;
        }

        public void setBloodoxygen(int bloodoxygen) {
            this.bloodoxygen = bloodoxygen;
        }

        public int getTempetature() {
            return tempetature;
        }

        public void setTempetature(int tempetature) {
            this.tempetature = tempetature;
        }

        public String getRingsn() {
            return ringsn;
        }

        public void setRingsn(String ringsn) {
            this.ringsn = ringsn;
        }

        public int getBattery() {
            return battery;
        }

        public void setBattery(int battery) {
            this.battery = battery;
        }

        public DevStatusBean getDevStatus() {
            return devStatus;
        }

        public void setDevStatus(DevStatusBean devStatus) {
            this.devStatus = devStatus;
        }

        public String getRingStatus() {
            return ringStatus;
        }

        public void setRingStatus(String ringStatus) {
            this.ringStatus = ringStatus;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public static class DevStatusBean {
            /**
             * dev_status : 未连接
             * content :
             */


            private String dev_status;
            private String content;

            @Override
            public String toString() {
                return "DevStatusBean{" +
                        "dev_status='" + dev_status + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }

            public String getDev_status() {
                return dev_status;
            }

            public void setDev_status(String dev_status) {
                this.dev_status = dev_status;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
