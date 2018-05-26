package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.PaymentType;
import com.sumzupp.backendapp.repositories.PaymentTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 02-Jan-18.
 */

@Service
public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Integer getCount() {
        return paymentTypeRepository.getCount();
    }

    public PaymentType findByType(Integer type) {
        return paymentTypeRepository.findByType(type);
    }
}
