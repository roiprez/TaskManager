package com.example.roiprez.taskmanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Roiprez on 15/04/2017.
 */

public class Task {
    private String authorUid;
    private String id;
    private String taskText;
    private String taskDate;
    private int taskPriority;
    private String[] taskTags;

    public Task(String authorUid, String taskText) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.taskText = taskText;
        this.authorUid = authorUid;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        taskDate = dateFormat.format(date); //2016/11/16 12:08:43
    }

    public Task() {
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public String getTaskText() {
        return taskText;
    }

    //public void setTaskText(String taskText) {this.taskText = taskText;}

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public void setId(String string) {
        this.id = string;
    }

    public String getId() {
        return this.id;
    }
}
