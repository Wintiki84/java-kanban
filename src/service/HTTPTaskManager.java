package service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.AbstractTask;
import model.SubTask;
import model.Task;
import model.TypeTask;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HTTPTaskManager extends FileBackedTasksManager {
    private static Gson gson = new Gson();
    protected Map<Integer, TypeTask> history = new HashMap<>();
    private KVTaskClient kvTaskClient;

    public HTTPTaskManager(String uri) {
        super("tasksTest.csv");
        kvTaskClient = new KVTaskClient(uri);
    }

    @Override
    public void save() {

        for (AbstractTask task : getHistory()) {
            history.put(task.getId(), task.getTypeTask());
        }
        String tasksSerialized = gson.toJson(getTasks());
        String epicsSerialized = gson.toJson(getEpics());
        String subTasksSerialized = gson.toJson(getSubTasks());
        String historySerialized = gson.toJson(history);
        String taskIdSerialized = gson.toJson(IdGenerator.getTaskId());
        String response = "{\"tasks\":" + tasksSerialized + ",\"epics\":" + epicsSerialized +
                ",\"subtasks\":" + subTasksSerialized + ",\"history\":" +
                historySerialized + ",\"IdGenerator\":" + taskIdSerialized + "}";
        kvTaskClient.put(kvTaskClient.getApiToken(), response);
    }

    @Override
    public void readingTasks() {
        String tasksJson = kvTaskClient.load(kvTaskClient.getApiToken());
        JsonElement jsonElement = JsonParser.parseString(tasksJson);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonObject tasksObject = jsonObject.get("tasks").getAsJsonObject();
        Type typeTasks = new TypeToken<Map<Integer, Task>>() {
        }.getType();
        tasks = new Gson().fromJson(tasksObject, typeTasks);

        JsonObject epicsObject = jsonObject.get("epics").getAsJsonObject();
        Type typeEpics = new TypeToken<Map<Integer, Task>>() {
        }.getType();
        epics = new Gson().fromJson(epicsObject, typeEpics);

        JsonObject subTasksObject = jsonObject.get("subtasks").getAsJsonObject();
        Type typeSubTasks = new TypeToken<Map<Integer, SubTask>>() {
        }.getType();
        subTasks = new Gson().fromJson(subTasksObject, typeSubTasks);

        JsonObject historyObject = jsonObject.get("history").getAsJsonObject();
        Type typeHistory = new TypeToken<Map<Integer, TypeTask>>() {
        }.getType();
        Map<Integer, TypeTask> history = new Gson().fromJson(historyObject, typeHistory);
        for (Map.Entry<Integer, TypeTask> entryHistory : history.entrySet()) {
            TypeTask typeTask = entryHistory.getValue();
            switch (typeTask) {
                case TASK:
                    historyManager.add(tasks.get(entryHistory.getKey()));
                    continue;
                case EPIC:
                    historyManager.add(epics.get(entryHistory.getKey()));
                    continue;
                case SUB_TASK:
                    historyManager.add(subTasks.get(entryHistory.getKey()));
                    continue;
                default:
                    continue;
            }
        }
        IdGenerator.setTaskId(jsonObject.get("IdGenerator").getAsInt());
    }
}
