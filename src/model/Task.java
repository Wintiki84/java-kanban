package model;

import service.Status;

public class Task extends AbstractTask {
    public Task(String name, String description, Status status) {
        super(name, description, status);
    }

}
