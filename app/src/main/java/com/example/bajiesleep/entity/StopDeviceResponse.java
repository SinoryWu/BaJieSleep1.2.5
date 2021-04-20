package com.example.bajiesleep.entity;

public class StopDeviceResponse {

    /**
     * code : 10006
     * msg : 操作失败:当前设备未处于使用状态
     * data :
     */

    private int code;
    private String msg;
    private String data;

    @Override
    public String toString() {
        return "StopDeviceResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
