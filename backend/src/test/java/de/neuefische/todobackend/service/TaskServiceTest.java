package de.neuefische.todobackend.service;

import de.neuefische.todobackend.db.TaskDb;
import de.neuefische.todobackend.model.IncomingTask;
import de.neuefische.todobackend.model.Task;
import de.neuefische.todobackend.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private final TaskDb taskDbMock = mock(TaskDb.class);
    private final TaskService taskService = new TaskService(taskDbMock);

    @Test
    public void getTasksReturnsTasks(){
        // Given
        when(taskDbMock.listTasks()).thenReturn(new ArrayList<Task>(List.of(
                new Task("976f37f4-8aa7-481d-ad2c-2120613f3347", "Write tests", TaskStatus.OPEN)
        )));

        // When
        ArrayList<Task> tasks = taskService.getTasks();

        // Then
        assertThat(tasks, contains(new Task("976f37f4-8aa7-481d-ad2c-2120613f3347", "Write tests", TaskStatus.OPEN)));
        verify(taskDbMock).listTasks();
    }

    @Test
    public void addTaskAddsAndReturnsTask(){
        // Given
        IncomingTask mockTask = new IncomingTask("Write tests", TaskStatus.OPEN);
        when(taskService.addTask(mockTask)).thenReturn(new Task("976f37f4-8aa7-481d-ad2c-2120613f3347", "Write tests", TaskStatus.OPEN));

        // When
        Task addTask = taskService.addTask(mockTask);

        // Then
        assertThat(addTask, is(new Task("976f37f4-8aa7-481d-ad2c-2120613f3347", "Write tests", TaskStatus.OPEN)));
        verify(taskDbMock).addTask(mockTask.getDescription(), mockTask.getStatus());

    }

    @Test
    public void updateTaskReturnsNewTask(){
        // Given
        String id = "976f37f4-8aa7-481d-ad2c-2120613f3347";
        Task updateTask = new Task(id, "Write tests", TaskStatus.IN_PROGRESS);
        when(taskDbMock.updateTask(id, updateTask)).thenReturn(Optional.of(new Task(id, "Write tests", TaskStatus.IN_PROGRESS)));

        // When
        Task task = taskService.updateTask(id, updateTask);

        // Then
        assertThat(task, is(updateTask));
        verify(taskDbMock, times(2)).updateTask(id, updateTask);
    }

    @Test
    public void deleteTasksRemovesTask(){
        // Given
        String id = "3e4df21b-d559-469c-bd38-d7e9b3846ad9";

        // When
        taskService.deleteTask(id);

        // Then
        verify(taskDbMock).deleteTask(id);

    }

}