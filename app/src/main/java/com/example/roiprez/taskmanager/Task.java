package com.example.roiprez.taskmanager;

import java.util.Date;
import java.util.UUID;

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

    public Task(String authorUid, String taskText){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.taskText = taskText;
        this.authorUid = authorUid;
    }

    public String getAuthorUid(){
        return authorUid;
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
