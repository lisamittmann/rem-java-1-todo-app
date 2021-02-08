package de.neuefische.todobackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {

    OPEN("OPEN"),
    IN_PROGRESS("IN PROGRESS"),
    DONE("DONE");

    public final String status;

    TaskStatus(String status) {
        this.status = status;
    }

}
