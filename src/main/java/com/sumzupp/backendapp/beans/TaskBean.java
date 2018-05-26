package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class TaskBean {

    private String taskId;

    private String taskTitle;

    private String taskDescription;

    private Long reminderTime;

    public TaskBean() {

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Long getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Long reminderTime) {
        this.reminderTime = reminderTime;
    }
}
