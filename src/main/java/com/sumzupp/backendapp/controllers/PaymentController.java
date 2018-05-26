package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.MerchantHashBean;
import com.sumzupp.backendapp.beans.MerchantHashGenerateBean;
import com.sumzupp.backendapp.beans.PaymentBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 29-Dec-17.
 */

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/getMerchantHash", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody MerchantHashBean getMerchantHash(@RequestBody MerchantHashGenerateBean merchantHashGenerateBean) {
        return paymentService.getMerchantHash(merchantHashGenerateBean.getHashGenerationString());
    }

    @RequestMapping(value = "/makePayment", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean makePayment(@RequestBody PaymentBean paymentBean) {
        return paymentService.makePayment(paymentBean);
    }
}
