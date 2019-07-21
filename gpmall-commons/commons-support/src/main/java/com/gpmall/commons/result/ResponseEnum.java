package com.gpmall.commons.result;

public enum ResponseEnum {

    SUCCESS(0,"成功"),
    FAILED(1,""),
    ;
    private int code;
    private String msg;
    private ResponseEnum(int code,String msg){
        this.msg = msg;
        this.code = code;
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
}
