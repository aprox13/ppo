package ru.ifkbhit.ppo4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ifkbhit.ppo4.dao.TaskDao;
import ru.ifkbhit.ppo4.model.Task;
import ru.ifkbhit.ppo4.model.TaskList;
import ru.ifkbhit.ppo4.repo.TaskListRepository;
import ru.ifkbhit.ppo4.repo.TaskRepository;

@Controller
public class TasksController {

    @Autowired
    TaskListRepository taskListRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/lists")
    @ResponseBody
    public Iterable<TaskList> tasks(Model model) {
        return taskListRepository.findAll();
    }

    @GetMapping("/tasks")
    @ResponseBody
    public Iterable<Task> lists(Model model) {
        return taskRepository.findAll();
    }

    @GetMapping("/list/new")
    @ResponseBody
    public void newList(Model model) {
        TaskList tl = new TaskList();

        tl.setName(String.valueOf(System.currentTimeMillis()));
        taskListRepository.save(tl);
    }

    @GetMapping("/task/new")
    @ResponseBody
    public void newTask(@ModelAttribute("tl") Long listId, Model model) {
        Task t = new Task();

        t.setTask("task for " + listId);
        TaskList tl = taskListRepository.findById(listId).orElse(null);


        t.setTaskList(tl);
        taskRepository.save(t);
    }


//    @PatchMapping("/api/task/done")
//    public void setDone(@RequestBody Task task) {
//        taskDao.setDone(task);
//    }
//
//    @DeleteMapping("/api/task/delete")
//    @ResponseBody
//    public void deleteTask(@RequestBody Task task) {
//        taskDao.deleteTask(task.getId());
//    }
//
//
//    @DeleteMapping("/api/list/delete")
//    @ResponseBody
//    public void deleteList(@RequestBody TaskList list) {
//        taskDao.deleteTaskList(list.getId());
//    }
//
//    @PostMapping("/task")
//    public String addTask(@RequestBody Task task) {
//        taskDao.addTask(task);
//
//        return "redirect:/tasks";
//    }
//
//    @PostMapping("/list")
//    public String addList(@ModelAttribute TaskList newList, BindingResult errors, Model model) {
//        taskDao.addList(newList);
//
//        return "redirect:/tasks";
//    }
}
