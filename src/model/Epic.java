package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask {
    private final List<SubTask> subTasks = new ArrayList<>();
    protected LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC);

    public Epic(String name, String description, Status status) {
        super(TypeTask.EPIC, name, description, status,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
    }


    public void addSubTask(SubTask subTask) {
        this.subTasks.add(subTask);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
