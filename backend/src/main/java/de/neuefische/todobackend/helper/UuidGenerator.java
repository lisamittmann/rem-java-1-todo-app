package de.neuefische.todobackend.helper;

import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public class UuidGenerator {

    public String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
