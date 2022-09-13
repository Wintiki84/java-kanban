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
    private static final String TABLE_HEADER = "id,type,name,status,description,epic,datetime,description";
    private static final String NEW_LINE = "\n";
    private String FileName = "tasks.csv";
    private Path path = Paths.get(SRC_PATH, DATA_PATH, FileName);

    public FileBackedTasksManager(String fileName) {
        FileName = fileName;
        path = Paths.get(SRC_PATH, DATA_PATH, FileName);
    }

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

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    protected void save() {
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
        for (AbstractTask task : getHistory()) {
            stringBuilderTasks.append(task.getId() + COLON + task.getTypeTask())
                    .append(NEW_LINE);
        }

        stringBuilderTasks.append(NEW_LINE);
        stringBuilderTasks.append(IdGenerator.getTaskId());
        String stingTasks = String.valueOf(stringBuilderTasks);

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path.toString()))) {
            fileWriter.write(stingTasks);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readingTasks() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path.toString()))) {
            String stringTask;
            while ((stringTask = fileReader.readLine()) != null) {
                if (!stringTask.equals(TABLE_HEADER) & !stringTask.equals("")) {
                    if (stringTask.contains(COMMA)) {
                        AbstractTask abstractTask = AbstractTask.fromString(stringTask);
                        switch (abstractTask.getTypeTask()) {
                            case TASK:
                                Task task = new Task(abstractTask.getName(),
                                        abstractTask.getDescription(),
                                        abstractTask.getStatus(),
                                        abstractTask.getStartTime(),
                                        abstractTask.getDuration());
                                task.setId(abstractTask.getId());
                                setTask(abstractTask.getId(), task);
                                continue;
                            case EPIC:
                                Epic epic = new Epic(abstractTask.getName(),
                                        abstractTask.getDescription(),
                                        abstractTask.getStatus());
                                epic.setId(abstractTask.getId());
                                setEpic(abstractTask.getId(), epic);
                                continue;
                            case SUB_TASK:
                                SubTask subTaskString = SubTask.fromStringSubTask(stringTask);
                                SubTask subTask = new SubTask(subTaskString.getName(),
                                        subTaskString.getDescription(),
                                        subTaskString.getStatus(),
                                        subTaskString.getEpicId(),
                                        subTaskString.getStartTime(),
                                        subTaskString.getDuration());
                                subTask.setId(subTaskString.getId());
                                subTask.setEpicId(subTask.getEpicId());
                                setSubTask(subTaskString.getId(), subTask);
                                continue;
                            default:
                                continue;
                        }
                    } else if (stringTask.contains(COLON)) {
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
        setPrioritizedTasks();
    }
}

