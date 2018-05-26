package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 17-Oct-17.
 */
public class InstitutesNamesBean extends StatusBean {

    private List<String> instituteNames = new ArrayList<>();

    public InstitutesNamesBean() {

    }

    public List<String> getInstituteNames() {
        return instituteNames;
    }

    public void setInstituteNames(List<String> instituteNames) {
        this.instituteNames = instituteNames;
    }
}
