package service;

public class Managers {
    TaskManager taskManager = new InMemoryTaskManager();
    HistoryManager historyManager = new InMemoryHistoryManager();

    public TaskManager getDefault() {
        return taskManager;
    }

    public HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
