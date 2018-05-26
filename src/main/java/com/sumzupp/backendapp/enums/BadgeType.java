package com.sumzupp.backendapp.enums;

/**
 * Created by akash.mercer on 09-Sep-17.
 */
public enum BadgeType {
    BASIC(1), BRONZE(2), SILVER(3), GOLD(4), PLATINUM(5);

    private int type;

    BadgeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
