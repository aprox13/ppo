package ru.ifkbhit.ppo4.model;


public class Task {

    private String task;
    private boolean done;
    private Long id;
    private Long taskListId;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(Long taskListId) {
        this.taskListId = taskListId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "task='" + task + '\'' +
                ", done=" + done +
                ", id=" + id +
                ", taskListId=" + taskListId +
                '}';
    }
}
