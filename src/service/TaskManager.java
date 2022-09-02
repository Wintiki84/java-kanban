package service;

import model.AbstractTask;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public interface TaskManager {

    Map<Integer, Task> getTasks();

    Map<Integer, SubTask> getSubTasks();

    Map<Integer, Epic> getEpics();

    Task createTask(Task task);

    SubTask createSubTask(SubTask subTask);

    Epic createEpic(Epic epic);

    void updateTask(Task task);

    void updateTask(Epic epic);

    void updateTask(SubTask subTask);

    void deleteTask(int taskId);

    void deleteSubTask(int taskId);

    void deleteEpic(int taskId);

    Task getTask(int taskId);

    Epic getEpic(int taskId);

    SubTask getSubTask(int taskId);

    void deleteAllTask();

    List<AbstractTask> getHistory();

    List<AbstractTask> getPrioritizedTasks();
}
