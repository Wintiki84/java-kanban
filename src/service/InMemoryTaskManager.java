package service;

import model.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static model.Status.*;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    protected final TreeSet<AbstractTask> prioritizedTasks = new TreeSet<>(new DataComparatorTask());
    protected final List<AbstractTask> prioritizedListTasks = new ArrayList<>();

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
        task.setId(IdGenerator.getNewId());
        tasks.put(task.getId(), task);
        setPrioritizedTasks();
        return task;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(IdGenerator.getNewId());
        Epic tempEpic = epics.get(subTask.getEpicId());
        if (null != tempEpic) {
            tempEpic.getSubTasks().add(subTask);
            epics.put(subTask.getEpicId(), tempEpic);
        }
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus();
        updateEpicTime();
        setPrioritizedTasks();
        return subTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(IdGenerator.getNewId());
        epics.put(epic.getId(), epic);
        setPrioritizedTasks();
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
        updateEpicStatus();
        updateEpicTime();
    }

    protected void updateEpicStatus() {
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
        try {
            tasks.remove(taskId);
            setPrioritizedTasks();
            historyManager.remove(taskId);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer: " + e.getMessage());
        }
    }

    @Override
    public void deleteSubTask(int taskId) {
        try {
            Epic tempEpic = epics.get(subTasks.get(taskId).getEpicId());
            tempEpic.getSubTasks().remove(subTasks.get(taskId));
            updateEpicStatus();
            updateEpicTime();
            epics.put(subTasks.get(taskId).getEpicId(), tempEpic);
            subTasks.remove(taskId);
            setPrioritizedTasks();
            historyManager.remove(taskId);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer: " + e.getMessage());
        }
    }

    @Override
    public void deleteEpic(int taskId) {
        try {
            for (SubTask subTask : epics.get(taskId).getSubTasks()) {
                subTasks.remove(subTask.getId());
            }
            epics.remove(taskId);
            setPrioritizedTasks();
            historyManager.remove(taskId);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer: " + e.getMessage());
        }
    }

    @Override
    public Task getTask(int taskId) {
        try {
            historyManager.add(tasks.get(taskId));
            return tasks.get(taskId);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Epic getEpic(int taskId) {
        try {
            historyManager.add(epics.get(taskId));
            return epics.get(taskId);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer: " + e.getMessage());
            return null;
        }
    }

    @Override
    public SubTask getSubTask(int taskId) {
        try {
            historyManager.add(subTasks.get(taskId));
            return subTasks.get(taskId);
        } catch (NullPointerException e) {
            System.out.println("Null Pointer: " + e.getMessage());
            return null;
        }
    }

    protected void setTask(int id, Task task) {
        tasks.put(id, task);
    }

    protected void setEpic(int id, Epic epic) {
        epics.put(id, epic);
    }

    protected void setSubTask(int id, SubTask subTask) {
        subTasks.put(id, subTask);
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        setPrioritizedTasks();
    }

    @Override
    public List<AbstractTask> getHistory() {
        return historyManager.getHistory();
    }

    protected void updateEpicTime() {
        for (Epic epic : epics.values()) {
            LocalDateTime startTime = epic.getStartTime();
            long duration = 0;
            for (SubTask subTask : subTasks.values()) {
                if (subTask.getEpicId().equals(epic.getId())) {
                    if (startTime.equals(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC))) {
                        startTime = subTask.getStartTime();
                    }
                    if (subTask.getStartTime().isBefore(startTime)) {
                        startTime = subTask.getStartTime();
                    }
                    duration = duration + subTask.getDuration();
                }
            }
            epic.setStartTime(startTime);
            epic.setDuration(duration);
            epics.put(epic.getId(), epic);
        }
    }


    public List<AbstractTask> getPrioritizedTasks() {
        return prioritizedListTasks;
    }


    protected void setPrioritizedTasks() {
        prioritizedTasks.clear();
        prioritizedListTasks.clear();
        for (SubTask subTask : subTasks.values())
            if (!subTask.getStartTime().equals(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC))) {
                prioritizedTasks.add(subTask);
            }

        for (Task task : tasks.values())
            if (!task.getStartTime().equals(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC))) {
                prioritizedTasks.add(task);
            }

        prioritizedListTasks.addAll(prioritizedTasks);
        for (SubTask subTask : subTasks.values())
            if (subTask.getStartTime().equals(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC))){
                prioritizedListTasks.add(subTask);
            }
        for (Task task : tasks.values())
            if (task.getStartTime().equals(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC))) {
                prioritizedListTasks.add(task);
            }
        validationTasks();
    }

    protected void validationTasks() {
        LocalDateTime localDT = LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC);
        for (AbstractTask task : prioritizedTasks) {
            if (task.getEndTime().isBefore(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC))) {
                task.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC));
            }
            if (!task.getStartTime().isAfter(localDT)
                    & !task.getEndTime().equals(LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC))
                    & !task.getTypeTask().equals(TypeTask.EPIC)) {
                task.setStartTime(localDT.plusMinutes(1));
            }
            localDT = task.getEndTime();
        }
        updateEpicTime();
    }
}
