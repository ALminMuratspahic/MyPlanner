package com.almin.myplanner.entity;

public class Task {

    private int id;
    private String taskName;
    private String date;
    private boolean isActive;

    public Task(String taskName, String date, boolean isActive) {
        this.taskName = taskName;
        this.date = date;
        this.isActive = isActive;
    }

    public Task(int id, String taskName, String date, boolean isActive) {
        this.id = id;
        this.taskName = taskName;
        this.date = date;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", date='" + date + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
