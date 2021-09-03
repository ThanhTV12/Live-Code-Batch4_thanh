package com.example.Exam.model;

import lombok.Data;


@Data
public class ResponseData<T> {
    private Status status;
    private T data;
    private String error;

    public ResponseData(T t) {
        this.status = Status.success;
        this.data = t;
    }

    public ResponseData(String error) {
        this.status = Status.fail;
        this.error = error;
    }


    public static enum Status {
        success, fail
    }

}
