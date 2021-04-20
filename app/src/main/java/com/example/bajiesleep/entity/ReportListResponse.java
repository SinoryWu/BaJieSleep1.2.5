package com.example.bajiesleep.entity;

import java.util.List;

public class ReportListResponse {

    @Override
    public String toString() {
        return "ReportListResponse{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * code : 0
     * data : {"current_page":1,"data":[{"id":724,"sn":"S01D1907000066","ahi":"2.15","uid":797,"report_id":"24-1970-01-01-797","truename":"李家乐","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:45-06:00","quality":1,"createTime":1603922418,"reportUrl":"5f99e9eff1eab16a311823e9"},{"id":676,"sn":"S01D1907000067","ahi":"3.7","uid":1276,"report_id":"24-1970-01-01-1276","truename":"王小荣","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:29-06:00","quality":2,"createTime":1603576824,"reportUrl":"5f94a6ebd4e1a402264b3bc5"},{"id":665,"sn":"S01D190700005A","ahi":"14.69","uid":1296,"report_id":"24-1970-01-01-1296","truename":"屠文莉","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:08-05:58","quality":1,"createTime":1603403931,"reportUrl":"5f9200f0af264e63af1e76c2"},{"id":656,"sn":"S01D190700005A","ahi":"15.04","uid":1296,"report_id":"24-1970-01-01-1296","truename":"屠文莉","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:28-05:46","quality":1,"createTime":1603316802,"reportUrl":"5f90af7010701e479ba20a13"},{"id":785,"sn":"S01D190700006A","ahi":"1.36","uid":1269,"report_id":"24-1970-01-01-1269","truename":"李倩","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:46-05:59","quality":1,"createTime":1603231199,"reportUrl":"5f8f5dedf21f8d6577a230d3"},{"id":637,"sn":"S01D190700006A","ahi":"1.18","uid":1269,"report_id":"24-1970-01-01-1269","truename":"李倩","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:13-05:54","quality":1,"createTime":1603144472,"reportUrl":"5f8e0c719a9f216f408bab2d"},{"id":627,"sn":"S01D1907000067","ahi":"8.39","uid":1277,"report_id":"24-1970-01-01-1277","truename":"金洪烈","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:21-06:00","quality":1,"createTime":1603058429,"reportUrl":"5f8cbee4376121399132e7f9"},{"id":625,"sn":"S01D190700005A","ahi":"9.49","uid":1275,"report_id":"24-1970-01-01-1275","truename":"冯巍韬","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:39-06:00","quality":1,"createTime":1603058424,"reportUrl":"5f8cbaee32f36b5226a761dd"},{"id":619,"sn":"S01D190700005A","ahi":"8.82","uid":1275,"report_id":"24-1970-01-01-1275","truename":"冯巍韬","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:48-06:00","quality":1,"createTime":1602972001,"reportUrl":"5f8b696cbdb3ce7ab7296bc0"},{"id":602,"sn":"S01D190700005A","ahi":"37.75","uid":237,"report_id":"24-1970-01-01-237","truename":"陈李平","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"01:43-05:59","quality":1,"createTime":1602799173,"reportUrl":"5f88c679d86e0a3005a6e7ea"},{"id":600,"sn":"S01D1907000050","ahi":"3.54","uid":1054,"report_id":"24-1970-01-01-1054","truename":"江凤","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:33-05:48","quality":2,"createTime":1602798525,"reportUrl":"5f88c66e37612139912829f7"},{"id":595,"sn":"S01D190700006A","ahi":"0.83","uid":1269,"report_id":"24-1970-01-01-1269","truename":"李倩","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:04-05:59","quality":1,"createTime":1602712791,"reportUrl":"5f8774f0e8fb9335394c1c40"},{"id":594,"sn":"S01D1907000067","ahi":"3.8","uid":1268,"report_id":"24-1970-01-01-1268","truename":"王国伟","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"01:33-05:59","quality":2,"createTime":1602712768,"reportUrl":"5f8777c037ed203f3f7f922a"},{"id":588,"sn":"S01D1907000057","ahi":"5.7","uid":237,"report_id":"24-1970-01-01-237","truename":"陈李平","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:31-05:59","quality":1,"createTime":1602626379,"reportUrl":"5f86237237c25c6db891b053"},{"id":580,"sn":"S01D1907000057","ahi":"24.63","uid":237,"report_id":"24-1970-01-01-237","truename":"陈李平","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"01:06-05:59","quality":1,"createTime":1602539973,"reportUrl":"5f84d1f4d46dc37f028f4272"}],"first_page_url":"/?page=1","from":1,"last_page":10,"last_page_url":"/?page=10","next_page_url":"/?page=2","path":"/","per_page":15,"prev_page_url":null,"to":15,"total":138}
     * msg : ok
     */


    private int code;
    private DataBeanX data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBeanX {
        @Override
        public String toString() {
            return "DataBeanX{" +
                    "current_page=" + current_page +
                    ", first_page_url='" + first_page_url + '\'' +
                    ", from=" + from +
                    ", last_page=" + last_page +
                    ", last_page_url='" + last_page_url + '\'' +
                    ", next_page_url='" + next_page_url + '\'' +
                    ", path='" + path + '\'' +
                    ", per_page=" + per_page +
                    ", prev_page_url=" + prev_page_url +
                    ", to=" + to +
                    ", total=" + total +
                    ", data=" + data +
                    '}';
        }

        /**
         * current_page : 1
         * data : [{"id":724,"sn":"S01D1907000066","ahi":"2.15","uid":797,"report_id":"24-1970-01-01-797","truename":"李家乐","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:45-06:00","quality":1,"createTime":1603922418,"reportUrl":"5f99e9eff1eab16a311823e9"},{"id":676,"sn":"S01D1907000067","ahi":"3.7","uid":1276,"report_id":"24-1970-01-01-1276","truename":"王小荣","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:29-06:00","quality":2,"createTime":1603576824,"reportUrl":"5f94a6ebd4e1a402264b3bc5"},{"id":665,"sn":"S01D190700005A","ahi":"14.69","uid":1296,"report_id":"24-1970-01-01-1296","truename":"屠文莉","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:08-05:58","quality":1,"createTime":1603403931,"reportUrl":"5f9200f0af264e63af1e76c2"},{"id":656,"sn":"S01D190700005A","ahi":"15.04","uid":1296,"report_id":"24-1970-01-01-1296","truename":"屠文莉","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:28-05:46","quality":1,"createTime":1603316802,"reportUrl":"5f90af7010701e479ba20a13"},{"id":785,"sn":"S01D190700006A","ahi":"1.36","uid":1269,"report_id":"24-1970-01-01-1269","truename":"李倩","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"23:46-05:59","quality":1,"createTime":1603231199,"reportUrl":"5f8f5dedf21f8d6577a230d3"},{"id":637,"sn":"S01D190700006A","ahi":"1.18","uid":1269,"report_id":"24-1970-01-01-1269","truename":"李倩","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:13-05:54","quality":1,"createTime":1603144472,"reportUrl":"5f8e0c719a9f216f408bab2d"},{"id":627,"sn":"S01D1907000067","ahi":"8.39","uid":1277,"report_id":"24-1970-01-01-1277","truename":"金洪烈","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:21-06:00","quality":1,"createTime":1603058429,"reportUrl":"5f8cbee4376121399132e7f9"},{"id":625,"sn":"S01D190700005A","ahi":"9.49","uid":1275,"report_id":"24-1970-01-01-1275","truename":"冯巍韬","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:39-06:00","quality":1,"createTime":1603058424,"reportUrl":"5f8cbaee32f36b5226a761dd"},{"id":619,"sn":"S01D190700005A","ahi":"8.82","uid":1275,"report_id":"24-1970-01-01-1275","truename":"冯巍韬","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:48-06:00","quality":1,"createTime":1602972001,"reportUrl":"5f8b696cbdb3ce7ab7296bc0"},{"id":602,"sn":"S01D190700005A","ahi":"37.75","uid":237,"report_id":"24-1970-01-01-237","truename":"陈李平","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"01:43-05:59","quality":1,"createTime":1602799173,"reportUrl":"5f88c679d86e0a3005a6e7ea"},{"id":600,"sn":"S01D1907000050","ahi":"3.54","uid":1054,"report_id":"24-1970-01-01-1054","truename":"江凤","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:33-05:48","quality":2,"createTime":1602798525,"reportUrl":"5f88c66e37612139912829f7"},{"id":595,"sn":"S01D190700006A","ahi":"0.83","uid":1269,"report_id":"24-1970-01-01-1269","truename":"李倩","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:04-05:59","quality":1,"createTime":1602712791,"reportUrl":"5f8774f0e8fb9335394c1c40"},{"id":594,"sn":"S01D1907000067","ahi":"3.8","uid":1268,"report_id":"24-1970-01-01-1268","truename":"王国伟","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"01:33-05:59","quality":2,"createTime":1602712768,"reportUrl":"5f8777c037ed203f3f7f922a"},{"id":588,"sn":"S01D1907000057","ahi":"5.7","uid":237,"report_id":"24-1970-01-01-237","truename":"陈李平","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"00:31-05:59","quality":1,"createTime":1602626379,"reportUrl":"5f86237237c25c6db891b053"},{"id":580,"sn":"S01D1907000057","ahi":"24.63","uid":237,"report_id":"24-1970-01-01-237","truename":"陈李平","hospitalid":24,"hospitalName":"道奇医疗器械有限公司","sleeptime":"01:06-05:59","quality":1,"createTime":1602539973,"reportUrl":"5f84d1f4d46dc37f028f4272"}]
         * first_page_url : /?page=1
         * from : 1
         * last_page : 10
         * last_page_url : /?page=10
         * next_page_url : /?page=2
         * path : /
         * per_page : 15
         * prev_page_url : null
         * to : 15
         * total : 138
         */


        private int current_page;
        private String first_page_url;
        private int from;
        private int last_page;
        private String last_page_url;
        private String next_page_url;
        private String path;
        private int per_page;
        private Object prev_page_url;
        private int to;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public String getFirst_page_url() {
            return first_page_url;
        }

        public void setFirst_page_url(String first_page_url) {
            this.first_page_url = first_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getLast_page_url() {
            return last_page_url;
        }

        public void setLast_page_url(String last_page_url) {
            this.last_page_url = last_page_url;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public Object getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(Object prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            @Override
            public String toString() {
                return "DataBean{" +
                        "id=" + id +
                        ", sn='" + sn + '\'' +
                        ", ahi='" + ahi + '\'' +
                        ", uid=" + uid +
                        ", report_id='" + report_id + '\'' +
                        ", truename='" + truename + '\'' +
                        ", hospitalid=" + hospitalid +
                        ", hospitalName='" + hospitalName + '\'' +
                        ", sleeptime='" + sleeptime + '\'' +
                        ", quality=" + quality +
                        ", createTime=" + createTime +
                        ", reportUrl='" + reportUrl + '\'' +
                        '}';
            }

            /**
             * id : 724
             * sn : S01D1907000066
             * ahi : 2.15
             * uid : 797
             * report_id : 24-1970-01-01-797
             * truename : 李家乐
             * hospitalid : 24
             * hospitalName : 道奇医疗器械有限公司
             * sleeptime : 00:45-06:00
             * quality : 1
             * createTime : 1603922418
             * reportUrl : 5f99e9eff1eab16a311823e9
             */


            private int id;
            private String sn;
            private String ahi;
            private int uid;
            private String report_id;
            private String truename;
            private int hospitalid;
            private String hospitalName;
            private String sleeptime;
            private int quality;
            private int createTime;
            private String reportUrl;
            private int modeType;
            private int ahiLevel;


            public int getModeType() {
                return modeType;
            }

            public void setModeType(int modeType) {
                this.modeType = modeType;
            }

            public int getAhiLevel() {
                return ahiLevel;
            }

            public void setAhiLevel(int ahiLevel) {
                this.ahiLevel = ahiLevel;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getAhi() {
                return ahi;
            }

            public void setAhi(String ahi) {
                this.ahi = ahi;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getReport_id() {
                return report_id;
            }

            public void setReport_id(String report_id) {
                this.report_id = report_id;
            }

            public String getTruename() {
                return truename;
            }

            public void setTruename(String truename) {
                this.truename = truename;
            }

            public int getHospitalid() {
                return hospitalid;
            }

            public void setHospitalid(int hospitalid) {
                this.hospitalid = hospitalid;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getSleeptime() {
                return sleeptime;
            }

            public void setSleeptime(String sleeptime) {
                this.sleeptime = sleeptime;
            }

            public int getQuality() {
                return quality;
            }

            public void setQuality(int quality) {
                this.quality = quality;
            }

            public int getCreateTime() {
                return createTime;
            }

            public void setCreateTime(int createTime) {
                this.createTime = createTime;
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
