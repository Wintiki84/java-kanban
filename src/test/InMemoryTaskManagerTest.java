package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;
import service.TaskManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryTaskManagerTest extends TaskManagerTest {
    protected TaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    public void beforeEach() {
        taskManager.deleteAllTask();
    }

    @Override
    @Test
    public void createAndGetTasks() {
        tasks = taskManager.getTasks();
        assertEquals(0, tasks.size(), "Неверное количество задач.");

        final Task task = new Task("Task1", "Task1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        final Task savedTask = taskManager.createTask(task);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(task.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void createAndGetEpics() {
        epics = taskManager.getEpics();
        assertEquals(0, epics.size(), "Неверное количество задач.");

        final Epic epic = new Epic("Epic", "Epic", Status.NEW);

        final Epic savedEpic = taskManager.createEpic(epic);

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");

        epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(epic.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void createAndGetSubTasks() {
        subTasks = taskManager.getSubTasks();
        assertEquals(0, subTasks.size(), "Неверное количество задач.");

        final Epic epic = new Epic("Epic", "Epic", Status.NEW);
        final SubTask subTask = new SubTask("SubTask1", "SubTask1", Status.NEW, epic.getEpicId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        taskManager.createEpic(epic);
        final SubTask savedSubTask = taskManager.createSubTask(subTask);

        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");

        subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(subTask.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void updateTask() {
        final Task task = new Task("Task1", "Task1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        taskManager.createTask(task);

        task.setStatus(Status.DONE);

        taskManager.updateTask(task);
        tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(task.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void updateEpic() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);

        taskManager.createEpic(epic);

        epic.setStatus(Status.DONE);

        taskManager.updateTask(epic);
        epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(epic.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void updateSubTask() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);
        final SubTask subTask = new SubTask("SubTask1", "SubTask1", Status.NEW, epic.getEpicId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);

        subTask.setStatus(Status.DONE);

        taskManager.updateTask(subTask);
        subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(subTask.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void deleteTask() {
        final Task task = new Task("Task1", "Task1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        taskManager.createTask(task);

        taskManager.deleteTask(task.getId());

        tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(0, tasks.size(), "Неверное количество задач.");
        assertEquals(null, tasks.get(task.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void deleteEpic() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);
        taskManager.createEpic(epic);

        taskManager.deleteEpic(epic.getId());

        epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(0, epics.size(), "Неверное количество задач.");
        assertEquals(null, epics.get(epic.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void deleteSubTask() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);
        final SubTask subTask = new SubTask("SubTask1", "SubTask1", Status.NEW, epic.getEpicId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);

        taskManager.deleteSubTask(subTask.getId());

        subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(0, subTasks.size(), "Неверное количество задач.");
        assertEquals(null, subTasks.get(subTask.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    public void getTask() {
        final Task task = new Task("Task1", "Task1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        taskManager.createTask(task);

        tasks = taskManager.getTasks();

        final Task savedTask = taskManager.getTask(task.getId());

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    @Override
    @Test
    public void getEpic() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);

        taskManager.createEpic(epic);

        epics = taskManager.getEpics();

        final Epic savedEpic = taskManager.getEpic(epic.getId());

        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
    }

    @Override
    @Test
    public void getSubTask() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);
        final SubTask subTask = new SubTask("SubTask1", "SubTask1", Status.NEW, epic.getEpicId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        subTasks = taskManager.getSubTasks();

        final SubTask savedSubTask = taskManager.getSubTask(subTask.getId());

        assertNotNull(subTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");
    }

    @Override
    @Test
    void deleteAllTask() {
        final Task task = new Task("Task1", "Task1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);
        final SubTask subTask = new SubTask("SubTask1", "SubTask1", Status.NEW, epic.getEpicId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);

        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createSubTask(subTask);
        taskManager.deleteAllTask();
        tasks = taskManager.getTasks();
        epics = taskManager.getEpics();
        subTasks = taskManager.getSubTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertNotNull(epics, "Задачи на возвращаются.");
        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(0, tasks.size(), "Неверное количество Task.");
        assertEquals(null, tasks.get(task.getId()), "Задачи не совпадают.");
        assertEquals(0, epics.size(), "Неверное количество Epic.");
        assertEquals(null, epics.get(task.getId()), "Задачи не совпадают.");
        assertEquals(0, subTasks.size(), "Неверное количество SubTask.");
        assertEquals(null, subTasks.get(task.getId()), "Задачи не совпадают.");
    }

    @Override
    @Test
    void getHistory() {
        final Task task1 = new Task("Task1", "Task1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        taskManager.createTask(task1);
        final Task task2 = new Task("Task2", "Task2", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        taskManager.createTask(task2);
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);
        taskManager.createEpic(epic);
        final SubTask subTask1 = new SubTask("SubTask1", "SubTask1", Status.NEW, epic.getEpicId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        taskManager.createSubTask(subTask1);
        final SubTask subTask2 = new SubTask("SubTask2", "SubTask2", Status.NEW, epic.getEpicId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        taskManager.createSubTask(subTask2);
        List<AbstractTask> history = taskManager.getHistory();

        assertNotNull(history, "История не возвращаются.");
        assertEquals(0, history.size(), "Неверное количество задач в истории.");

        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getEpic(epic.getId());
        taskManager.getTask(task1.getId());
        taskManager.getSubTask(subTask1.getId());
        history = taskManager.getHistory();

        assertEquals(4, history.size(), "Неверное количество задач.");
        assertEquals(history.get(0), task2, "Задачи не совпадают.");
        assertEquals(history.get(1), epic, "Задачи не совпадают.");
        assertEquals(history.get(2), task1, "Задачи не совпадают.");
        assertEquals(history.get(3), subTask1, "Задачи не совпадают.");

        taskManager.deleteTask(task2.getId());
        history = taskManager.getHistory();

        assertEquals(3, history.size(), "Неверное количество задач.");
        assertEquals(history.get(0), epic, "Задачи не совпадают.");
        assertEquals(history.get(1), task1, "Задачи не совпадают.");
        assertEquals(history.get(2), subTask1, "Задачи не совпадают.");

        taskManager.deleteTask(task1.getId());
        history = taskManager.getHistory();

        assertEquals(2, history.size(), "Неверное количество задач.");
        assertEquals(history.get(0), epic, "Задачи не совпадают.");
        assertEquals(history.get(1), subTask1, "Задачи не совпадают.");

        taskManager.deleteTask(subTask1.getId());
        history = taskManager.getHistory();

        assertEquals(1, history.size(), "Неверное количество задач.");
        assertEquals(history.get(0), epic, "Задачи не совпадают.");
    }
}
