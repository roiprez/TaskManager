package com.example.roiprez.taskmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Roiprez on 15/04/2017.
 */

public class Task {
    private String authorUid;
    private String id;
    private String taskText;
    private Date taskDate;
    private int taskPriority;
    private String [] taskTags;

    public Task(String taskText, Date taskDate){
        this.taskText = taskText;
        this.taskDate = taskDate;
    }

    public Task(String taskText){
        this.taskText = taskText;
    }

    public Task(String authorUid, String taskText){
        this.taskText = taskText;
        this.authorUid = authorUid;
    }

    public String getAuthorUid(){
        return authorUid;
    }
    public Task(){
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Date getTaskDate(){
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public void setId(String string){
        this.id = string;
    }

    public String getId(){
        return this.id;
    }
}
