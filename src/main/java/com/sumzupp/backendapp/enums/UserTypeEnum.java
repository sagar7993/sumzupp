package com.sumzupp.backendapp.enums;

/**
 * Created by akash.mercer on 22-Jun-17.
 */
public enum UserTypeEnum {

    STUDENT(1), TEACHER(2), PARENT(3), INSTITUTE(4);

    private int type;

    UserTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
