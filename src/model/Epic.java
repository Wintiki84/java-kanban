package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask {
    private List<SubTask> subTask = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void setSubTask(SubTask subTask) {
        this.subTask.add(subTask);
    }

    public List<SubTask> getSubTask() {
        return subTask;
    }
}
