package com.davidmogar.quizzer;

import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.Test;
import com.davidmogar.quizzer.loaders.AssessmentLoader;
import com.davidmogar.quizzer.loaders.TestsLoader;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import static spark.Spark.get;

public class Main {

    private Options options;

    public Main() {
        options = new Options();

        setCommandLineOptions();
    }

    public HashMap<Long, Grade> calculateGrades(URL questionsUrl, URL answersURL) {
        HashMap<Long, Grade> grades = null;

        try {
            Assessment assessment = AssessmentLoader.loadAssessment(questionsUrl, answersURL, null);
            if (assessment != null) {
                assessment.calculateGrades();
                grades = assessment.getGrades();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grades;
    }

    public boolean validateAssessments(URL url) throws IOException {
        boolean valid = true;

        for (Test test : TestsLoader.loadTests(url)) {
            Assessment assessment = AssessmentLoader.loadAssessment(test.getQuestionsUrl(), test.getAnswersUrl(),
                    test.getGradesUrl());
            if (assessment != null) {
                valid = assessment.validateGrades();
                if (!valid) {
                    break;
                }
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
            } else {
                if (commandLine.hasOption("h")) {
                    showHelp();
                } else {
                    if (commandLine.hasOption("t")) {
                        boolean valid = validateAssessments(new URL(commandLine.getOptionValue("t")));
                        System.out.println(valid ? "All tests OK" : "Tests failed");
                    } else {
                        if (commandLine.hasOption("a") && commandLine.hasOption("q")) {
                            HashMap<Long, Grade> grades = calculateGrades(new URL(commandLine.getOptionValue("q")),
                                    new URL(commandLine.getOptionValue("a")));

                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCommandLineOptions() {
        options.addOption("q", "questions", true, "URL to the questions file");
        options.addOption("a", "answers", true, "URL to the assessments file");
        options.addOption("o", "output", false, "Generate output");
        options.addOption("t", "tests", true, "Validate assessments in tests file");
        options.addOption("h", "help", false, "Show this help");
    }

    private void showHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Main", options);
    }

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.parseArguments(args);
        } catch (IOException e) {
            System.err.append("There was a problem trying to process the url: " + e.getMessage());
        }
    }

}
