package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.AdminLoginStatusBean;
import com.sumzupp.backendapp.beans.LoginBean;
import com.sumzupp.backendapp.entities.Admin;
import com.sumzupp.backendapp.repositories.AdminRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Service
public class AdminService {
    private static final String TAG = "AdminService : ";

    @Autowired
    private AdminRepository adminRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public Integer getCount() {
        return adminRepository.getCount();
    }

    public AdminLoginStatusBean login(LoginBean loginBean) {
        AdminLoginStatusBean adminLoginStatusBean = new AdminLoginStatusBean();
        adminLoginStatusBean.setStatus(0);
        adminLoginStatusBean.setMessage(Constants.INVALID_ADMIN_CREDENTIALS);

        Admin admin = adminRepository.login(loginBean.getEmail(), loginBean.getPassword());

        if(admin == null) {
            return adminLoginStatusBean;
        } else {
            adminLoginStatusBean.setStatus(1); adminLoginStatusBean.setMessage(Constants.LOGIN_SUCCESSFUL);
            adminLoginStatusBean.setEmail(admin.getEmail()); adminLoginStatusBean.setName(admin.getAdminName());
            return adminLoginStatusBean;
        }
    }
}
