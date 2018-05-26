package com.sumzupp.backendapp.utils;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.sumzupp.backendapp.beans.NotificationBean;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class NotificationManager {

    public static final int RETRY_COUNT = 3;

    public static void sendNotification(List<NotificationBean> notificationBeans){
        for (int i = 0; i < notificationBeans.size(); i++) {
            NotificationBean notificationBean = notificationBeans.get(i);

            Sender sender = new Sender(Constants.FCM_KEY);

            HashMap<String, String> dataMap = new HashMap<>();
            dataMap.put("title", notificationBean.getTitle());
            dataMap.put("shortDescription", notificationBean.getShortDescription());

            if (!StringUtils.isEmpty(notificationBean.getLongDescription())) {
                dataMap.put("longDescription", notificationBean.getLongDescription());
            }

            if (!StringUtils.isEmpty(notificationBean.getNotificationImageUrl())) {
                dataMap.put("notificationImageUrl", notificationBean.getNotificationImageUrl());
            }

            dataMap.put("promoUrl", String.valueOf(notificationBean.getPromoUrl()));
            dataMap.put("notificationType", String.valueOf(notificationBean.getNotificationType()));

            Message.Builder builder = new Message.Builder();
            builder.setData(dataMap);

            Message message = builder.build();

            if (!StringUtils.isEmpty(notificationBean.getReceiverId())) {
                try {
                    sender.send(message, notificationBean.getReceiverId(), RETRY_COUNT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendBroadcastNotification(NotificationBean notificationBean, List<String> receivers){
        Sender sender = new Sender(Constants.FCM_KEY);

        Message.Builder builder = new Message.Builder();

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("title", notificationBean.getTitle());
        dataMap.put("shortDescription", notificationBean.getShortDescription());

        if (!StringUtils.isEmpty(notificationBean.getLongDescription())) {
            dataMap.put("longDescription", notificationBean.getLongDescription());
        }

        if (!StringUtils.isEmpty(notificationBean.getNotificationImageUrl())) {
            dataMap.put("notificationImageUrl", notificationBean.getNotificationImageUrl());
        }

        dataMap.put("promoUrl", String.valueOf(notificationBean.getPromoUrl()));
        dataMap.put("notificationType", String.valueOf(notificationBean.getNotificationType()));

        builder.setData(dataMap);

        Message message = builder.build();

        try {
            sender.send(message, receivers, RETRY_COUNT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}