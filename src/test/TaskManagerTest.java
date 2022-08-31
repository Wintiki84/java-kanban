package test;

import model.*;
import org.junit.jupiter.api.Test;
import service.DataComparatorTask;
import service.IdGenerator;
import service.InMemoryHistoryManager;
import service.TaskManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static model.Status.*;

abstract class TaskManagerTest<T extends TaskManager>{
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, SubTask> subTasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    protected final TreeSet<AbstractTask> prioritizedTasks = new TreeSet<>(new DataComparatorTask());
    protected final List<AbstractTask> prioritizedListTasks = new ArrayList<>();

    @Test
    Void getTasks(){

    };

    Map<Integer, SubTask> getSubTasks();

    Map<Integer, Epic> getEpics();

    Task createTask(Task task);

    SubTask createSubTask(SubTask subTask);

    Epic createEpic(Epic epic);

    void updateTask(Task task);

    void updateTask(Epic epic);

    void updateTask(SubTask subTask);

    void updateEpicStatus();

    void deleteTask(int taskId);

    void deleteSubTask(int taskId);

    void deleteEpic(int taskId);

    Task getTask(int taskId);

    Epic getEpic(int taskId);

    SubTask getSubTask(int taskId);

    void setTask(int id, Task task);

    void setEpic(int id, Epic epic);

    void setSubTask(int id, SubTask subTask);

    void deleteAllTask();

    void updateEpicTime();

    List<AbstractTask> getHistory();

    List<AbstractTask> getPrioritizedTasks();
    void setPrioritizedTasks();

    void validationTasks();
    @Test
    void addNewTask() {
        Task task = new Task("Task1", "SubTask1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(1000), ZoneOffset.UTC), 0);
        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }
}
