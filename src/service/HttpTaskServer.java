package service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

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
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }


    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String query = httpExchange.getRequestURI().getQuery();
            switch (method) {
                case "GET":
                    if (path.equals("/tasks/task")) {
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getTasks());
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    } else {
                        JsonElement jsonElement = JsonParser.parseString(query);
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        int taskId = jsonObject.get("id").getAsInt();
                        String tasksSerialized = gson.toJson(fileBackedTasksManager.getTask(taskId));
                        response = tasksSerialized;
                        httpExchange.sendResponseHeaders(200, 0);
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }
                    /*    if (id) {
                            httpExchange.sendResponseHeaders(404, 0);
                            response = "";
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(response.getBytes());
                            }
                        }
                    }
                case "POST":
                    int postId = Integer.parseInt(path.split("/")[2]);
                    for (Post post : posts) {
                        if (post.getId() == postId) {
                            id = false;
                            String postSerialized = path.split("/")[3];
                            post.addComment(new Comment("ya", "123"));
                            response = "";
                            httpExchange.sendResponseHeaders(201, 0);
                            try (OutputStream os = httpExchange.getResponseBody()) {
                                os.write(response.getBytes());
                            }
                        }
                    }
                    if (id) {
                        httpExchange.sendResponseHeaders(404, 0);
                        response = "";
                        try (OutputStream os = httpExchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }*/
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

