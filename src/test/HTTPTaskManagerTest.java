package test;

import model.AbstractTask;
import model.Epic;
import model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import service.HTTPTaskManager;
import service.KVServer;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HTTPTaskManagerTest extends TaskManagerTest<HTTPTaskManager> {

    KVServer kvServer = new KVServer();

    public HTTPTaskManagerTest() throws IOException {
        kvServer.start();
        taskManager = new HTTPTaskManager("http://localhost:8078");
    }

    @AfterEach
    public void stopKVServer() {
        kvServer.stop();
    }

    @Test
    void readingAnEmptyTaskList() {
        taskManager.getTask(1);
        taskManager.readingTasks();
        tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(0, tasks.size(), "Неверное количество задач.");

        epics = taskManager.getEpics();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(0, epics.size(), "Неверное количество задач.");

        subTasks = taskManager.getSubTasks();

        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(0, subTasks.size(), "Неверное количество задач.");
    }

    @Test
    void readingAnEpicWithoutSubtasks() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);

        taskManager.createEpic(epic);
        epics = taskManager.getEpics();
        taskManager.readingTasks();

        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(epic.getId()), "Задачи не совпадают.");
    }

    @Test
    void ReadingTasksAnEmptyHistoryList() {
        final Epic epic = new Epic("Epic", "Epic", Status.NEW);

        taskManager.getHistory().clear();
        taskManager.createEpic(epic);
        taskManager.readingTasks();
        List<AbstractTask> history = taskManager.getHistory();

        assertNotNull(history, "История на возвращаются.");
        assertEquals(0, history.size(), "Неверное количество задач в истории.");
    }
}
