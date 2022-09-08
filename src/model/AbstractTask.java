package model;

import java.time.LocalDateTime;

public class AbstractTask {
    protected final String name;
    protected final String description;
    protected final TypeTask task;
    protected int id;
    protected Status status;
    protected LocalDateTime startTime;
    protected long duration;

    protected AbstractTask(TypeTask typeTask, String name, String description, Status status,
                           LocalDateTime startTime, long duration) {
        this.task = typeTask;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public static AbstractTask fromString(String task) {
        String[] splitTask = task.split(",");
        if (!TypeTask.valueOf(splitTask[1]).equals(TypeTask.SUB_TASK)) {
            AbstractTask abstractTask = new AbstractTask(TypeTask.valueOf(splitTask[1]), splitTask[2],
                    splitTask[4], Status.valueOf(splitTask[3]), LocalDateTime.parse(splitTask[6]),
                    Long.parseLong(splitTask[7]));
            abstractTask.setId(Integer.parseInt(splitTask[0]));
            return abstractTask;
        } else {
            AbstractTask abstractTask = new AbstractTask(TypeTask.valueOf(splitTask[1]), splitTask[2],
                    splitTask[4], Status.valueOf(splitTask[3]), LocalDateTime.parse(splitTask[6]),
                    Long.parseLong(splitTask[7]));
            return abstractTask;
        }
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TypeTask getTypeTask() {
        return task;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
    }

    @Override
    public String toString() {
        return (id + "," + task + "," + name + "," + status + "," + description + ",," +
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
