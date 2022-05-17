package service;

import model.AbstractTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<AbstractTask> history = new ArrayList<>();

    @Override
    public void add(AbstractTask task) {
        if (history.size() == 10) {
            this.history.remove(1);
        }
        this.history.add(task);
    }

    @Override
    public List<AbstractTask> getHistory() {
        return history;
    }
}
