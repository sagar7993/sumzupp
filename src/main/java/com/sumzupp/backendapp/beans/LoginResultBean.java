package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.TeacherInstitute;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.enums.UserTypeEnum;

public class LoginResultBean extends StatusBean {

    private String userId;

    private String username;

    private String personName;

    private String contactNumber;

    private String email;

    private String profilePicUrl;

    private Integer userStatus;

    private Boolean admin;

    private String instituteName;

    private StandardDivisionBean standardDivisionBean;

    private Integer userType;

    public LoginResultBean(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public StandardDivisionBean getStandardDivisionBean() {
        return standardDivisionBean;
    }

    public void setStandardDivisionBean(StandardDivisionBean standardDivisionBean) {
        this.standardDivisionBean = standardDivisionBean;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public void populateData(User user, TeacherInstitute teacherInstitute) {
        this.userId = user.getId();
        this.personName = user.getName();
        this.contactNumber = user.getContactNumber();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profilePicUrl = user.getProfilePicUrl();
        this.userStatus = user.getStatus();
        this.admin = user.getAdmin();
        this.userType = user.getUserType().getType();

        if (UserTypeEnum.STUDENT.getType() == user.getUserType().getType()) {
            if (user.getStandardDivision() != null) {
                this.instituteName = user.getStandardDivision().getInstitute().getName();
            }
        } else {
            if (teacherInstitute != null) {
                this.instituteName = teacherInstitute.getInstitute().getName();
            }
        }

        if (user.getStandardDivision() != null) {
            this.standardDivisionBean = new StandardDivisionBean(user.getStandardDivision());
        }
    }
}