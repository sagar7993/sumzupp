package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.enums.UserTypeEnum;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class UserBean {

    private String id;

    private String personName;

    private String contactNumber;

    private String email;

    private String profilePicUrl;

    private String fcmToken;

    private String instituteName;

    private Integer quizScore;

    private StandardDivisionBean standardDivisionBean;

    private StudentRankingBean studentRankingBean;

    private UserAddressBean userAddressBean;

    public UserBean() {

    }

    public UserBean(User user) {
        id = user.getId();
        personName = user.getName();
        contactNumber = user.getContactNumber();
        email = user.getEmail();
        profilePicUrl = user.getProfilePicUrl();
        fcmToken = user.getFcmToken();

        if (UserTypeEnum.STUDENT.getType() == user.getUserType().getType()) {
            if (user.getStandardDivision() != null) {
                instituteName = user.getStandardDivision().getInstitute().getName();
            }
        }

        quizScore = user.getQuizScore();

        if (user.getStandardDivision() != null) {
            standardDivisionBean = new StandardDivisionBean(user.getStandardDivision());
        }
    }

    public UserBean(User user, User institute) {
        id = user.getId();
        personName = user.getName();
        contactNumber = user.getContactNumber();
        email = user.getEmail();
        profilePicUrl = user.getProfilePicUrl();
        fcmToken = user.getFcmToken();

        if (UserTypeEnum.STUDENT.getType() == user.getUserType().getType()) {
            if (user.getStandardDivision() != null) {
                instituteName = user.getStandardDivision().getInstitute().getName();
            }
        } else {
            if (institute != null) {
                instituteName = institute.getName();
            }
        }

        if (user.getStandardDivision() != null) {
            standardDivisionBean = new StandardDivisionBean(user.getStandardDivision());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public StandardDivisionBean getStandardDivisionBean() {
        return standardDivisionBean;
    }

    public void setStandardDivisionBean(StandardDivisionBean standardDivisionBean) {
        this.standardDivisionBean = standardDivisionBean;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Integer getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(Integer quizScore) {
        this.quizScore = quizScore;
    }

    public StudentRankingBean getStudentRankingBean() {
        return studentRankingBean;
    }

    public void setStudentRankingBean(StudentRankingBean studentRankingBean) {
        this.studentRankingBean = studentRankingBean;
    }

    public UserAddressBean getUserAddressBean() {
        return userAddressBean;
    }

    public void setUserAddressBean(UserAddressBean userAddressBean) {
        this.userAddressBean = userAddressBean;
    }
}
