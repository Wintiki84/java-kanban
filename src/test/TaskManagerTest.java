package test;

import model.*;
import org.junit.jupiter.api.Test;
import service.TaskManager;
import java.util.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();

    @Test
    public void createAndGetTasks() {
    }

    @Test
    public void createAndGetEpics() {
    }

    @Test
    public void createAndGetSubTasks() {
    }

    @Test
    public void updateTask() {
    }

    @Test
    public void updateEpic() {
    }

    @Test
    public void updateSubTask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void deleteSubTask() {
    }

    @Test
    void deleteEpic() {
    }

    @Test
    void getTask() {
    }

    @Test
    void getEpic() {
    }

    @Test
    void getSubTask() {
    }

    @Test
    void deleteAllTask() {
    }

    @Test
    void getHistory() {
    }
}
