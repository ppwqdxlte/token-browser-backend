package com.laowang.tokenbrowserbackend.entity;

import java.io.Serializable;
import java.util.Objects;

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data = null;     // data after success
    private String msg = null; // message of success or exception or error
    private Integer code = null; // result code
    private Throwable throwable = null; // instance of Exception or Error

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result<?> result = (Result<?>) o;
        return Objects.equals(getData(), result.getData()) && Objects.equals(getMsg(), result.getMsg()) && Objects.equals(getCode(), result.getCode()) && Objects.equals(getThrowable(), result.getThrowable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData(), getMsg(), getCode(), getThrowable());
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", throwable=" + throwable +
                '}';
    }

    public Result(String msg, Throwable throwable) {
        this.msg = msg;
        this.throwable = throwable;
    }

    public Result(String msg, Integer code, Throwable throwable) {
        this.msg = msg;
        this.code = code;
        this.throwable = throwable;
    }

    public Result(T data, String msg, Integer code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    public Result(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    public Result() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
