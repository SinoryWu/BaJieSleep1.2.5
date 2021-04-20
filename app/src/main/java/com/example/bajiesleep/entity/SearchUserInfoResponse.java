package com.example.bajiesleep.entity;

import java.util.List;

public class SearchUserInfoResponse {

    @Override
    public String toString() {
        return "SearchUserInfo{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * code : 0
     * data : {"current_page":1,"data":[{"id":658,"uid":846,"truename":"李子柒","mobile":"13333333333","create_time":1600929233,"sex":"1","using":false,"report":[{"sn":"S01D190700006A","ahi":"64.97","report_id":"24-2020-07-01-846","quality":1,"createTime":1593554371,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5efbb5f98d56c000088abc51"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":400,"uid":237,"truename":"陈李平","mobile":"13588110028","create_time":1590141326,"sex":"1","using":false,"report":[{"sn":"S01D1907000068","ahi":"28.91","report_id":"24-2020-05-24-237","quality":1,"createTime":1590271190,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ec99cf4a932a100060ccb20"},{"sn":"S01D1907000068","ahi":"21.44","report_id":"24-2020-05-23-237","quality":1,"createTime":1590184739,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ec84b6c6b44640006656f98"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":396,"uid":443,"truename":"明知李","mobile":"13188911888","create_time":1588396941,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":394,"uid":441,"truename":"迷李","mobile":"18969333311","create_time":1587964524,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":389,"uid":437,"truename":"李志明","mobile":"13512333212","create_time":1587368469,"sex":"1","using":true,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":388,"uid":436,"truename":"李家乐","mobile":"13569888961","create_time":1587351501,"sex":"1","using":false,"report":[{"sn":"S01D190700006D","ahi":"9.78","report_id":"24-2020-04-24-436","quality":1,"createTime":1587678989,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea20feef3d8170006549be7"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":383,"uid":431,"truename":"李代沫","mobile":"18900333399","create_time":1587007751,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":372,"uid":421,"truename":"李玉祥","mobile":"17698076671","create_time":1585122016,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":355,"uid":393,"truename":"李佳","mobile":"","create_time":1584717095,"sex":"1","using":false,"report":[{"sn":"S01D190700006A","ahi":"3.12","report_id":"24-2020-05-05-393","quality":3,"createTime":1588629587,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5eb090679dfcca000848d062"},{"sn":"S01D190700006A","ahi":"10.97","report_id":"24-2020-04-30-393","quality":1,"createTime":1588197606,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea9f8e8d8da2d0008313628"},{"sn":"S01D190700006A","ahi":"11.47","report_id":"24-2020-04-28-393","quality":1,"createTime":1588024780,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea755e893a418000691dd4f"},{"sn":"S01D190700006A","ahi":"14.81","report_id":"24-2020-04-27-393","quality":1,"createTime":1587938389,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea60468c089b7000826f4c9"},{"sn":"S01D190700006A","ahi":"13.13","report_id":"24-2020-04-22-393","quality":1,"createTime":1587506400,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e9f6ce8bade9f000938ac6b"},{"sn":"S01D190700006A","ahi":"12.52","report_id":"24-2020-04-21-393","quality":1,"createTime":1587419994,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e9e1b69e9e30d0008f66630"},{"sn":"S01D190700006A","ahi":"13.4","report_id":"24-2020-03-24-393","quality":1,"createTime":1585000814,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e793168fc36ed0076681bf3"},{"sn":"S01D190700006A","ahi":"19.64","report_id":"24-2020-03-22-393","quality":1,"createTime":1584827908,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e768e742a6bfd0075cfb5c2"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":86,"uid":164,"truename":"李测试3","mobile":"13511111113","create_time":1576131412,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"}],"first_page_url":"/?page=1","from":1,"last_page":2,"last_page_url":"/?page=2","next_page_url":"/?page=2","path":"/","per_page":10,"prev_page_url":null,"to":10,"total":13}
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
         * data : [{"id":658,"uid":846,"truename":"李子柒","mobile":"13333333333","create_time":1600929233,"sex":"1","using":false,"report":[{"sn":"S01D190700006A","ahi":"64.97","report_id":"24-2020-07-01-846","quality":1,"createTime":1593554371,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5efbb5f98d56c000088abc51"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":400,"uid":237,"truename":"陈李平","mobile":"13588110028","create_time":1590141326,"sex":"1","using":false,"report":[{"sn":"S01D1907000068","ahi":"28.91","report_id":"24-2020-05-24-237","quality":1,"createTime":1590271190,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ec99cf4a932a100060ccb20"},{"sn":"S01D1907000068","ahi":"21.44","report_id":"24-2020-05-23-237","quality":1,"createTime":1590184739,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ec84b6c6b44640006656f98"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":396,"uid":443,"truename":"明知李","mobile":"13188911888","create_time":1588396941,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":394,"uid":441,"truename":"迷李","mobile":"18969333311","create_time":1587964524,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":389,"uid":437,"truename":"李志明","mobile":"13512333212","create_time":1587368469,"sex":"1","using":true,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":388,"uid":436,"truename":"李家乐","mobile":"13569888961","create_time":1587351501,"sex":"1","using":false,"report":[{"sn":"S01D190700006D","ahi":"9.78","report_id":"24-2020-04-24-436","quality":1,"createTime":1587678989,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea20feef3d8170006549be7"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":383,"uid":431,"truename":"李代沫","mobile":"18900333399","create_time":1587007751,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":372,"uid":421,"truename":"李玉祥","mobile":"17698076671","create_time":1585122016,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":355,"uid":393,"truename":"李佳","mobile":"","create_time":1584717095,"sex":"1","using":false,"report":[{"sn":"S01D190700006A","ahi":"3.12","report_id":"24-2020-05-05-393","quality":3,"createTime":1588629587,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5eb090679dfcca000848d062"},{"sn":"S01D190700006A","ahi":"10.97","report_id":"24-2020-04-30-393","quality":1,"createTime":1588197606,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea9f8e8d8da2d0008313628"},{"sn":"S01D190700006A","ahi":"11.47","report_id":"24-2020-04-28-393","quality":1,"createTime":1588024780,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea755e893a418000691dd4f"},{"sn":"S01D190700006A","ahi":"14.81","report_id":"24-2020-04-27-393","quality":1,"createTime":1587938389,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5ea60468c089b7000826f4c9"},{"sn":"S01D190700006A","ahi":"13.13","report_id":"24-2020-04-22-393","quality":1,"createTime":1587506400,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e9f6ce8bade9f000938ac6b"},{"sn":"S01D190700006A","ahi":"12.52","report_id":"24-2020-04-21-393","quality":1,"createTime":1587419994,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e9e1b69e9e30d0008f66630"},{"sn":"S01D190700006A","ahi":"13.4","report_id":"24-2020-03-24-393","quality":1,"createTime":1585000814,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e793168fc36ed0076681bf3"},{"sn":"S01D190700006A","ahi":"19.64","report_id":"24-2020-03-22-393","quality":1,"createTime":1584827908,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5e768e742a6bfd0075cfb5c2"}],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"},{"id":86,"uid":164,"truename":"李测试3","mobile":"13511111113","create_time":1576131412,"sex":"1","using":false,"report":[],"hospitalid":24,"hospitalName":"道奇医疗器械有限公司"}]
         * first_page_url : /?page=1
         * from : 1
         * last_page : 2
         * last_page_url : /?page=2
         * next_page_url : /?page=2
         * path : /
         * per_page : 10
         * prev_page_url : null
         * to : 10
         * total : 13
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
             * id : 658
             * uid : 846
             * truename : 李子柒
             * mobile : 13333333333
             * create_time : 1600929233
             * sex : 1
             * using : false
             * report : [{"sn":"S01D190700006A","ahi":"64.97","report_id":"24-2020-07-01-846","quality":1,"createTime":1593554371,"reportUrl":"http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5efbb5f98d56c000088abc51"}]
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
                 * sn : S01D190700006A
                 * ahi : 64.97
                 * report_id : 24-2020-07-01-846
                 * quality : 1
                 * createTime : 1593554371
                 * reportUrl : http://120.26.54.110:9501/zgrequest/reportDetail?report_id=5efbb5f98d56c000088abc51
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
