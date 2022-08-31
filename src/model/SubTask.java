package model;

import java.time.LocalDateTime;

public class SubTask extends AbstractTask {

    public SubTask(String name, String description, Status status, int epicId, LocalDateTime startTime, long duration) {
        super(TypeTask.SUB_TASK, name, description, status, startTime, duration);
        super.setEpicId(epicId);
    }
}
