package test;

import service.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    public InMemoryTaskManagerTest() {
        taskManager = new InMemoryTaskManager();
    }
}