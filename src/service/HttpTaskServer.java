package service;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.RequestMethod;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static Gson gson = new Gson();
    private FileBackedTasksManager fileBackedTasksManager = Managers.getDefaultFileBackedTasksManager();

    public void startHttpTaskServer() throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler());
        httpServer.createContext("/tasks/epic", new EpicHandler());
        httpServer.createContext("/tasks/subtask", new SubTaskHandler());
        httpServer.createContext("/tasks/history", new HistoryHandler());
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }


    public class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            String query = httpExchange.getRequestURI().getQuery();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    if (query == null) {
                        String serializedTasks = gson.toJson(fileBackedTasksManager.getTasks());
                        response = serializedTasks;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        int taskId = Integer.valueOf(query);
                        String serializedTask = gson.toJson(fileBackedTasksManager.getTask(taskId));
                        response = serializedTask;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                case POST:
                    Task task = gson.fromJson(query, Task.class);
                    String serializedTask = gson.toJson(fileBackedTasksManager.createTask(task));
                    response = serializedTask;
                    httpExchange.sendResponseHeaders(201, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                case DELETE:
                    Integer taskId = Integer.valueOf(query);
                    fileBackedTasksManager.deleteTask(taskId);
                    response = "";
                    httpExchange.sendResponseHeaders(204, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    response = "";
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

            }
        }
    }

    public class EpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            String query = httpExchange.getRequestURI().getQuery();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    if (query == null) {
                        String serializedEpics = gson.toJson(fileBackedTasksManager.getEpics());
                        response = serializedEpics;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        Integer taskId = Integer.valueOf(query);
                        String serializedEpic = gson.toJson(fileBackedTasksManager.getEpic(taskId));
                        response = serializedEpic;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                case POST:
                    Epic epic = gson.fromJson(query, Epic.class);
                    String serializedEpic = gson.toJson(fileBackedTasksManager.createEpic(epic));
                    response = serializedEpic;
                    httpExchange.sendResponseHeaders(201, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                case DELETE:
                    Integer taskId = Integer.valueOf(query);
                    fileBackedTasksManager.deleteEpic(taskId);
                    response = "";
                    httpExchange.sendResponseHeaders(204, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    response = "";
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

            }
        }
    }

    public class SubTaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            String query = httpExchange.getRequestURI().getQuery();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    if (query == null) {
                        String serializedSubTasks = gson.toJson(fileBackedTasksManager.getSubTasks());
                        response = serializedSubTasks;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        Integer taskId = Integer.valueOf(query);
                        String serializedSubTask = gson.toJson(fileBackedTasksManager.getSubTask(taskId));
                        response = serializedSubTask;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                case POST:
                    SubTask subTask = gson.fromJson(query, SubTask.class);
                    String serializedSubTask = gson.toJson(fileBackedTasksManager.createSubTask(subTask));
                    response = serializedSubTask;
                    httpExchange.sendResponseHeaders(201, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                case DELETE:
                    Integer taskId = Integer.valueOf(query);
                    fileBackedTasksManager.deleteSubTask(taskId);
                    response = "";
                    httpExchange.sendResponseHeaders(204, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    response = "";
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

            }
        }
    }

    public class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    String serializedHistory = gson.toJson(fileBackedTasksManager.getHistory());
                    response = serializedHistory;
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    response = "";
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
            }
        }
    }

    public class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    String serializedPrioritizedTasks = gson.toJson(fileBackedTasksManager.getPrioritizedTasks());
                    response = serializedPrioritizedTasks;
                    httpExchange.sendResponseHeaders(200, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                default:
                    httpExchange.sendResponseHeaders(404, 0);
                    response = "";
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
            }
        }
    }
}

