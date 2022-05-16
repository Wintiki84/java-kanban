package service;

import model.AbstractTask;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.HashMap;
import java.util.Map;

import static service.Status.*;

public class ManagerTask {
    private int taskID = 0;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, SubTask> subTasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    private IdGenerator idGenerator = new IdGenerator();

    public int getTaskID() {
        return taskID;
    }

    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public Task createTask(Task task) {
        int taskId = idGenerator.getNewId();
        task.setId(taskId);
        tasks.put(taskId, task);
        return task;
    }

    public SubTask createSubTask(SubTask subTask) {
        int taskId = idGenerator.getNewId();
        subTask.setId(taskId);
        Epic tempEpic = epics.get(subTask.getEpicId());//Добовляем ИД поздадачи в эпик
        tempEpic.getSubTask().add(subTask);
        epics.put(subTask.getEpicId(), tempEpic);
        subTasks.put(taskId, subTask);
        return subTask;
    }

    public Epic createEpic(Epic epic) {
        int taskId = idGenerator.getNewId();
        epic.setId(taskId);
        epics.put(taskId, epic);
        return epic;
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    // метод для изменения статуса эпик
    public void updateEpicStatus() {
        for (Epic epic : epics.values()) {
            int statusNew = 0;
            int statusInProgress = 0;
            int statusDone = 0;

            for (SubTask subTask : subTasks.values()) {
                if (subTask.getEpicId() == epic.getId()) {
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
            if ((statusNew > 0) & (statusInProgress == 0) & (statusDone == 0)) {
                epic.setStatus(NEW);
                epics.put(epic.getId(), epic);
            } else if ((statusNew == 0) & (statusInProgress == 0)) {
                epic.setStatus(DONE);
                epics.put(epic.getId(), epic);
            } else {
                epic.setStatus(IN_PROGRESS);
                epics.put(epic.getId(), epic);
            }
        }
    }

    public void deleteTask(int taskId) {
        tasks.remove(taskId);
    }

    public void deleteSubTask(int taskId) {
        Epic tempEpic = epics.get(subTasks.get(taskId).getEpicId());
        tempEpic.getSubTask().remove(subTasks.get(taskId));
        epics.put(subTasks.get(taskId).getEpicId(), tempEpic);
        subTasks.remove(taskId);
    }

    public void deleteEpic(int taskId) {
        for (SubTask subTask : epics.get(taskId).getSubTask()) {
            subTasks.remove(subTask.getId());
        }
        epics.remove(taskId);
    }

    public AbstractTask getTask(int taskId, TypeTask typeTask) {
        switch (typeTask) {
            case TASK:
                return tasks.get(taskId);
            case EPIC:
                return epics.get(taskId);
            case SUB_TASK:
                return subTasks.get(taskId);
            default:
                throw new IllegalStateException("Unexpected value: " + typeTask);
        }
    }

    public void deleteAllTask() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

}
