package service;

import model.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final String COLON = ":";
    private static final String COMMA = ",";
    private static final String SRC_PATH = "src/";
    private static final String DATA_PATH = "data";
    private static final String FILE_NAME = "tasks.csv";
    private static final String TABLE_HEADER = "id,type,name,status,description,epic";
    private static final String NEW_LINE = "\n";

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
        Path tasksFILE_PATH = Paths.get(SRC_PATH, DATA_PATH, FILE_NAME);
        StringBuilder stringBuilderTasks = new StringBuilder();
        stringBuilderTasks.append(TABLE_HEADER + NEW_LINE);
        for (Task task : getTasks().values()) {
            stringBuilderTasks.append(task.toString())
                    .append(NEW_LINE);
        }

        for (Epic epic : getEpics().values()) {
            stringBuilderTasks.append(epic.toString())
                    .append(NEW_LINE);
        }

        for (SubTask subTask : getSubTasks().values()) {
            stringBuilderTasks.append(subTask.toString())
                    .append(NEW_LINE);
        }

        stringBuilderTasks.append("\n");
        for (AbstractTask Task : getHistory()) {
            stringBuilderTasks.append(Task.getId() + COLON + Task.getTypeTask())
                    .append(NEW_LINE);
        }

        stringBuilderTasks.append(NEW_LINE);
        stringBuilderTasks.append(IdGenerator.getTaskId());
        String stingTasks = String.valueOf(stringBuilderTasks);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(tasksFILE_PATH.toString()));) {
            fileWriter.write(stingTasks);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readingTasks() {
        Path tasksFILE_PATH = Paths.get(SRC_PATH, DATA_PATH, FILE_NAME);
        try (BufferedReader fileReader = new BufferedReader(new FileReader(tasksFILE_PATH.toString()));) {
            String stringTask;
            while ((stringTask = fileReader.readLine()) != null) {
                if (!stringTask.equals(TABLE_HEADER) & !stringTask.equals("")) {
                    if (stringTask.indexOf(COMMA) != -1) {
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
                    } else if (stringTask.indexOf(COLON) != -1) {
                        String[] splitTask = stringTask.split(COLON);
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

