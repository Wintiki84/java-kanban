package model;

import java.time.LocalDateTime;

public class Task extends AbstractTask {
    public Task(String name, String description, Status status, LocalDateTime startTime, long duration) {
        super(TypeTask.TASK, name, description, status, startTime, duration);
    }
}
