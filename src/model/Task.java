package model;

public class Task extends AbstractTask {
    public Task(String name, String description, Status status) {
        super(TypeTask.TASK, name, description, status);
    }
}
