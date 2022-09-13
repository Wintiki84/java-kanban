package service;

public final class Managers {
    private static final TaskManager taskManager = new InMemoryTaskManager();
    private static final HistoryManager historyManager = new InMemoryHistoryManager();
    private static final FileBackedTasksManager fileBackedTasksManager =
            new FileBackedTasksManager("tasksTest.csv");

    public static TaskManager getDefaultTaskManager() {
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return historyManager;
    }

    public static FileBackedTasksManager getDefaultFileBackedTasksManager() {
        return fileBackedTasksManager;
    }
}
