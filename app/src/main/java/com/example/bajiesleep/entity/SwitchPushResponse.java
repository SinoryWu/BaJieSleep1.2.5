package com.example.bajiesleep.entity;

public class SwitchPushResponse {

    @Override
    public String toString() {
        return "SwitchPushResponse{" +
                "code=" + code +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    /**
     * code : 0
     * data :
     * msg : 更新成功
     */


    private int code;
    private String data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
