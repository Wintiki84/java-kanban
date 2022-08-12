package service;

import model.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTasksManager extends InMemoryTaskManager {

    static final String colon = ":";
    static final String comma = ",";
    static final String[] filePath = {"src/", "data"};
    static final String fileName = "tasks.csv";
    static final String tableHeader = "id,type,name,status,description,epic";
    static final String newLine = "\n";

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        super.createSubTask(subTask);
        save();
        return subTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateTask(Epic epic) {
        super.updateTask(epic);
        save();
    }

    @Override
    public void updateTask(SubTask subTask) {
        super.updateTask(subTask);
        save();
    }

    @Override
    public void updateEpicStatus() {
        super.updateEpicStatus();
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteSubTask(int taskId) {
        super.deleteSubTask(taskId);
        save();
    }

    @Override
    public void deleteEpic(int taskId) {
        super.deleteEpic(taskId);
        save();
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public Task getTask(int taskId) {
        Task task = super.getTask(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int taskId) {
        Epic epic = super.getEpic(taskId);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTask(int taskId) {
        SubTask subTask = super.getSubTask(taskId);
        save();
        return subTask;
    }


    private void save() {
        Path tasksFilePath = Paths.get(filePath[0], filePath[1], fileName);
        StringBuilder stringBuilderTasks = new StringBuilder();
        stringBuilderTasks.append(tableHeader + newLine);
        for (Task task : getTasks().values()) {
            stringBuilderTasks.append(task.toString())
                    .append(newLine);
        }

        for (Epic epic : getEpics().values()) {
            stringBuilderTasks.append(epic.toString())
                    .append(newLine);
        }

        for (SubTask subTask : getSubTasks().values()) {
            stringBuilderTasks.append(subTask.toString())
                    .append(newLine);
        }

        stringBuilderTasks.append("\n");
        for (AbstractTask Task : getHistory()) {
            stringBuilderTasks.append(Task.getId() + colon + Task.getTypeTask())
                    .append(newLine);
        }

        stringBuilderTasks.append(newLine);
        stringBuilderTasks.append(IdGenerator.getTaskId());
        String stingTasks = String.valueOf(stringBuilderTasks);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(tasksFilePath.toString()));) {
            fileWriter.write(stingTasks);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readingTasks() {
        Path tasksFilePath = Paths.get(filePath[0], filePath[1], fileName);
        try (BufferedReader fileReader = new BufferedReader(new FileReader(tasksFilePath.toString()));) {
            String stringTask;
            while ((stringTask = fileReader.readLine()) != null) {
                if (!stringTask.equals(tableHeader) & !stringTask.equals("")) {
                    if (stringTask.indexOf(comma) != -1) {
                        AbstractTask abstractTask = AbstractTask.fromString(stringTask);
                        switch (abstractTask.getTypeTask()) {
                            case TASK:
                                Task task = new Task(abstractTask.getName(),
                                        abstractTask.getDescription(),
                                        abstractTask.getStatus());
                                task.setId(abstractTask.getId());
                                task.setEpicId(abstractTask.getEpicId());
                                setTask(abstractTask.getId(), task);
                                continue;
                            case EPIC:
                                Epic epic = new Epic(abstractTask.getName(),
                                        abstractTask.getDescription(),
                                        abstractTask.getStatus());
                                epic.setId(abstractTask.getId());
                                epic.setEpicId(abstractTask.getEpicId());
                                setEpic(abstractTask.getId(), epic);
                                continue;
                            case SUB_TASK:
                                SubTask subTask = new SubTask(abstractTask.getName(),
                                        abstractTask.getDescription(),
                                        abstractTask.getStatus(), abstractTask.getEpicId());
                                subTask.setId(abstractTask.getId());
                                subTask.setEpicId(abstractTask.getEpicId());
                                setSubTask(abstractTask.getId(), subTask);
                                continue;
                            default:
                                continue;
                        }
                    } else if (stringTask.indexOf(colon) != -1) {
                        String[] splitTask = stringTask.split(colon);
                        TypeTask typeTask = TypeTask.fromString(splitTask[1]);
                        switch (typeTask) {
                            case TASK:
                                historyManager.add(tasks.get(Integer.parseInt(splitTask[0])));
                                continue;
                            case EPIC:
                                historyManager.add(epics.get(Integer.parseInt(splitTask[0])));
                                continue;
                            case SUB_TASK:
                                historyManager.add(subTasks.get(Integer.parseInt(splitTask[0])));
                                continue;
                            default:
                                continue;
                        }
                    } else IdGenerator.setTaskId(Integer.parseInt(stringTask));
                } else continue;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

