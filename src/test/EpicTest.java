package test;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class EpicTest {

    private final TaskManager managerTask = new InMemoryTaskManager();
    private final Epic epic = new Epic("Epic", "Epic", Status.NEW);
    int idEpic = managerTask.createEpic(epic).getId();
    SubTask subTask1 = new SubTask("SubTask1", "SubTask1", Status.NEW, idEpic,
            LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
    SubTask subTask2 = new SubTask("SubTask2", "SubTask2", Status.NEW, idEpic,
            LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
    SubTask subTask3 = new SubTask("SubTask3", "SubTask3", Status.NEW, idEpic,
            LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
    int idSubTask1 = managerTask.createSubTask(subTask1).getId();
    int idSubTask2 = managerTask.createSubTask(subTask2).getId();
    int idSubTask3 = managerTask.createSubTask(subTask3).getId();

    @Test
    public void AllSubtasksWithTheStatusNewEpicStatusNew() {

        Assertions.assertEquals(Status.NEW, managerTask.getEpic(idEpic).getStatus());
    };

    @Test
    public void AllSubtasksWithTheStatusDoneEpicStatusDone() {

        subTask1.setStatus(Status.DONE);
        managerTask.updateTask(subTask1);
        subTask2.setStatus(Status.DONE);
        managerTask.updateTask(subTask2);
        subTask3.setStatus(Status.DONE);
        managerTask.updateTask(subTask3);
        Assertions.assertEquals(Status.DONE, managerTask.getEpic(idEpic).getStatus());
    };

    @Test
    public void SubtasksWithNewAndDoneEpicStatusInProgress() {

        subTask1.setStatus(Status.DONE);
        managerTask.updateTask(subTask1);
        subTask2.setStatus(Status.NEW);
        managerTask.updateTask(subTask2);
        subTask3.setStatus(Status.NEW);
        managerTask.updateTask(subTask3);
        Assertions.assertEquals(Status.IN_PROGRESS, managerTask.getEpic(idEpic).getStatus());
    };

    @Test
    public void SubtasksWithInProgressEpicStatusInProgress() {

        subTask1.setStatus(Status.IN_PROGRESS);
        managerTask.updateTask(subTask1);
        subTask2.setStatus(Status.IN_PROGRESS);
        managerTask.updateTask(subTask2);
        subTask3.setStatus(Status.IN_PROGRESS);
        managerTask.updateTask(subTask3);
        Assertions.assertEquals(Status.IN_PROGRESS, managerTask.getEpic(idEpic).getStatus());
    };


}