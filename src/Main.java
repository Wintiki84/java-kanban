import model.*;
import service.*;

public class Main {

    public static void main(String[] args) {
        //весь код в этом классе только для проверки роботоспособности (временный)
        /*System.out.println("Поехали!");
        InMemoryTaskManager managerTask = new FileBackedTasksManager();
        Status status = Status.NEW;

        Epic epic1 = new Epic("Важный эпик 1", "Очень важный", status);
        managerTask.createEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1 важного эпика 1", "Очень важная подзадача",
                status, 1);
        managerTask.createSubTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2 важного эпика 1", "Очень важная подзадача",
                status, 1);
        managerTask.createSubTask(subTask2);
        Epic epic2 = new Epic("Важный эпик 2", "Очень важный", status);
        managerTask.createEpic(epic2);
        SubTask subTask3 = new SubTask("Подзадача 1 важного эпика 2", "Очень важная подзадача",
                status, 4);
        managerTask.createSubTask(subTask3);
        Task task = new Task( "Важная задача 1", "Очень важная", status);
        managerTask.createTask(task);*/
        //InMemoryTaskManager managerTask = new InMemoryTaskManager();
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        fileBackedTasksManager.readingTasks();



        for (AbstractTask tasks : fileBackedTasksManager.getHistory()) {
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
        }

    }
}
