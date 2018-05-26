package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.UserBean;
import com.sumzupp.backendapp.entities.TeacherInstitute;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.repositories.TeacherInstituteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Service
public class TeacherInstituteService {
    private static final String TAG = "TeacherInstituteService : ";

    @Autowired
    private TeacherInstituteRepository teacherInstituteRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(TeacherInstitute teacherInstitute) throws Exception {
        teacherInstituteRepository.save(teacherInstitute);
    }

    public void saveAll(List<TeacherInstitute> teacherInstitutes) throws Exception {
        teacherInstituteRepository.save(teacherInstitutes);
    }

    public TeacherInstitute findByTeacher(User teacher) {
        return teacherInstituteRepository.findByTeacher(teacher);
    }

    public List<TeacherInstitute> findByInstitute(User institute) {
        return teacherInstituteRepository.findByInstitute(institute);
    }

    public List<UserBean> findTeacherByInstitute(User institute) {
        List<TeacherInstitute> teacherInstitutes = teacherInstituteRepository.findByInstitute(institute);

        List<UserBean> userBeans = new ArrayList<>();

        for (TeacherInstitute teacherInstitute : teacherInstitutes) {
            userBeans.add(new UserBean(teacherInstitute.getTeacher(), teacherInstitute.getInstitute()));
        }

        return userBeans;
    }
}
