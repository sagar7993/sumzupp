package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.MerchantHashBean;
import com.sumzupp.backendapp.beans.NotificationBean;
import com.sumzupp.backendapp.beans.PaymentBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.entities.Payment;
import com.sumzupp.backendapp.entities.PaymentType;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.repositories.PaymentRepository;
import com.sumzupp.backendapp.utils.Constants;
import com.sumzupp.backendapp.utils.DateOperations;
import com.sumzupp.backendapp.utils.NotificationManager;
import com.sumzupp.backendapp.utils.PaymentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 29-Dec-17.
 */

@Service
public class PaymentService {
    private static final String TAG = "PaymentService : ";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private UserService userService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public StatusBean makePayment(PaymentBean paymentBean) {
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userService.findById(paymentBean.getUserId());

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + paymentBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        if (getCountByUser(paymentBean.getUserId()) == 0) {
            Payment payment = new Payment();
            payment.setPaymentId(paymentBean.getPaymentId());
            payment.setTxnId(paymentBean.getTxnId());
            payment.setAmount(paymentBean.getAmount());
            payment.setPaymentDate(DateOperations.getTodayStartDate().getTime());
            payment.setUser(retrievedUser);

            PaymentType retrievedPaymentType = paymentTypeService.findByType(1);
            payment.setPaymentType(retrievedPaymentType);

            try {
                paymentRepository.save(payment);

                sendPaymentSuccessfulNotification(paymentBean.getAmount(), retrievedUser.getFcmToken());
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in creating Payment for User : " + paymentBean.getUserId() + " with error : " + e.getMessage());

                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                return statusBean;
            }
        } else {
            debugLogger.error(TAG + "Payment already processed for User : " + paymentBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.PAYMENT_ALREADY_PROCESSED);

            return statusBean;
        }

        statusBean.setStatus(1);
        return statusBean;
    }

    public MerchantHashBean getMerchantHash(String hashString) {
        MerchantHashBean merchantHashBean = new MerchantHashBean();

        StringBuilder hash = new StringBuilder(hashString).append(PaymentUtils.SALT);

        StringBuilder hexString = new StringBuilder();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.reset();
            messageDigest.update(hash.toString().getBytes());

            byte[] mdBytes = messageDigest.digest();

            for (byte aMessageDigest : mdBytes) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);

                if (hex.length() == 1) {
                    hexString.append("0");
                }

                hexString.append(hex);
            }

            merchantHashBean.setStatus(1);
            merchantHashBean.setMerchantHash(hexString.toString());
        } catch (NoSuchAlgorithmException e) {
            errorLogger.error(TAG + "Error in generating PayUMoney Merchant hash with error : " + e.getMessage());

            merchantHashBean.setStatus(0);
            merchantHashBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return merchantHashBean;
    }

    public Integer getCountByUser(String userId) {
        return paymentRepository.getCountByUser(userId);
    }

    public void sendPaymentSuccessfulNotification(Integer amount, String fcmToken) {
        NotificationBean notificationBean = new NotificationBean();
        notificationBean.setTitle("Payment successful!!");
        notificationBean.setShortDescription("Payment of Rs." + amount + " has been successful.");
        notificationBean.setLongDescription("Hurrah! Payment of Rs." + amount + " has been successfully completed. You can now access all the assignments available on sumzupp.");
        notificationBean.setNotificationType(2);
        notificationBean.setReceiverId(fcmToken);

        List<NotificationBean> notificationBeans = new ArrayList<>(1);
        notificationBeans.add(notificationBean);

        NotificationManager.sendNotification(notificationBeans);
    }
}
