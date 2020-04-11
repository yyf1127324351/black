package com.common;

import java.util.ArrayList;
import java.util.List;

public class BaseResponse<T> {


    private Integer code;

    private String message;



    // 自定义返回数据
    // @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // easyui datagrid 需要这2个数据才能正确运行事件
    private Long total = 0L;
    private List<?> rows = new ArrayList<>();

    public BaseResponse() {
        this.code = BaseResponse.ResponseCode.SUCCESS.value;
        this.message = BaseResponse.ResponseCode.SUCCESS.description;
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(BaseResponse.ResponseCode.SUCCESS.value, BaseResponse.ResponseCode.SUCCESS.description, null);
    }

    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<T>(BaseResponse.ResponseCode.SUCCESS.value, message, null);
    }
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(BaseResponse.ResponseCode.SUCCESS.value, BaseResponse.ResponseCode.SUCCESS.description, data);
    }
    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<T>(BaseResponse.ResponseCode.SUCCESS.value, message, data);
    }
    public static <T> BaseResponse<T> error() {
        return new BaseResponse<T>(BaseResponse.ResponseCode.SYS_ERROR.value, BaseResponse.ResponseCode.SYS_ERROR.description, null);
    }
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<T>(BaseResponse.ResponseCode.SYS_ERROR.value, message, null);
    }
    public static <T> BaseResponse<T> paramEerror() {
        return new BaseResponse<T>(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.value, BaseResponse.ResponseCode.PARAM_CHECK_FAIL.description, null);
    }
    public static <T> BaseResponse<T> paramEerror(String message) {
        return new BaseResponse<T>(BaseResponse.ResponseCode.PARAM_CHECK_FAIL.value, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public enum ResponseCode{

        SUCCESS(200,"success"),

        PARAM_CHECK_FAIL(442,"request params check fail"),

        SYS_ERROR(500,"system error");


        private Integer value;
        private String description;

        ResponseCode(Integer value, String description) {
            this.value = value;
            this.description = description;
        }

        public Integer getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

    }


}
