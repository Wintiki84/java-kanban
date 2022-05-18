package service;

import model.AbstractTask;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.Status.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final IdGenerator idGenerator = new IdGenerator();
    private final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Task createTask(Task task) {
        int taskId = idGenerator.getNewId();
        task.setId(taskId);
        tasks.put(taskId, task);
        return task;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        int taskId = idGenerator.getNewId();
        subTask.setId(taskId);
        Epic tempEpic = epics.get(subTask.getEpicId());
        if (null != tempEpic) {
            tempEpic.getSubTask().add(subTask);
            epics.put(subTask.getEpicId(), tempEpic);
        }
        subTasks.put(taskId, subTask);
        return subTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        int taskId = idGenerator.getNewId();
        epic.setId(taskId);
        epics.put(taskId, epic);
        return epic;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    @Override
    public void updateEpicStatus() {
        for (Epic epic : epics.values()) {
            int statusNew = 0;
            int statusInProgress = 0;
            int statusDone = 0;

            for (SubTask subTask : subTasks.values()) {
                if (subTask.getEpicId().equals(epic.getId())) {
                    switch (subTask.getStatus()) {
                        case NEW:
                            statusNew++;
                            break;
                        case IN_PROGRESS:
                            statusInProgress++;
                            break;
                        case DONE:
                            statusDone++;
                            break;
                    }
                }
            }
            if ((statusNew > 0) && (statusInProgress == 0) && (statusDone == 0)) {
                epic.setStatus(NEW);
                epics.put(epic.getId(), epic);
            } else if ((statusNew == 0) && (statusInProgress == 0)) {
                epic.setStatus(DONE);
                epics.put(epic.getId(), epic);
            } else {
                epic.setStatus(IN_PROGRESS);
                epics.put(epic.getId(), epic);
            }
        }
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void deleteSubTask(int taskId) {
        Epic tempEpic = epics.get(subTasks.get(taskId).getEpicId());
        tempEpic.getSubTask().remove(subTasks.get(taskId));
        epics.put(subTasks.get(taskId).getEpicId(), tempEpic);
        subTasks.remove(taskId);
    }

    @Override
    public void deleteEpic(int taskId) {
        for (SubTask subTask : epics.get(taskId).getSubTask()) {
            subTasks.remove(subTask.getId());
        }
        epics.remove(taskId);
    }

    @Override
    public Task getTask(int taskId) {
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public Epic getEpic(int taskId) {
        historyManager.add(epics.get(taskId));
        return epics.get(taskId);
    }

    @Override
    public SubTask getSubTask(int taskId) {
        historyManager.add(subTasks.get(taskId));
        return subTasks.get(taskId);
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    @Override
    public List<AbstractTask> getHistory() {
        return historyManager.getHistory();
    }
}
