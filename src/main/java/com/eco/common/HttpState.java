package com.eco.common;

import org.omg.PortableInterceptor.INACTIVE;

/**
 * description: HttpState 封装统一响应状态枚举类<br>
 * date: 2019/11/3 16:33 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public enum HttpState {

    UNKNOWN_ERROR(-1, "未知错误"),

    SUCCESS(200, "成功"),

    PARAM_ERROR(201, "参数不合法"),

    DATABASE_ERROR(202, "数据库异常");

    private Integer status;
    private String message;


    HttpState(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer setStatus(Integer status) {
        this.status = status;
        return this.status;
    }

    public String getMessage() {
        return message;
    }

    public String setMessage(String message) {
        this.message = message;
        return this.message;
    }
}
