package test;

import model.*;
import org.junit.jupiter.api.Test;
import service.DataComparatorTask;
import org.junit.jupiter.api.Assertions;
import service.InMemoryHistoryManager;
import service.TaskManager;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static model.Status.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagerTest<T extends TaskManager> {
    protected Map<Integer, Task> tasks = new HashMap<>();
    protected Map<Integer, SubTask> subTasks = new HashMap<>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    protected final List<AbstractTask> prioritizedListTasks = new ArrayList<>();

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
    void getTask(){
    }

    @Test
    void getEpic(){
    }

    @Test
    void getSubTask(){
    }

    @Test
    void deleteAllTask() {
    }

    @Test
    void getHistory() {
    }

 //   List<AbstractTask> getPrioritizedTasks();

}
