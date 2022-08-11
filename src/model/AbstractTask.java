package model;

public class AbstractTask {
    private int id;
    private TypeTask task;
    private final String name;
    private final String description;
    private Status status;
    private int epicId;

    protected AbstractTask(TypeTask typeTask, String name, String description, Status status) {
        this.task = typeTask;
        this.name = name;
        this.description = description;
        this.status = status;
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

    public static AbstractTask fromString(String task) {
        String[] splitTask = task.split(",");
        AbstractTask abstractTask = new AbstractTask(TypeTask.valueOf(splitTask[1]), splitTask[2],
                splitTask[4], Status.valueOf(splitTask[3]));
        abstractTask.setId(Integer.parseInt (splitTask[0]));
        abstractTask.setEpicId(Integer.parseInt (splitTask[5]));
        return abstractTask;
    }

    @Override
    public String toString() {
        return (id + "," + task + "," + name + "," + status + "," + description + "," + epicId);
    }
}
