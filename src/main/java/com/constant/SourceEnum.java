package com.constant;

public enum SourceEnum {
    OA(1,"OA"),HR(2,"HR"),IMPORT(3,"导入");

    private int code;
    private String value;

    SourceEnum(int code, String value){
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
    public static String getValue(int code){
        SourceEnum[] values = SourceEnum.values();
        for (SourceEnum source : values) {
            if (source.getCode() == code) {
                return source.getValue();
            }
        }
        return "";
    }
}
