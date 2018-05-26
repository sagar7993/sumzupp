package com.sumzupp.backendapp.enums;

/**
 * Created by akash.mercer on 25-Jul-17.
 */
public enum  FileTypeEnum {
    STUDENT_DATA_SHEET(0), TEACHER_DATA_SHEET(1), QUESTION_SHEET(2), QUESTION_PAPER(3);

    private int type;

    FileTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
