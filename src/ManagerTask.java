import java.util.HashMap;
import java.util.Map;

public class ManagerTask {
    private Integer id = 0;
    Map<Integer, Task> tasks = new HashMap<>();
    Map<Integer, SubTask> subTasks = new HashMap<>();
    Map<Integer, Epic> epics = new HashMap<>();


    public Integer getId() {
        return id;
    }
    
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    public Map<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    public void newTask(Task task) {
        id++;
        task.setId(id);
        tasks.put(id, task);
    }

    public void newTask(SubTask subTask) {
        id++;
        subTask.setId(id);
        Epic tempEpic = epics.get(subTask.getEpicId());//Добовляем ИД поздадачи в эпик
        tempEpic.subTaskId.add(id);
        epics.put(subTask.getEpicId(), tempEpic);
        subTasks.put(id, subTask);
    }

    public void newTask(Epic epic) {
        id++;
        epic.setId(id);
        epics.put(id, epic);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateTask(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
    }

    // метод для изменения статуса эпик
    public void updateEpicStatus() {
        for (Epic epic : epics.values()) {
            Integer i = 0;
            Double status = 0.0;
            for (SubTask subTask : subTasks.values()) {
                if (subTask.getEpicId() == epic.getId()) {
                    i++;
                    switch (subTask.getStatus()) {
                        case NEW:
                            status += 1.0;
                            break;
                        case IN_PROGRESS:
                            status += 2.0;
                            break;
                        case DONE:
                            status += 3.0;
                            break;
                    }
                }
            }
            if ((status / i) == 1) {
                epic.setStatus(Status.NEW);
                epics.put(epic.getId(), epic);
            } else if ((status / i) == 3) {
                epic.setStatus(Status.DONE);
                epics.put(epic.getId(), epic);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
                epics.put(epic.getId(), epic);
            }
        }
    }


    public void deleteTask(Integer taskId, TypeTask typeTask) {
        switch (typeTask) {
            case TASK:
                tasks.remove(taskId);
                break;
            case EPIC:
                for (Integer id : epics.get(taskId).getSubTaskId()) {
                    subTasks.remove(id);
                }
                epics.remove(taskId);
                break;
            case SUB_TASK:
                epics.get(subTasks.get(taskId).getEpicId()).subTaskId.remove(taskId);
                subTasks.remove(taskId);
                break;
        }

    }

    public AbstractTask getTask(Integer taskId, TypeTask typeTask) {
        switch (typeTask) {
            case TASK:
                return tasks.get(taskId);
            case EPIC:
                return epics.get(taskId);
            case SUB_TASK:
                return subTasks.get(taskId);
            default:
                throw new IllegalStateException("Unexpected value: " + typeTask);
        }
    }
    
    public void deleteAllTask(){
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

}
