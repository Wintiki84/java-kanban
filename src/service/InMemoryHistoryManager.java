package service;

import model.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_SIZE = 10;

    private final List<AbstractTask> history = new ArrayList<>();

    @Override
    public void add(AbstractTask task) {
        if (history.size() == HISTORY_SIZE) {
            this.history.remove(0);
        }
        this.history.add(task);
    }

    @Override
    public List<AbstractTask> getHistory() {
        return history;
    }
}
