package com.example.bajiesleep.entity;

import java.util.List;

public class MessageResponse {

    /**
     * code : 0
     * msg : ok
     * data : [{"name":"模版一","content":"【八戒睡眠】温馨提醒，进行睡眠监测时，请阅读说明书http://dwz.win/Mqk，注意语音提示；戒指佩戴时红灯常亮，监护仪收发灯橙色闪烁，代表监测正常，如有疑问，请联系微信客服或400-6899-570","id":1},{"name":"模版二","content":"【八戒睡眠】温馨提醒，您上次监测未产生有效报告，请将戒指充电后（充满后绿灯常亮），按照使用说明http://dwz.win/Mqk重新进行监测！如有疑问，请联系微信客服或400-6899-570","id":2},{"name":"模版三","content":"【八戒睡眠】温馨提醒，您的监测已结束，请及时将设备归还至医院以便出具报告！","id":3}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "MessageResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

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
         * name : 模版一
         * content : 【八戒睡眠】温馨提醒，进行睡眠监测时，请阅读说明书http://dwz.win/Mqk，注意语音提示；戒指佩戴时红灯常亮，监护仪收发灯橙色闪烁，代表监测正常，如有疑问，请联系微信客服或400-6899-570
         * id : 1
         */

        private String name;
        private String content;
        private int id;

        @Override
        public String toString() {
            return "DataBean{" +
                    "name='" + name + '\'' +
                    ", content='" + content + '\'' +
                    ", id=" + id +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
