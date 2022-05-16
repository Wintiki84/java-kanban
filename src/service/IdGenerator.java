package service;

public class IdGenerator {
    private int taskId;

    public int getNewId(){
        this.taskId++;
        return taskId;
    }
}
