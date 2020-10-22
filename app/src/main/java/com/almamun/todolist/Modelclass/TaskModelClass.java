package com.almamun.todolist.Modelclass;

public class TaskModelClass {
    private int id;
    public boolean status;
    public String tasktext;

//    public TaskModelClass(int status, String tasktext) {
//        this.status = status;
//        this.tasktext = tasktext;
//    }

    public TaskModelClass(int id,boolean status,String tasktext) {
        this.status = status;
        this.id = id;
        this.tasktext = tasktext;
    }

    public TaskModelClass() {
    }

    @Override
    public String toString() {
        return "TaskModelClass{" +
                "status=" + status +
                ", id=" + id +
                ", tasktext='" + tasktext + '\'' +
                '}';
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTasktext() {
        return tasktext;
    }

    public void setTasktext(String tasktext) {
        this.tasktext = tasktext;
    }


}
