package ru.ifkbhit.ppo4.model;

import com.sun.istack.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NonNull
    private long id;

    @NonNull
    private String task;

    @NonNull
    private boolean done;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Long taskListId;

    public long getId() {
        return id;
    }

    @NonNull
    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return done;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTask(@NonNull String task) {
        this.task = task;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }
}
