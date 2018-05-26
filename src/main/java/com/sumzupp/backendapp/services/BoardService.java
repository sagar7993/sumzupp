package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.BoardBean;
import com.sumzupp.backendapp.entities.Board;
import com.sumzupp.backendapp.repositories.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Service
public class BoardService {
    private static final String TAG = "BoardService : ";

    @Autowired
    private BoardRepository boardRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public Integer getCount() {
        return boardRepository.getCount();
    }

    public Board findByType(Integer type) {
        return boardRepository.findByType(type);
    }

    public Board findById(String boardId) {
        return boardRepository.findOne(boardId);
    }

    public List<Board> findByIds(List<String> boardIds) {
        return boardRepository.findByIds(boardIds);
    }

    public List<BoardBean> fetchAllActiveBoardBeans() {
        return boardRepository.fetchAllActiveBoardBeans();
    }
}
