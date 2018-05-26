package com.sumzupp.backendapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by akash.mercer on 07-Jul-17.
 */
public class DateOperations {

    public static String getDateStringForDump(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy,HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String getDateStringInFrontEndFormatFromDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        return simpleDateFormat.format(date);
    }

    public static String getYesterdayDateStringInFrontEndFormatFromDate(Date date){
        Date newDate = new Date(date.getTime());
        newDate.setTime(date.getTime() - 24*60*60*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        return simpleDateFormat.format(newDate);
    }

    public static String getIstDateString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 5);
        calendar.add(Calendar.SECOND, 30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getTomorrowDateStringInFrontEndFormatFromDate(Date date){
        Date newDate = new Date(date.getTime());
        newDate.setTime(date.getTime() + 24*60*60*1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        return simpleDateFormat.format(newDate);
    }

    public static Date getTodayStartDate(){
        Calendar calendar = Calendar.getInstance();

        //Change Date on the basis of Server Time >= 18:30
        long seconds = calendar.get(Calendar.HOUR_OF_DAY)*60*60 + calendar.get(Calendar.MINUTE)*60;

        if(seconds >= Constants.SERVER_DATE_CHANGE_SECOND){
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
