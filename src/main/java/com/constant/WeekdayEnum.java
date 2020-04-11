package com.constant;

public enum WeekdayEnum {
    MON(1,"mon"),TUS(2,"tus"),WED(3,"wed"),THU(4,"thu"),FRI(5,"fri"),SAT(6,"sat"),SUN(0,"sun");

    private int code;
    private String value;

    WeekdayEnum(int code, String value){
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
        WeekdayEnum[] values = WeekdayEnum.values();
        for (int i = 0; i < values.length; i++) {
            WeekdayEnum weekday = values[i];
            if (weekday.getCode() == code){
                return weekday.getValue();
            }
        }
        return null;
    }


}
