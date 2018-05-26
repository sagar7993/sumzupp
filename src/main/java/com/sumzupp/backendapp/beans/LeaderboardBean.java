package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 08-Nov-17.
 */
public class LeaderboardBean extends StatusBean {

    private List<StudentRankingDetailsBean> studentRankingDetailsBeans = new ArrayList<>();

    public LeaderboardBean() {

    }

    public List<StudentRankingDetailsBean> getStudentRankingDetailsBeans() {
        return studentRankingDetailsBeans;
    }

    public void setStudentRankingDetailsBeans(List<StudentRankingDetailsBean> studentRankingDetailsBeans) {
        this.studentRankingDetailsBeans = studentRankingDetailsBeans;
    }
}
