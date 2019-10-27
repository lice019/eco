package com.eco.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * description: 通用结果数据响应实体 <br>
 * date: 2019/10/26 22:35 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //保证key为null时，则消失
public class Res<T> implements Serializable {

    private Integer status;   //响应状态
    private String msg;       //响应消息
    private T data;           //响应数据


    private Res(Integer status) {
        this.status = status;
    }

    private Res(String msg) {
        this.msg = msg;
    }

    private Res(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Res(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private Res(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Res<T> success() {
        return new Res<T>(HttpStatus.OK.value());
    }

    public static <T> Res<T> success(Integer status) {
        return new Res<T>(status);
    }

    public static <T> Res<T> success(Integer status, String msg) {
        return new Res<T>(status, msg);
    }

    public static <T> Res<T> success(Integer status, T data) {
        return new Res<T>(status, data);
    }

    public static <T> Res<T> success(Integer status, String msg, T data) {
        return new Res<T>(status, msg, data);
    }

    public static <T> Res<T> error() {
        return new Res<T>(HttpStatus.BAD_REQUEST.value());
    }

    public static <T> Res<T> error(Integer status) {
        return new Res<T>(status);
    }

    public static <T> Res<T> error(String msg) {
        return new Res<T>(msg);
    }

    public static <T> Res<T> error(Integer status, String msg) {
        return new Res<T>(status, msg);
    }

    public static <T> Res<T> error(Integer status, String msg, T data) {
        return new Res<T>(status, msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
