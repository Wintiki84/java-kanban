package model;

import java.time.LocalDateTime;

public class SubTask extends AbstractTask {
    protected int epicId;

    public SubTask(String name, String description, Status status, int epicId, LocalDateTime startTime, long duration) {
        super(TypeTask.SUB_TASK, name, description, status, startTime, duration);
        setEpicId(epicId);
    }

    public static SubTask fromStringSubTask(String task) {
        String[] splitTask = task.split(",");
        SubTask subTask = new SubTask(splitTask[2],
                splitTask[4], Status.valueOf(splitTask[3]), Integer.parseInt(splitTask[5]), LocalDateTime.parse(splitTask[6]),
                Long.parseLong(splitTask[7]));
        subTask.setId(Integer.parseInt(splitTask[0]));
        subTask.setEpicId(Integer.parseInt(splitTask[5]));
        return subTask;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer Id) {
        epicId = Id;
    }

    @Override
    public String toString() {
        return (id + "," + task + "," + name + "," + status + "," + description + "," + epicId + "," +
                startTime + "," + duration);
    }
}
