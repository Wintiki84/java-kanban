import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import service.HttpTaskManager;
import service.HttpTaskServer;
import service.KvServer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Main {

    public static void main(String[] args) throws IOException {
        //весь код в этом классе только для проверки роботоспособности (временный)
        KvServer kvServer = new KvServer();
        System.out.println("Поехали!");
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.startHttpTaskServer();
        kvServer.start();
        HttpTaskManager managerTask = new HttpTaskManager("http://localhost:8078");


        Epic epic1 = new Epic("Важный эпик 1", "Очень важный", Status.NEW);
        managerTask.createEpic(epic1);
        SubTask subTask1 = new SubTask("SubTask1", "SubTask1", Status.NEW, epic1.getId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        managerTask.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("SubTask1", "SubTask1", Status.NEW, epic1.getId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        managerTask.createSubTask(subTask2);
        Epic epic2 = new Epic("Важный эпик 2", "Очень важный", Status.NEW);
        managerTask.createEpic(epic2);
        SubTask subTask3 = new SubTask("SubTask1", "SubTask1", Status.NEW, epic2.getId(),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        managerTask.createSubTask(subTask3);
        Task task = new Task("Task1", "Task1", Status.NEW,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneOffset.UTC), 0);
        managerTask.createTask(task);
        //InMemoryTaskManager managerTask = new InMemoryTaskManager();

        managerTask.readingTasks();
        kvServer.stop();




       /* for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getTask(1);
        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getEpic(2);
        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getEpic(4);
        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getSubTask(6);
        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getEpic(2);
        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getSubTask(8);
        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getSubTask(9);
        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        fileBackedTasksManager.getSubTask(6);

        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
        System.out.println("\n");
        for (AbstractTask tasks : fileBackedTasksManager.getPrioritizedTasks()) {
            System.out.println("Задача № " + tasks.getId() + ": " + tasks.getStartTime());
        }*/

    }
}
