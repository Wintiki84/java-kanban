package service;

public class IdGenerator {
    private int taskID;

    public int getNewId(){
        this.taskID++;
        return taskID;
    }
}
