package de.neuefische.todobackend.service;

import de.neuefische.todobackend.db.TaskDb;
import de.neuefische.todobackend.model.IncomingTask;
import de.neuefische.todobackend.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class TaskService {

    private TaskDb taskDb;

    @Autowired
    public TaskService(TaskDb taskDb) {
        this.taskDb = taskDb;
    }

    public ArrayList<Task> getTasks(){
        return taskDb.listTasks();
    }

    public Task addTask(IncomingTask incomingTask) {
        return taskDb.addTask(incomingTask.getDescription(), incomingTask.getStatus());
    }

    public Task updateTask(String id, Task task) {
        if(taskDb.updateTask(id, task).isPresent()){
            return taskDb.updateTask(id, task).get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public void deleteTask(String id){
        taskDb.deleteTask(id);
    }

}
