package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.StandardBean;
import com.sumzupp.backendapp.entities.*;
import com.sumzupp.backendapp.repositories.StandardDivisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Service
public class StandardDivisionService {
    private static final String TAG = "StandardDivisionService : ";

    @Autowired
    private StandardDivisionRepository standardDivisionRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private TeacherSubjectService teacherSubjectService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public StandardDivision findById(String id) {
        return standardDivisionRepository.findOne(id);
    }

    public StandardDivision findByStandardAndBoardAndInstitute(String standardId, String boardId) {
        // TODO: 10-Sep-17 Below Institute Id is hard-coded on purpose
        return standardDivisionRepository.findByStandardAndBoardAndInstitute(standardId, boardId, "7");
    }

    public List<StandardDivision> findByInstitute(String instituteId) {
        User retrievedInstitute = userService.findById(instituteId);

        if (retrievedInstitute == null) {
            errorLogger.error(TAG + "Error in finding Institute with id : " + instituteId);
            return null;
        }

        return standardDivisionRepository.findByInstitute(retrievedInstitute);
    }

    public List<StandardDivision> findByInstituteAndStandard(User institute, Standard standard) {
        return standardDivisionRepository.findByInstituteAndStandard(institute, standard);
    }

    public List<StandardDivision> findByTeacher(String teacherId) {
        User retrievedTeacher = userService.findById(teacherId);

        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + teacherId);
            return null;
        }

        List<TeacherSubject> teacherSubjectList = teacherSubjectService.findTeacherSubjectByTeacher(retrievedTeacher);

        List<StandardDivision> standardDivisionList = new ArrayList<>();

        for (TeacherSubject teacherSubject: teacherSubjectList) {
            if(!standardDivisionList.contains(teacherSubject.getStandardDivision())) {
                standardDivisionList.add(teacherSubject.getStandardDivision());
            }
        }

        return standardDivisionList;
    }

    public List<StandardDivision> findByIds(List<String> standardIds) {
        return standardDivisionRepository.findByIds(standardIds);
    }

    public List<StandardDivision> findByStandard(Standard standard) {
        return standardDivisionRepository.findByStandard(standard);
    }

    public void saveStandardDivisions(User institute, List<StandardBean> standardBeans) throws Exception {
        List<StandardDivision> standardDivisions = new ArrayList<>();

        for (StandardBean standardBean : standardBeans) {
            Standard retrievedStandard = standardService.findById(standardBean.getStandardId());

            List<Board> boards = boardService.findByIds(standardBean.getBoardIds());

            for (Board board : boards) {
                for (String divisionName : standardBean.getDivisionNames()) {
                    standardDivisions.add(new StandardDivision(divisionName, institute, retrievedStandard, board));
                }
            }
        }

        standardDivisionRepository.save(standardDivisions);
    }

    public List<StandardBean> findStandardDivisionsByInstitute(String instituteId) {
        return standardDivisionRepository.findStandardBeansByInstitute(instituteId);
    }
}
