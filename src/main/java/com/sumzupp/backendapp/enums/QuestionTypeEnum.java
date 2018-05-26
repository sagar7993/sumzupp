package com.sumzupp.backendapp.enums;

/**
 * Created by akash.mercer on 05-Jul-17.
 */
public enum QuestionTypeEnum {
    CHOOSE_ONE(1), FILL_IN_THE_BLANK(2);

    private int type;

    QuestionTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
