package com.davidmogar.quizzer;

import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.Test;
import com.davidmogar.quizzer.loaders.AssessmentLoader;
import com.davidmogar.quizzer.loaders.TestsLoader;
import com.google.gson.Gson;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import static spark.Spark.get;

public class Quizzer {

    private Options options;

    public Quizzer() {
        options = new Options();

        setCommandLineOptions();
    }

    public Assessment calculateGrades(URL questionsUrl, URL answersURL) {
        Assessment assessment = null;

        try {
            assessment = AssessmentLoader.loadAssessment(questionsUrl, answersURL, null);
            if (assessment != null) {
                assessment.calculateGrades();
            }
        } catch (IOException e) {
            // Return default value
        }

        return assessment;
    }

    public boolean validateAssessments(URL url) throws IOException {
        boolean valid = true;

        for (Test test : TestsLoader.loadTests(url)) {
            try {
                Assessment assessment = AssessmentLoader.loadAssessment(test.getQuestionsUrl(), test.getAnswersUrl(),
                        test.getGradesUrl());
                if (assessment != null) {
                    if (assessment.validateGrades()) {
                        System.out.println("Test valid");
                    } else {
                        valid = false;
                        System.err.println("Test not valid");
                    }
                }
            } catch (IOException e) {
                valid = false;
            }
        }

        return valid;
    }

    private void parseArguments(String[] args) throws IOException {
        CommandLineParser parser = new BasicParser();
        CommandLine commandLine = null;

        try {
            commandLine = parser.parse(options, args);

            if (args.length == 0) {
                Server.startServer();
            } else if (commandLine.hasOption("h")) {
                showHelp();
            } else if (commandLine.hasOption("t")) {
                    boolean valid = validateAssessments(new URL(commandLine.getOptionValue("t")));
                    System.out.println(valid ? "All tests OK" : "Tests failed");
            } else if (commandLine.hasOption("a") && commandLine.hasOption("q")) {
                Assessment assessment = calculateGrades(new URL(commandLine.getOptionValue("q")),
                        new URL(commandLine.getOptionValue("a")));
                if (commandLine.hasOption("s")) {
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(assessment.getStatistics()));
                }
            } else {
                showHelp();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCommandLineOptions() {
        options.addOption("q", true, "URL to the questions file");
        options.addOption("a", true, "URL to the answers file");
        options.addOption("o", false, "Generate output");
        options.addOption("t", true, "Validate assessments in tests file");
        options.addOption("s", false, "Show questions statistics");
        options.addOption("h", false, "Show this help");
    }

    private void showHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java Quizzer [options]", options);
    }

    public static void main(String[] args) {
        Quizzer quizzer = new Quizzer();
        try {
            quizzer.parseArguments(args);
        } catch (IOException e) {
            System.err.append("There was a problem while executing the program: " + e.getMessage());
        }
    }

}
