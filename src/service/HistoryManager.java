package service;

import model.AbstractTask;

import java.util.List;

public interface HistoryManager {

    void add(AbstractTask task);

    void remove(int id);

    void deleteHistory();

    List<AbstractTask> getHistory();
}
