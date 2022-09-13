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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static Gson gson = new Gson();
    private static FileBackedTasksManager fileBackedTasksManager = Managers.getDefaultFileBackedTasksManager();

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


    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            String query = httpExchange.getRequestURI().getQuery();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    if (query == null) {
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getTasks());
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        Integer taskId = Integer.valueOf(query);
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getTask(taskId));
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                case POST:
                    Task task = gson.fromJson(query, Task.class);
                    String tasksSerialized = gson.toJson(fileBackedTasksManager.createTask(task));
                    response = tasksSerialized;
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

    static class EpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            String query = httpExchange.getRequestURI().getQuery();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    if (query == null) {
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getEpics());
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        Integer taskId = Integer.valueOf(query);
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getEpic(taskId));
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                case POST:
                    Epic epic = gson.fromJson(query, Epic.class);
                    String tasksSerialized = gson.toJson(fileBackedTasksManager.createEpic(epic));
                    response = tasksSerialized;
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

    static class SubTaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            String query = httpExchange.getRequestURI().getQuery();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    if (query == null) {
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getSubTasks());
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        Integer taskId = Integer.valueOf(query);
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getSubTask(taskId));
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                case POST:
                    SubTask subTask = gson.fromJson(query, SubTask.class);
                    String tasksSerialized = gson.toJson(fileBackedTasksManager.createSubTask(subTask));
                    response = tasksSerialized;
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

    static class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    String tasksSerialized = gson.toJson(fileBackedTasksManager.getHistory());
                    response = tasksSerialized;
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

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            switch (RequestMethod.valueOf(method)) {
                case GET:
                    String tasksSerialized = gson.toJson(fileBackedTasksManager.getPrioritizedTasks());
                    response = tasksSerialized;
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

