package com.sumzupp.backendapp.beans;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 20-Jun-17.
 */
public class SignUpBean {

    private String name;

    private String username;

    private String contactNumber;

    private String address;

    private String email;

    private String password;

    private String accountType;

    private String deviceType;

    private String appVersion;

    private String fcmToken;

    private String instituteId;

    private String standardDivisionId;

    private Integer userType;

    private UserAddressBean userAddressBean;

    private List<StandardBean> standardBeans = new ArrayList<>();

    private List<TeacherSubjectBean> teacherSubjectBeans = new ArrayList<>();

    public SignUpBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactNumber() {
        if (!TextUtils.isEmpty(contactNumber)) {
            return contactNumber.length() > 10 ? contactNumber.substring(3) : contactNumber;
        } else {
            return null;
        }
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getStandardDivisionId() {
        return standardDivisionId;
    }

    public void setStandardDivisionId(String standardDivisionId) {
        this.standardDivisionId = standardDivisionId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public UserAddressBean getUserAddressBean() {
        return userAddressBean;
    }

    public void setUserAddressBean(UserAddressBean userAddressBean) {
        this.userAddressBean = userAddressBean;
    }

    public List<StandardBean> getStandardBeans() {
        return standardBeans;
    }

    public void setStandardBeans(List<StandardBean> standardBeans) {
        this.standardBeans = standardBeans;
    }

    public List<TeacherSubjectBean> getTeacherSubjectBeans() {
        return teacherSubjectBeans;
    }

    public void setTeacherSubjectBeans(List<TeacherSubjectBean> teacherSubjectBeans) {
        this.teacherSubjectBeans = teacherSubjectBeans;
    }
}
