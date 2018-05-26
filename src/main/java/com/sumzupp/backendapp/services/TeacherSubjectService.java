package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.entities.*;
import com.sumzupp.backendapp.repositories.TeacherSubjectRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akash.mercer on 18-Jun-17.
 */

@Service
public class TeacherSubjectService {
    private static final String TAG = "TeacherSubjectService : ";

    @Autowired
    private TeacherSubjectRepository teacherSubjectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherInstituteService teacherInstituteService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentStandardDivisionService assignmentStandardDivisionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StandardDivisionService standardDivisionService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public List<SubjectBean> findByTeacher(User teacher) {
        return teacherSubjectRepository.findByTeacher(teacher);
    }

    public List<TeacherSubject> findTeacherSubjectByTeacher(User teacher) {
        return teacherSubjectRepository.findTeacherSubjectByTeacher(teacher);
    }

    public void saveAll(List<TeacherSubject> teacherSubjects) throws Exception {
        teacherSubjectRepository.save(teacherSubjects);
    }

    public void saveTeacherSubjects(User teahcer, List<TeacherSubjectBean> teacherSubjectBeans) {
        List<TeacherSubject> teacherSubjects = new ArrayList<>();

        for (TeacherSubjectBean teacherSubjectBean : teacherSubjectBeans) {
            Subject retrievedSubject = subjectService.findById(teacherSubjectBean.getSubjectId());

            for (String standardDivisionId : teacherSubjectBean.getStandardDivisionIds()) {
                StandardDivision retrievedStandardDivisiion = standardDivisionService.findById(standardDivisionId);

                teacherSubjects.add(new TeacherSubject(teahcer, retrievedSubject, retrievedStandardDivisiion));
            }
        }

        teacherSubjectRepository.save(teacherSubjects);
    }

    public StandardsBean getStandards(String teacherId){
        StandardsBean standardsBean = new StandardsBean();

        User retrievedTeacher = userService.findById(teacherId);

        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + teacherId);

            standardsBean.setStatus(0);
            standardsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return standardsBean;
        }

        standardsBean.setStandardBeans(teacherSubjectRepository.getStandards(retrievedTeacher));
        standardsBean.setStatus(1);

        return standardsBean;
    }

    public SubjectsBean findByStandards(String teacherId, String standardId) {
        SubjectsBean subjectsBean = new SubjectsBean();

        User retrievedTeacher = userService.findById(teacherId);

        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + teacherId);

            subjectsBean.setStatus(0);
            subjectsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return subjectsBean;
        }

        TeacherInstitute retrievedTeacherInstitute = teacherInstituteService.findByTeacher(retrievedTeacher);

        if (retrievedTeacherInstitute == null) {
            errorLogger.error(TAG + "Error in finding TeacherInstitute with teacherId : " + teacherId);

            subjectsBean.setStatus(0);
            subjectsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return subjectsBean;
        }

        Standard retrievedStandard = standardService.findById(standardId);

        if (retrievedStandard == null) {
            errorLogger.error(TAG + "Error in finding Standard with id : " + standardId);

            subjectsBean.setStatus(0);
            subjectsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return subjectsBean;
        }

        List<SubjectBean> subjectBeans = teacherSubjectRepository.findByTeacherAndStandard(retrievedTeacher, retrievedStandard);

        List<SubjectChapterBean> subjectChapterBeans = assignmentStandardDivisionService.getSubjectChaptersForStandard(retrievedTeacherInstitute.getInstitute(), retrievedStandard);

        if (!CollectionUtils.isEmpty(subjectChapterBeans)) {
            List<SubjectChapterBean> globalSubjectChapterBeans = assignmentService.getSubjectChaptersForStandard(retrievedTeacherInstitute.getInstitute(), retrievedStandard);

            if (globalSubjectChapterBeans != null) {
                for (SubjectChapterBean subjectChapterBean : globalSubjectChapterBeans) {
                    if (!subjectChapterBeans.contains(subjectChapterBean)) {
                        subjectChapterBeans.add(subjectChapterBean);
                    }
                }
            }
        } else {
            subjectChapterBeans = new ArrayList<>();

            subjectChapterBeans.addAll(assignmentService.getSubjectChaptersForStandard(retrievedTeacherInstitute.getInstitute(), retrievedStandard));
        }

        Map<String, List<SubjectChapterBean>> subjectChapterMap = new HashMap<>();

        for (SubjectChapterBean subjectChapterBean : subjectChapterBeans) {
            String key = subjectChapterBean.getSubjectId();

            if (subjectChapterMap.containsKey(key)) {
                List<SubjectChapterBean> retrievedSubjectChapterBeans = subjectChapterMap.get(key);
                retrievedSubjectChapterBeans.add(subjectChapterBean);
            } else {
                List<SubjectChapterBean> newSubjectChapterBeans = new ArrayList<>();
                newSubjectChapterBeans.add(subjectChapterBean);
                subjectChapterMap.put(key, newSubjectChapterBeans);
            }
        }

        for (SubjectBean subjectBean : subjectBeans) {
            List<SubjectChapterBean> retrievedSubjectChapterBeans = subjectChapterMap.get(subjectBean.getSubjectId());

            if (!CollectionUtils.isEmpty(retrievedSubjectChapterBeans)) {
                subjectBean.setSubjectChapterBeans(retrievedSubjectChapterBeans);
            } else {
                subjectBean.getSubjectChapterBeans().add(new SubjectChapterBean("No chapters available for " + subjectBean.getName() + " subject."));
            }
        }

        subjectsBean.setSubjectBeans(subjectBeans);
        subjectsBean.setStatus(1);
        return subjectsBean;
    }

}
