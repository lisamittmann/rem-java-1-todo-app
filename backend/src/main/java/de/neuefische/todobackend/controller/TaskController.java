package de.neuefische.todobackend.controller;

import ch.qos.logback.core.util.COWArrayList;
import de.neuefische.todobackend.db.TaskDb;
import de.neuefische.todobackend.model.IncomingTask;
import de.neuefische.todobackend.model.Task;
import de.neuefische.todobackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/todo")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ArrayList<Task> getTasks(){
        return taskService.getTasks();
    }

    @PostMapping
    public Task addTask(@RequestBody IncomingTask incomingTask) {
        return taskService.addTask(incomingTask);
    }

    @PutMapping("/{id}")
    public Task updateStatus(@PathVariable String id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id){
        taskService.deleteTask(id);
    }

}
