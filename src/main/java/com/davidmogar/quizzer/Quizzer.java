package com.davidmogar.quizzer;

import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.Test;
import com.davidmogar.quizzer.loaders.AssessmentLoader;
import com.davidmogar.quizzer.loaders.TestsLoader;
import com.davidmogar.quizzer.serializers.AssessmentSerializer;
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

    public void validateAssessments(URL url) throws IOException {
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

        System.out.println(valid ? "All tests OK" : "Tests failed");
    }

    private void parseArguments(String[] args) throws IOException {
        try {
            CommandLine commandLine = new BasicParser().parse(options, args);

            if (args.length == 0) {
                Server.startServer();
            } else if (commandLine.hasOption("h")) {
                showHelp();
            } else if (commandLine.hasOption("t")) {
                validateAssessments(new URL(commandLine.getOptionValue("t")));
            } else if (commandLine.hasOption("a") && commandLine.hasOption("q")) {
                Assessment assessment = calculateGrades(new URL(commandLine.getOptionValue("q")),
                        new URL(commandLine.getOptionValue("a")));

                AssessmentSerializer.Format format = commandLine.hasOption("o")? parseFormat(commandLine
                        .getOptionValue("o")) : AssessmentSerializer.Format.JSON;

                showGrades(assessment.getGrades(), format);

                if (commandLine.hasOption("s")) {
                    showStatistics(assessment.getStatistics(), format);
                }
            } else {
                showHelp();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private AssessmentSerializer.Format parseFormat(String formatString) {
        AssessmentSerializer.Format format;

        try {
            format = AssessmentSerializer.Format.valueOf(formatString.toUpperCase());
        } catch(Exception e) {
            format = AssessmentSerializer.Format.JSON;
        }

        return format;
    }

    private void setCommandLineOptions() {
        options.addOption("q", true, "URL to the questions file");
        options.addOption("a", true, "URL to the answers file");
        options.addOption("o", true, "Generate output");
        options.addOption("t", true, "Validate assessments in tests file");
        options.addOption("s", false, "Show questions statistics");
        options.addOption("h", false, "Show this help");
    }

    private void showGrades(HashMap<Long, Grade> grades, AssessmentSerializer.Format format) {
        System.out.println("Assessment's grades:");
        System.out.println(AssessmentSerializer.serializeGrades(grades, format) + "\n");

    }

    private void showHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java Quizzer [options]", options);
    }

    private void showStatistics(HashMap<Long, Integer> statistics, AssessmentSerializer.Format format) {
        System.out.println("Assessment's statistics:");
        System.out.println(AssessmentSerializer.serializeStatistics(statistics, format) + "\n");
    }

    public static void main(String[] args) {
        Quizzer quizzer = new Quizzer();
        try {
            quizzer.parseArguments(args);
        } catch (Exception e) {
            System.err.append("There was a problem while executing the program: " + e.getMessage());
        }
    }

}
