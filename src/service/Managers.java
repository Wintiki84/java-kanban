package service;

public class Managers {
    private final TaskManager taskManager = new InMemoryTaskManager();
    private final HistoryManager historyManager = new InMemoryHistoryManager();

    public TaskManager getDefault() {
        return taskManager;
    }

    public HistoryManager getDefaultHistory() {
        return historyManager;
    }
}
