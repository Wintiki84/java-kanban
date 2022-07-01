package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask {
    private final List<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void addSubTask(SubTask subTask) {
        this.subTasks.add(subTask);
    }

    public List<SubTask> getSubTask() {
        return subTasks;
    }
}
