package com.sumzupp.backendapp.beans;

import org.springframework.data.domain.Page;

/**
 * Created by akash.mercer on 27-Jun-17.
 */
public class CommentsBean extends StatusBean {

    private Page<CommentBean> commentsPage;

    public CommentsBean() {

    }

    public Page<CommentBean> getCommentsPage() {
        return commentsPage;
    }

    public void setCommentsPage(Page<CommentBean> commentsPage) {
        this.commentsPage = commentsPage;
    }
}
