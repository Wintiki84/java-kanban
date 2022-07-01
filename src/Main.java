import model.*;
import service.*;

public class Main {

    public static void main(String[] args) {
        //весь код в этом классе только для проверки роботоспособности (временный)
        System.out.println("Поехали!");
        InMemoryTaskManager managerTask = new InMemoryTaskManager();
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
        Task task = new Task("Важная задача 1", "Очень важная", status);
        managerTask.createTask(task);


        managerTask.getTask(6);
        managerTask.getEpic(1);
        managerTask.getEpic(4);
        managerTask.getSubTask(5);
        managerTask.getEpic(1);
        managerTask.getSubTask(2);
        managerTask.getSubTask(3);
        managerTask.getSubTask(5);


        for (AbstractTask tasks : managerTask.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }

        System.out.println("Удаляем ");
        managerTask.deleteSubTask(5);

        for (AbstractTask tasks : managerTask.getHistory()) {
            System.out.println("Задача № " + tasks.getId());
        }
    }
}
