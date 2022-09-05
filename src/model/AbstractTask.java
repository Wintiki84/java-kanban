package model;

import java.time.LocalDateTime;

public class AbstractTask {
    private int id;
    private TypeTask task;
    private final String name;
    private final String description;
    private Status status;
    private int epicId;
    private LocalDateTime startTime;
    private long duration;

    protected AbstractTask(TypeTask typeTask, String name, String description, Status status,
                           LocalDateTime startTime, long duration) {
        this.task = typeTask;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public TypeTask getTypeTask() {
        return task;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    public static AbstractTask fromString(String task) {
        String[] splitTask = task.split(",");
        AbstractTask abstractTask = new AbstractTask(TypeTask.valueOf(splitTask[1]), splitTask[2],
                splitTask[4], Status.valueOf(splitTask[3]), LocalDateTime.parse(splitTask[6]),
                Integer.parseInt(splitTask[7]));
        abstractTask.setId(Integer.parseInt(splitTask[0]));
        abstractTask.setEpicId(Integer.parseInt(splitTask[5]));
        return abstractTask;
    }

    @Override
    public String toString() {
        return (id + "," + task + "," + name + "," + status + "," + description + "," + epicId + "," +
                startTime + "," + duration);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AbstractTask task = (AbstractTask) obj;
        return (this.id == task.id
                && (this.task == task.getTypeTask())
                && (this.description.equals(task.getDescription()))
                && (this.status == task.getStatus())
                && (this.startTime.equals(task.getStartTime()))
                && (this.duration == task.getDuration())
        );
    }

}
