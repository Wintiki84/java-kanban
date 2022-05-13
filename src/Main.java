import java.util.*;


public class Main<menuActionsTask, exit> {

    public static void main(String[] args) {
        //весь код в этом классе только для проверки роботоспособности (временный)
        System.out.println("Поехали!");
        ManagerTask managerTask = new ManagerTask();
        Status status = Status.NEW;

        Epic epic1 = new Epic("Важный эпик 1", "Очень важный", status);
        managerTask.newTask(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1 важного эпика 1", "Очень важная подзадача",
                status, 1);
        managerTask.newTask(subTask1);
        SubTask subTask2 = new SubTask("Подзадача 2 важного эпика 1", "Очень важная подзадача",
                status, 1);
        managerTask.newTask(subTask2);
        Epic epic2 = new Epic("Важный эпик 2", "Очень важный", status);
        managerTask.newTask(epic2);
        SubTask subTask3 = new SubTask("Подзадача 1 важного эпика 2", "Очень важная подзадача",
                status, 4);
        managerTask.newTask(subTask3);
        Task task = new Task("Важная задача 1", "Очень важная", status);
        managerTask.newTask(task);

        System.out.println("Задача № " + managerTask.tasks.get(6).getId());
        System.out.println(managerTask.tasks.get(6).getName() + " " + managerTask.tasks.get(6).getDescription() + " " +
                managerTask.tasks.get(6).getStatus());


        for (Map.Entry<Integer, Epic> epic : managerTask.getEpics().entrySet()) {
            System.out.println("Эпик № " + epic.getKey());
            System.out.println(epic.getValue().getName() + " " + epic.getValue().getDescription() + " " +
                    epic.getValue().getStatus());
            System.out.println("Подзадачи" + epic.getValue().subTaskId);
        }
        for (Map.Entry<Integer, SubTask> subTask : managerTask.getSubTasks().entrySet()) {
            System.out.println("Подзадача № " + subTask.getKey());
            System.out.println(subTask.getValue().getName() + " " + subTask.getValue().getDescription() + " " +
                    subTask.getValue().getStatus());
        }
        SubTask subTask4 = managerTask.subTasks.get(2);
        subTask4.setStatus(Status.IN_PROGRESS);
        managerTask.updateTask(subTask4);
        subTask4 = managerTask.subTasks.get(5);
        subTask4.setStatus(Status.DONE);
        managerTask.updateTask(subTask4);
        managerTask.updateEpicStatus();
        System.out.println("Задача № " + managerTask.tasks.get(6).getId());
        System.out.println(managerTask.tasks.get(6).getName() + " " + managerTask.tasks.get(6).getDescription() + " " +
                managerTask.tasks.get(6).getStatus());
        for (Map.Entry<Integer, Epic> epic : managerTask.getEpics().entrySet()) {
            System.out.println("Эпик № " + epic.getKey());
            System.out.println(epic.getValue().getName() + " " + epic.getValue().getDescription() + " " +
                    epic.getValue().getStatus());
            System.out.println("Подзадачи" + epic.getValue().subTaskId);
        }
        for (Map.Entry<Integer, SubTask> subTask : managerTask.getSubTasks().entrySet()) {
            System.out.println("Подзадача № " + subTask.getKey());
            System.out.println(subTask.getValue().getName() + " " + subTask.getValue().getDescription() + " " +
                    subTask.getValue().getStatus());
        }
        managerTask.deleteTask(5, TypeTask.SUB_TASK);
        managerTask.deleteTask(1, TypeTask.EPIC);
        System.out.println("Задача № " + managerTask.tasks.get(6).getId());
        System.out.println(managerTask.tasks.get(6).getName() + " " + managerTask.tasks.get(6).getDescription() + " " +
                managerTask.tasks.get(6).getStatus());
        for (Map.Entry<Integer, Epic> epic : managerTask.getEpics().entrySet()) {
            System.out.println("Эпик № " + epic.getKey());
            System.out.println(epic.getValue().getName() + " " + epic.getValue().getDescription() + " " +
                    epic.getValue().getStatus());
            System.out.println("Подзадачи" + epic.getValue().subTaskId);
        }
        for (Map.Entry<Integer, SubTask> subTask : managerTask.getSubTasks().entrySet()) {
            System.out.println("Подзадача № " + subTask.getKey());
            System.out.println(subTask.getValue().getName() + " " + subTask.getValue().getDescription() + " " +
                    subTask.getValue().getStatus());
        }
    }
}
