public abstract class AbstractTask {
    private String name;
    private String description;
    private Integer id;
    private Status status;

    public AbstractTask(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
