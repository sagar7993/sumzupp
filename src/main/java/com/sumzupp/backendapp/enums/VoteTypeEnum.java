package com.sumzupp.backendapp.enums;

/**
 * Created by akash.mercer on 22-Jun-17.
 */
public enum VoteTypeEnum {

    UPVOTE(1), DOWNVOTE(2), LIKE(3), DISLIKE(4);

    private int type;

    VoteTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
