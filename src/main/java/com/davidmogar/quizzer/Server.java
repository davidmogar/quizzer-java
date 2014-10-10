package com.davidmogar.quizzer;

import com.davidmogar.quizzer.loaders.AssessmentLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.util.Set;

import static spark.Spark.*;

public class Server {

    public static void startServer() {
        get("/", (req, res) -> {
            return "<html><body><form action=\"/\" method=\"POST\"><textarea name=\"questions\" rows=\"10\" " +
                    "cols=\"50\"></textarea><textarea name=\"answers\" rows=\"10\" cols=\"50\"></textarea><input " +
                    "type=\"submit\" " +
                    "value=\"Generate " +
                    "grades\"/></form></body></html>";
        });

        createPostRoutes();
    }

    private static void createPostRoutes() {
        post("/", (req, res) -> {
            Set<String> params = req.queryParams();
            String questionsJson = req.queryParams("questions");
            String answersJson = req.queryParams("answers");

            String response;

            try {
                Assessment assessment = AssessmentLoader.loadAssessment(questionsJson, answersJson, null);
                assessment.calculateGrades();
                Gson gson = new GsonBuilder().create();
                response = gson.toJson(assessment.getGrades());
            } catch (IOException e) {
                response = "Invalid input";
            }

            return response;
        });
    }

}
