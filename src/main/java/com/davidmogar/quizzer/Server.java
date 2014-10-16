package com.davidmogar.quizzer;

import com.davidmogar.quizzer.loaders.AssessmentLoader;
import com.davidmogar.quizzer.serializers.AssessmentSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ModelAndView;
import spark.template.freemarker.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

public class Server {

    public static void startServer() {

        /* Static files location */
        staticFileLocation("public");

        /* Get index page */
        get("/", (req, res) -> {
            return new ModelAndView(new HashMap<String, Object>(), "index.ftl");
        }, new FreeMarkerEngine());

        /* Post questions and answers */
        post("/", (req, res) -> {
            String questionsJson = req.queryParams("questions");
            String answersJson = req.queryParams("answers");

            String jsonGrades = "Invalid input";

            if (questionsJson != null && answersJson != null) {
                try {
                    Assessment assessment = AssessmentLoader.loadAssessment(questionsJson, answersJson, null);
                    assessment.calculateGrades();
                    jsonGrades = AssessmentSerializer.serializeGrades(assessment.getGrades(),
                            AssessmentSerializer.Format.JSON);
                } catch (IOException e) {
                    /* Return default value */
                }
            }

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("grades", jsonGrades);

            return new ModelAndView(attributes, "grades.ftl");
        }, new FreeMarkerEngine());
    }

}
