package ru.ifkbhit.ppo4.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class TaskList {

    @Id
    @NotNull
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @NotNull
    private String name;

    public long getId() {
        return id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", tasks=" + tasks +
                ", name='" + name + '\'' +
                '}';
    }
}
