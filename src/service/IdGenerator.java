package service;

public class IdGenerator {
    private static int taskId;

    public static int getNewId() {
        taskId++;
        return taskId;
    }

    public static int getTaskId() {
        return taskId;
    }
}
