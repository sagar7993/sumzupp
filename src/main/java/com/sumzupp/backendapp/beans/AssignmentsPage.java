package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 06-Jul-17.
 */

// TODO: 23-Sep-17 Remove this class as soon as possible
public class AssignmentsPage {

    private List<AssignmentBean> content = new ArrayList<>();

    private Boolean last;

    private Integer totalPages;

    private Integer totalElements;

    private Boolean first;

    private Integer numberOfElements;

    private Integer size;

    private Integer number;

    public AssignmentsPage() {

    }

    public List<AssignmentBean> getContent() {
        return content;
    }

    public void setContent(List<AssignmentBean> content) {
        this.content = content;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
