package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.TagType;
import com.sumzupp.backendapp.repositories.TagTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Service
public class TagTypeService {
    private static final String TAG = "TagTypeService : ";

    @Autowired
    private TagTypeRepository tagTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Integer getCount() {
        return tagTypeRepository.getCount();
    }

    public TagType findByType(Integer type) {
        return tagTypeRepository.findByType(type);
    }
}
