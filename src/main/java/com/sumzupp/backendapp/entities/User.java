package com.sumzupp.backendapp.entities;

import com.sumzupp.backendapp.beans.SignUpBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

/**
 * Created by akash.mercer on 21-Jun-17.
 */

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(length = 10)
    private String contactNumber;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String profilePicUrl;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean active = true;

    @Column
    private String rollNumber;

    @Column(nullable = false)
    private String accountType;

    @Column(nullable = false)
    private String deviceType;

    @Column(nullable = false)
    private String appVersion;

    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private Integer quizScore = 0;

    @Column(nullable = false, columnDefinition = "int(11) default 0")
    private Integer status = 0;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean admin = false;

    @Column
    private String fcmToken;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_type", nullable = false)
    private UserType userType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address")
    private UserAddress userAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standard_division")
    private StandardDivision standardDivision;

    public User() {

    }

    public User(SignUpBean signUpBean) {
        name = signUpBean.getName();
        username = signUpBean.getUsername();
        password = signUpBean.getPassword();
        contactNumber = signUpBean.getContactNumber();
        email = signUpBean.getEmail();
        accountType = signUpBean.getAccountType();
        deviceType = signUpBean.getDeviceType();
        appVersion = signUpBean.getAppVersion();
        fcmToken = signUpBean.getFcmToken();
    }

    @PrePersist
    public void setUsernameAndPassword() {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
            Random random = new Random();

            this.username = String.valueOf(100000 + random.nextInt(900000));
            this.password = String.valueOf(100000 + random.nextInt(900000));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
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

    public Integer getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(Integer quizScore) {
        this.quizScore = quizScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public StandardDivision getStandardDivision() {
        return standardDivision;
    }

    public void setStandardDivision(StandardDivision standardDivision) {
        this.standardDivision = standardDivision;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", active=" + active +
                ", accountType='" + accountType + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", status=" + status +
                ", admin=" + admin +
                ", fcmToken='" + fcmToken + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userType=" + userType +
                ", userAddress=" + userAddress +
                ", standardDivision=" + standardDivision +
                '}';
    }
}
