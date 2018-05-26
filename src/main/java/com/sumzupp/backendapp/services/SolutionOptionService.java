package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.SolutionOptionBean;
import com.sumzupp.backendapp.entities.Question;
import com.sumzupp.backendapp.entities.SolutionOption;
import com.sumzupp.backendapp.repositories.SolutionOptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Service
public class SolutionOptionService {
    private static final String TAG = "SolutionOptionService : ";

    @Autowired
    private SolutionOptionRepository solutionOptionRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public List<SolutionOptionBean> findByQuestionIds(List<String> questionIds) {
        return solutionOptionRepository.findByQuestionIds(questionIds);
    }

    public List<SolutionOptionBean> findByQuestionIdsWithSolution(List<String> questionIds) {
        return solutionOptionRepository.findByQuestionIdsWithSolution(questionIds);
    }

    public void saveAll(List<SolutionOption> solutionOptions) {
        solutionOptionRepository.save(solutionOptions);
    }

    public List<SolutionOption> findByQuestion(Question question) {
        return solutionOptionRepository.findByQuestion(question);
    }

    public List<SolutionOptionBean> findByQuestionId(String questionId) {
        return solutionOptionRepository.findByQuestionId(questionId);
    }

    public SolutionOption findById(String solutionOptionId) {
        return solutionOptionRepository.findOne(solutionOptionId);
    }
}
