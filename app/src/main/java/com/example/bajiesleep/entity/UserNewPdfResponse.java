package com.example.bajiesleep.entity;

import java.util.List;

public class UserNewPdfResponse {

    /**
     * code : 0
     * data : {"current_page":1,"data":[{"id":1031,"uid":391,"truename":"金洁","mobile":"13735886128","create_time":1601475248,"sex":"2","using":false,"report":[{"sn":"S01D1907000057","ahi":"1.66","report_id":"24-2020-10-11-391","quality":1,"createTime":1602367194,"reportUrl":"5f822f24e81ba025bb3a986d"},{"sn":"S01D1907000057","ahi":"15.99","report_id":"24-2020-10-03-391","quality":1,"createTime":1601675818,"reportUrl":"5f77a2f122b01f59bcb5f9b8"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"}],"first_page_url":"/?page=1","from":1,"last_page":7,"last_page_url":"/?page=7","next_page_url":"/?page=2","path":"/","per_page":15,"prev_page_url":null,"to":15,"total":101}
     * msg : ok
     */

    private int code;
    private DataBeanX data;
    private String msg;

    @Override
    public String toString() {
        return "UserListResponse{" +
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
         * data : [{"id":1031,"uid":391,"truename":"金洁","mobile":"13735886128","create_time":1601475248,"sex":"2","using":false,"report":[{"sn":"S01D1907000057","ahi":"1.66","report_id":"24-2020-10-11-391","quality":1,"createTime":1602367194,"reportUrl":"5f822f24e81ba025bb3a986d"},{"sn":"S01D1907000057","ahi":"15.99","report_id":"24-2020-10-03-391","quality":1,"createTime":1601675818,"reportUrl":"5f77a2f122b01f59bcb5f9b8"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"}]
         * first_page_url : /?page=1
         * from : 1
         * last_page : 7
         * last_page_url : /?page=7
         * next_page_url : /?page=2
         * path : /
         * per_page : 15
         * prev_page_url : null
         * to : 15
         * total : 101
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
                        ", uid=" + uid +
                        ", truename='" + truename + '\'' +
                        ", mobile='" + mobile + '\'' +
                        ", create_time=" + create_time +
                        ", sex='" + sex + '\'' +
                        ", using=" + using +
                        ", hospitalid=" + hospitalid +
                        ", hospitalName='" + hospitalName + '\'' +
                        ", report=" + report +
                        '}';
            }

            /**
             * id : 1031
             * uid : 391
             * truename : 金洁
             * mobile : 13735886128
             * create_time : 1601475248
             * sex : 2
             * using : false
             * report : [{"sn":"S01D1907000057","ahi":"1.66","report_id":"24-2020-10-11-391","quality":1,"createTime":1602367194,"reportUrl":"5f822f24e81ba025bb3a986d"},{"sn":"S01D1907000057","ahi":"15.99","report_id":"24-2020-10-03-391","quality":1,"createTime":1601675818,"reportUrl":"5f77a2f122b01f59bcb5f9b8"}]
             * hospitalid : 24
             * hospitalName : 道奇医疗器械有限公司
             */


            private int id;
            private int uid;
            private String truename;
            private String mobile;
            private int create_time;
            private String sex;
            private boolean using;
            private int hospitalid;
            private String hospitalName;
            private List<ReportBean> report;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getTruename() {
                return truename;
            }

            public void setTruename(String truename) {
                this.truename = truename;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public boolean isUsing() {
                return using;
            }

            public void setUsing(boolean using) {
                this.using = using;
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
                            "sn='" + sn + '\'' +
                            ", ahi='" + ahi + '\'' +
                            ", report_id='" + report_id + '\'' +
                            ", quality=" + quality +
                            ", createTime=" + createTime +
                            ", reportUrl='" + reportUrl + '\'' +
                            '}';
                }

                /**
                 * sn : S01D1907000057
                 * ahi : 1.66
                 * report_id : 24-2020-10-11-391
                 * quality : 1
                 * createTime : 1602367194
                 * reportUrl : 5f822f24e81ba025bb3a986d
                 */


                private String sn;
                private String ahi;
                private String report_id;
                private int quality;
                private int createTime;
                private String reportUrl;

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
}
