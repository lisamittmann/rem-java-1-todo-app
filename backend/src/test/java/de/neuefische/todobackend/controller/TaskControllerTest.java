package de.neuefische.todobackend.controller;

import de.neuefische.todobackend.db.TaskDb;
import de.neuefische.todobackend.helper.UuidGenerator;
import de.neuefische.todobackend.model.IncomingTask;
import de.neuefische.todobackend.model.Task;
import de.neuefische.todobackend.model.TaskStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl() {
        return "http://localhost:" + port + "/api/todo";
    }

    @Autowired
    private TaskDb taskDb;

    @MockBean
    private UuidGenerator generator;


    @Test
    public void addShouldAddItemToDb(){
        // Given
        IncomingTask addItem = new IncomingTask("Drink matcha", TaskStatus.OPEN);
        when(generator.generateUuid()).thenReturn("123");

        // When
        ResponseEntity<Task> response = restTemplate.postForEntity(getUrl(), addItem, Task.class);


        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new Task("123", "Drink matcha", TaskStatus.OPEN)));

        assertThat(taskDb.listTasks(), contains(new Task("123", "Drink matcha", TaskStatus.OPEN)));

    }

    @Test
    public void getTasksShouldReturnTasks(){
        // When
        ResponseEntity<Task[]> response = restTemplate.getForEntity(getUrl(), Task[].class);


    }

}