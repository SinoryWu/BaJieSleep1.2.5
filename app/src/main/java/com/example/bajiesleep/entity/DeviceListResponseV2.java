package com.example.bajiesleep.entity;

import java.util.List;

public class DeviceListResponseV2 {

    @Override
    public String toString() {
        return "DeviceListResponseV2{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * code : 0
     * data : {"data":{"current_page":1,"data":[{"outTime":40,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"","swversion":"","sim":"","reportStatus":0,"lastUpdateTime":1606894779,"truename":"李倩","telephone":"13588175940","sn":"S01D190700006A","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"李玉祥","telephone":"17698076671","sn":"S01D1907000068","status":20},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.10245","swversion":"","sim":"89860404191791270327","reportStatus":0,"lastUpdateTime":1604009079,"truename":"","telephone":"","sn":"S01D1907000063","status":1},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"李俊骏","telephone":"15857628008","sn":"S01D1907000069","status":20},{"outTime":44,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9659","swversion":"","sim":"89860404191791271344","reportStatus":0,"lastUpdateTime":1604099467,"truename":"南鸣华","telephone":"13867608775","sn":"S01D190700006C","status":15},{"outTime":60,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"安涛","telephone":"13901338820","sn":"S01D1907000062","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"","telephone":"","sn":"S01D1907000064","status":1},{"outTime":11,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.10245","swversion":"","sim":"89860404191791272020","reportStatus":0,"lastUpdateTime":1604052715,"truename":"李二狗","telephone":"13569888909","sn":"S01D200300005C","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9286","swversion":"","sim":"89860404191791270481","reportStatus":0,"lastUpdateTime":1603777539,"truename":"李佳颖","telephone":"13967618520","sn":"S01D190700005E","status":20},{"outTime":43,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9659","swversion":"","sim":"89860404191791270322","reportStatus":0,"lastUpdateTime":1603923399,"truename":"蒋武君","telephone":"13058783505","sn":"S01D190700005D","status":15}],"first_page_url":"/?page=1","from":1,"last_page":4,"last_page_url":"/?page=4","next_page_url":"/?page=2","path":"/","per_page":10,"prev_page_url":null,"to":10,"total":34},"onlineNum":0,"totalNum":34}
     * msg : ok
     */


    private int code;
    private DataBeanXX data;
    private String msg;
    private int onlineNum;
    private int totalNum;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBeanXX getData() {
        return data;
    }

    public void setData(DataBeanXX data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }


    public static class DataBeanXX {
        @Override
        public String toString() {
            return "DataBeanXX{" +
                    "data=" + data +
                    ", onlineNum=" + onlineNum +
                    ", totalNum=" + totalNum +
                    '}';
        }

        /**
         * data : {"current_page":1,"data":[{"outTime":40,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"","swversion":"","sim":"","reportStatus":0,"lastUpdateTime":1606894779,"truename":"李倩","telephone":"13588175940","sn":"S01D190700006A","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"李玉祥","telephone":"17698076671","sn":"S01D1907000068","status":20},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.10245","swversion":"","sim":"89860404191791270327","reportStatus":0,"lastUpdateTime":1604009079,"truename":"","telephone":"","sn":"S01D1907000063","status":1},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"李俊骏","telephone":"15857628008","sn":"S01D1907000069","status":20},{"outTime":44,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9659","swversion":"","sim":"89860404191791271344","reportStatus":0,"lastUpdateTime":1604099467,"truename":"南鸣华","telephone":"13867608775","sn":"S01D190700006C","status":15},{"outTime":60,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"安涛","telephone":"13901338820","sn":"S01D1907000062","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"","telephone":"","sn":"S01D1907000064","status":1},{"outTime":11,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.10245","swversion":"","sim":"89860404191791272020","reportStatus":0,"lastUpdateTime":1604052715,"truename":"李二狗","telephone":"13569888909","sn":"S01D200300005C","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9286","swversion":"","sim":"89860404191791270481","reportStatus":0,"lastUpdateTime":1603777539,"truename":"李佳颖","telephone":"13967618520","sn":"S01D190700005E","status":20},{"outTime":43,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9659","swversion":"","sim":"89860404191791270322","reportStatus":0,"lastUpdateTime":1603923399,"truename":"蒋武君","telephone":"13058783505","sn":"S01D190700005D","status":15}],"first_page_url":"/?page=1","from":1,"last_page":4,"last_page_url":"/?page=4","next_page_url":"/?page=2","path":"/","per_page":10,"prev_page_url":null,"to":10,"total":34}
         * onlineNum : 0
         * totalNum : 34
         */

        private DataBeanX data;
        private int onlineNum;
        private int totalNum;

        public DataBeanX getData() {
            return data;
        }

        public void setData(DataBeanX data) {
            this.data = data;
        }

        public int getOnlineNum() {
            return onlineNum;
        }

        public void setOnlineNum(int onlineNum) {
            this.onlineNum = onlineNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
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
             * data : [{"outTime":40,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"","swversion":"","sim":"","reportStatus":0,"lastUpdateTime":1606894779,"truename":"李倩","telephone":"13588175940","sn":"S01D190700006A","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"李玉祥","telephone":"17698076671","sn":"S01D1907000068","status":20},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.10245","swversion":"","sim":"89860404191791270327","reportStatus":0,"lastUpdateTime":1604009079,"truename":"","telephone":"","sn":"S01D1907000063","status":1},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"李俊骏","telephone":"15857628008","sn":"S01D1907000069","status":20},{"outTime":44,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9659","swversion":"","sim":"89860404191791271344","reportStatus":0,"lastUpdateTime":1604099467,"truename":"南鸣华","telephone":"13867608775","sn":"S01D190700006C","status":15},{"outTime":60,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"安涛","telephone":"13901338820","sn":"S01D1907000062","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"--","swversion":"","sim":"--","reportStatus":0,"lastUpdateTime":946656000,"truename":"","telephone":"","sn":"S01D1907000064","status":1},{"outTime":11,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.10245","swversion":"","sim":"89860404191791272020","reportStatus":0,"lastUpdateTime":1604052715,"truename":"李二狗","telephone":"13569888909","sn":"S01D200300005C","status":15},{"outTime":0,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9286","swversion":"","sim":"89860404191791270481","reportStatus":0,"lastUpdateTime":1603777539,"truename":"李佳颖","telephone":"13967618520","sn":"S01D190700005E","status":20},{"outTime":43,"breathrate":0,"heartrate":0,"bloodoxygen":0,"tempetature":0,"ringsn":"","battery":0,"devStatus":1,"ringStatus":0,"powerStatus":-1,"versionno":"2.4.9659","swversion":"","sim":"89860404191791270322","reportStatus":0,"lastUpdateTime":1603923399,"truename":"蒋武君","telephone":"13058783505","sn":"S01D190700005D","status":15}]
             * first_page_url : /?page=1
             * from : 1
             * last_page : 4
             * last_page_url : /?page=4
             * next_page_url : /?page=2
             * path : /
             * per_page : 10
             * prev_page_url : null
             * to : 10
             * total : 34
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
                            "outTime=" + outTime +
                            ", breathrate=" + breathrate +
                            ", heartrate=" + heartrate +
                            ", bloodoxygen=" + bloodoxygen +
                            ", tempetature=" + tempetature +
                            ", ringsn='" + ringsn + '\'' +
                            ", battery=" + battery +
                            ", devStatus=" + devStatus +
                            ", ringStatus=" + ringStatus +
                            ", powerStatus=" + powerStatus +
                            ", versionno='" + versionno + '\'' +
                            ", swversion='" + swversion + '\'' +
                            ", sim='" + sim + '\'' +
                            ", reportStatus=" + reportStatus +
                            ", lastUpdateTime=" + lastUpdateTime +
                            ", truename='" + truename + '\'' +
                            ", telephone='" + telephone + '\'' +
                            ", sn='" + sn + '\'' +
                            ", status=" + status +
                            ", modeType='" + modeType + '\'' +
                            '}';
                }




                /**
                 * outTime : 40
                 * breathrate : 0
                 * heartrate : 0
                 * bloodoxygen : 0
                 * tempetature : 0
                 * ringsn :
                 * battery : 0
                 * devStatus : 1
                 * ringStatus : 0
                 * powerStatus : -1
                 * versionno :
                 * swversion :
                 * sim :
                 * reportStatus : 0
                 * lastUpdateTime : 1606894779
                 * truename : 李倩
                 * telephone : 13588175940
                 * sn : S01D190700006A
                 * status : 15
                 */


                private int outTime;
                private int breathrate;
                private int heartrate;
                private int bloodoxygen;
                private int tempetature;
                private String ringsn;
                private int battery;
                private int devStatus;
                private int ringStatus;
                private int powerStatus;
                private String versionno;
                private String swversion;
                private String sim;
                private int reportStatus;
                private int lastUpdateTime;
                private String truename;
                private String telephone;
                private String sn;
                private int status;
                private String modeType;

                public String getModeType() {
                    return modeType;
                }

                public void setModeType(String modeType) {
                    this.modeType = modeType;
                }

                public int getOutTime() {
                    return outTime;
                }

                public void setOutTime(int outTime) {
                    this.outTime = outTime;
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

                public int getDevStatus() {
                    return devStatus;
                }

                public void setDevStatus(int devStatus) {
                    this.devStatus = devStatus;
                }

                public int getRingStatus() {
                    return ringStatus;
                }

                public void setRingStatus(int ringStatus) {
                    this.ringStatus = ringStatus;
                }

                public int getPowerStatus() {
                    return powerStatus;
                }

                public void setPowerStatus(int powerStatus) {
                    this.powerStatus = powerStatus;
                }

                public String getVersionno() {
                    return versionno;
                }

                public void setVersionno(String versionno) {
                    this.versionno = versionno;
                }

                public String getSwversion() {
                    return swversion;
                }

                public void setSwversion(String swversion) {
                    this.swversion = swversion;
                }

                public String getSim() {
                    return sim;
                }

                public void setSim(String sim) {
                    this.sim = sim;
                }

                public int getReportStatus() {
                    return reportStatus;
                }

                public void setReportStatus(int reportStatus) {
                    this.reportStatus = reportStatus;
                }

                public int getLastUpdateTime() {
                    return lastUpdateTime;
                }

                public void setLastUpdateTime(int lastUpdateTime) {
                    this.lastUpdateTime = lastUpdateTime;
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
            }
        }
    }
}
