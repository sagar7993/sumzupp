package com.sumzupp.backendapp.enums;

/**
 * Created by akash.mercer on 15-Sep-17.
 */
public enum S3BucketType {
    QUESTION_BANK_IMAGES("question-bank-images"), QUESTION_PAPERS("question-papers");

    private String name;

    S3BucketType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
