package de.neuefische.todobackend.db;

import de.neuefische.todobackend.helper.UuidGenerator;
import de.neuefische.todobackend.model.Task;
import de.neuefische.todobackend.model.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public class TaskDb {

    private ArrayList<Task> taskList = new ArrayList<>();
    private final UuidGenerator generator;

    @Autowired
    public TaskDb(UuidGenerator generator) {
        this.generator = generator;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<Task> listTasks() {
        return this.taskList;
    }

    public Task addTask(String description, TaskStatus status) {
        Task task = new Task(generator.generateUuid(), description, status);
        taskList.add(task);
        return task;
    }

    public Optional<Task> updateTask(String id, Task updateTask) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                task.setStatus(updateTask.getStatus());
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }

    public void deleteTask(String id) {
        Optional<Task> task = containsTask(id);
        if (task.isPresent()) {
            taskList.remove(task.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Optional<Task> containsTask(String id) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                return Optional.of(task);
            }
        }
        return Optional.empty();
    }


}
