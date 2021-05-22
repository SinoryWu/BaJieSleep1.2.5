package com.example.bajiesleep.entity;

import java.util.List;

public class DeviceRecoverResponse {

    /**
     * code : 0
     * data : {"type":2,"uid":437,"sex":"男","mobile":"13512333212","truename":"李志明","report":[{"update_time":1,"report_id":"李志明03-10 05-54","quality":1,"reportUrl":"5e66bc707796d9006a347051"},{"update_time":1,"report_id":"李志明03-12 05-29","quality":2,"reportUrl":"5e695f737d5774006f2c1a75"},{"update_time":1,"report_id":"李志明03-16 05-14","quality":2,"reportUrl":"5e6ea57191db280077532749"},{"update_time":1,"report_id":"李志明03-16 05-14","quality":2,"reportUrl":"5e6ea57191db280077532749"},{"update_time":1,"report_id":"李志明03-19 05-52","quality":1,"reportUrl":"5e7299f62a6bfd0075a3e712"},{"update_time":1,"report_id":"李志明03-30 06-00","quality":2,"reportUrl":"5e811a7a2a6bfd0077a4f1f4"},{"update_time":1,"report_id":"李志明03-30 06-00","quality":2,"reportUrl":"5e811a7a2a6bfd0077a4f1f4"},{"update_time":0,"report_id":"李志明05-14 06-00","quality":2,"reportUrl":"5ebc6df07af3840006ea9bd8"},{"update_time":1590376444,"report_id":"李志明05-23 05-58","quality":1,"reportUrl":"5ec84b6c6b44640006656f98"},{"update_time":0,"report_id":"李志明06-16 17-58","quality":2,"reportUrl":"5eeb5c12cc6f51000926b8ff"},{"update_time":0,"report_id":"李志明06-19 03-18","quality":2,"reportUrl":"5eeb818d032d3100097f948d"},{"update_time":0,"report_id":"李志明06-19 05-59","quality":1,"reportUrl":"5eebe3eb42f0910006a3ddd5"},{"update_time":0,"report_id":"李志明06-21 02-21","quality":3,"reportUrl":"5eee548d750cd90008efacaf"},{"update_time":0,"report_id":"李志明06-21 05-59","quality":2,"reportUrl":"5eee86ed750cd90008efe64c"},{"update_time":0,"report_id":"李志明06-24 06-00","quality":1,"reportUrl":"5ef27b6f8dfcd90006490850"},{"update_time":0,"report_id":"李志明06-25 05-59","quality":1,"reportUrl":"5ef3ccfdede9b600065d3bb3"},{"update_time":1604028225,"report_id":"李志明06-30 06-00","quality":1,"reportUrl":"5efa646d5f53d200090fdc14"},{"update_time":1603962338,"report_id":"李志明07-17 06-00","quality":1,"reportUrl":"5f10ce052615b200086ee98c"},{"update_time":0,"report_id":"李志明06-20 05-59","quality":1,"reportUrl":"5eed356e3dd14a0008190b26"},{"update_time":0,"report_id":"李志明05-24 05-59","quality":1,"reportUrl":"5ec99cf4a932a100060ccb20"}],"reportNum":20,"sn":"S01D1907000068","hospitalid":"24"}
     * msg : ok
     */

    private int code;
    private DataBean data;
    private String msg;

    @Override
    public String toString() {
        return "DeviceRecoverResponse{" +
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
                    "type=" + type +
                    ", uid=" + uid +
                    ", sex='" + sex + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", truename='" + truename + '\'' +
                    ", reportNum=" + reportNum +
                    ", sn='" + sn + '\'' +
                    ", hospitalid='" + hospitalid + '\'' +
                    ", report=" + report +
                    '}';
        }

        /**
         * type : 2
         * uid : 437
         * sex : 男
         * mobile : 13512333212
         * truename : 李志明
         * report : [{"update_time":1,"report_id":"李志明03-10 05-54","quality":1,"reportUrl":"5e66bc707796d9006a347051"},{"update_time":1,"report_id":"李志明03-12 05-29","quality":2,"reportUrl":"5e695f737d5774006f2c1a75"},{"update_time":1,"report_id":"李志明03-16 05-14","quality":2,"reportUrl":"5e6ea57191db280077532749"},{"update_time":1,"report_id":"李志明03-16 05-14","quality":2,"reportUrl":"5e6ea57191db280077532749"},{"update_time":1,"report_id":"李志明03-19 05-52","quality":1,"reportUrl":"5e7299f62a6bfd0075a3e712"},{"update_time":1,"report_id":"李志明03-30 06-00","quality":2,"reportUrl":"5e811a7a2a6bfd0077a4f1f4"},{"update_time":1,"report_id":"李志明03-30 06-00","quality":2,"reportUrl":"5e811a7a2a6bfd0077a4f1f4"},{"update_time":0,"report_id":"李志明05-14 06-00","quality":2,"reportUrl":"5ebc6df07af3840006ea9bd8"},{"update_time":1590376444,"report_id":"李志明05-23 05-58","quality":1,"reportUrl":"5ec84b6c6b44640006656f98"},{"update_time":0,"report_id":"李志明06-16 17-58","quality":2,"reportUrl":"5eeb5c12cc6f51000926b8ff"},{"update_time":0,"report_id":"李志明06-19 03-18","quality":2,"reportUrl":"5eeb818d032d3100097f948d"},{"update_time":0,"report_id":"李志明06-19 05-59","quality":1,"reportUrl":"5eebe3eb42f0910006a3ddd5"},{"update_time":0,"report_id":"李志明06-21 02-21","quality":3,"reportUrl":"5eee548d750cd90008efacaf"},{"update_time":0,"report_id":"李志明06-21 05-59","quality":2,"reportUrl":"5eee86ed750cd90008efe64c"},{"update_time":0,"report_id":"李志明06-24 06-00","quality":1,"reportUrl":"5ef27b6f8dfcd90006490850"},{"update_time":0,"report_id":"李志明06-25 05-59","quality":1,"reportUrl":"5ef3ccfdede9b600065d3bb3"},{"update_time":1604028225,"report_id":"李志明06-30 06-00","quality":1,"reportUrl":"5efa646d5f53d200090fdc14"},{"update_time":1603962338,"report_id":"李志明07-17 06-00","quality":1,"reportUrl":"5f10ce052615b200086ee98c"},{"update_time":0,"report_id":"李志明06-20 05-59","quality":1,"reportUrl":"5eed356e3dd14a0008190b26"},{"update_time":0,"report_id":"李志明05-24 05-59","quality":1,"reportUrl":"5ec99cf4a932a100060ccb20"}]
         * reportNum : 20
         * sn : S01D1907000068
         * hospitalid : 24
         */

        private int type;
        private int uid;
        private String sex;
        private String mobile;
        private String truename;
        private int reportNum;
        private String sn;
        private String hospitalid;
        private List<ReportBean> report;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public int getReportNum() {
            return reportNum;
        }

        public void setReportNum(int reportNum) {
            this.reportNum = reportNum;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getHospitalid() {
            return hospitalid;
        }

        public void setHospitalid(String hospitalid) {
            this.hospitalid = hospitalid;
        }

        public List<ReportBean> getReport() {
            return report;
        }

        public void setReport(List<ReportBean> report) {
            this.report = report;
        }

        public static class ReportBean {
            @Override
            public String toString() {
                return "ReportBean{" +
                        "update_time=" + update_time +
                        ", report_id='" + report_id + '\'' +
                        ", quality=" + quality +
                        ", reportUrl='" + reportUrl + '\'' +
                        ", truename='" + truename + '\'' +
                        ", hospitalid='" + hospitalid + '\'' +
                        ", id=" + id +
                        '}';
            }

            /**
             * update_time : 1
             * report_id : 李志明03-10 05-54
             * quality : 1
             * reportUrl : 5e66bc707796d9006a347051
             */


            private int update_time;
            private String report_id;
            private int quality;
            private String reportUrl;
            private String truename;
            private String hospitalid;
            private int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHospitalid() {
                return hospitalid;
            }

            public void setHospitalid(String hospitalid) {
                this.hospitalid = hospitalid;
            }

            public String getTrueName() {
                return truename;
            }

            public void setTrueName(String truename) {
                this.truename = truename;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public String getReport_id() {
                return report_id;
            }

            public void setReport_id(String report_id) {
                this.report_id = report_id;
            }

            public int getQuality() {
                return quality;
            }

            public void setQuality(int quality) {
                this.quality = quality;
            }

            public String getReportUrl() {
                return reportUrl;
            }

            public void setReportUrl(String reportUrl) {
                this.reportUrl = reportUrl;
            }
        }
    }
}
