package model;

public class SubTask extends AbstractTask {

    public SubTask(String name, String description, Status status, int epicId) {
        super(TypeTask.SUB_TASK, name, description, status);
        super.setEpicId(epicId);
    }
}
