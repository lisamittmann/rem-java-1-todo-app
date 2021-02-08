package de.neuefische.todobackend.db;

import de.neuefische.todobackend.helper.UuidGenerator;
import de.neuefische.todobackend.model.Task;
import de.neuefische.todobackend.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TaskDbTest {

    private final UuidGenerator generatorMock = mock(UuidGenerator.class);


    @Test
    public void listTasksReturnsTask(){
        // Given
        TaskDb taskDb = generateTaskDb();
        ArrayList<Task> expected = new ArrayList<Task>(List.of(
                new Task("976f37f4-8aa7-481d-ad2c-2120613f3347", "Write tests", TaskStatus.OPEN),
                new Task("61084198-b1b7-4d7c-837c-62f458ce765a", "Drink Matcha", TaskStatus.IN_PROGRESS),
                new Task("4f5cf145-d5f7-430f-8e0e-048ea3c1fc68", "Fluff Olaf", TaskStatus.DONE)
        ));

        // When
        ArrayList<Task> tasks = taskDb.listTasks();

        // Then
        assertThat(tasks, is(expected));
    }

    @Test
    public void addTaskAddsAndReturnsTask(){
        //Given
        TaskDb taskDb = generateTaskDb();
        when(generatorMock.generateUuid()).thenReturn("123");

        // When
        Task testTask = taskDb.addTask("Test Task", TaskStatus.OPEN);

        // Then
        assertThat(taskDb.listTasks(), hasItem(new Task("123", "Test Task", TaskStatus.OPEN)));
    }

    @Test
    public void updateTaskChangesStatus(){
        //Given
        TaskDb taskDb = generateTaskDb();
        String id = "976f37f4-8aa7-481d-ad2c-2120613f3347";
        Task updateTask = new Task(id, "Write tests", TaskStatus.IN_PROGRESS);

        // When
        Task task = taskDb.updateTask(id, updateTask).get();

        // Then
        assertThat(task, is(updateTask));

    }

    @Test
    public void deleteTaskRemovesTasks(){
        // Given
        TaskDb taskDb = generateTaskDb();
        String idToDelete = "976f37f4-8aa7-481d-ad2c-2120613f3347";

        // When
        taskDb.deleteTask(idToDelete);

        // Then
        assertThat(taskDb.listTasks(), is(new ArrayList<Task>(List.of(
                new Task("61084198-b1b7-4d7c-837c-62f458ce765a", "Drink Matcha", TaskStatus.IN_PROGRESS),
                new Task("4f5cf145-d5f7-430f-8e0e-048ea3c1fc68", "Fluff Olaf", TaskStatus.DONE)
        ))));

    }

    @Test
    public void deleteTaskDeleteNonExistingIdThrowException(){
        // Given
        TaskDb taskDb = generateTaskDb();
        String idToDelete = "b7b07eb5-c360-47d3-98a9-c9aebc2f51ca";

        // When
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                taskDb.deleteTask(idToDelete));

        // Then
        assertThat(exception.getStatus(), is(HttpStatus.BAD_REQUEST));


    }

    private TaskDb generateTaskDb(){
        ArrayList<Task> taskList = new ArrayList<Task>(List.of(
                new Task("976f37f4-8aa7-481d-ad2c-2120613f3347", "Write tests", TaskStatus.OPEN),
                new Task("61084198-b1b7-4d7c-837c-62f458ce765a", "Drink Matcha", TaskStatus.IN_PROGRESS),
                new Task("4f5cf145-d5f7-430f-8e0e-048ea3c1fc68", "Fluff Olaf", TaskStatus.DONE)
        ));

        TaskDb taskDb = new TaskDb(generatorMock);
        taskDb.setTaskList(taskList);
        return taskDb;
    }

}