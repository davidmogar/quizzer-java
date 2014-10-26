package com.davidmogar.quizzer;

import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.Test;
import com.davidmogar.quizzer.loaders.AssessmentLoader;
import com.davidmogar.quizzer.loaders.TestsLoader;
import com.davidmogar.quizzer.serializers.AssessmentSerializer;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Helps to validate assessments, parsing questions and answers json files and generating new files with students'
 * grades and questions statistics.
 */
public class Quizzer {

    private Options options;

    public Quizzer() {
        options = new Options();

        setCommandLineOptions();
    }

    /**
     * Calculate assessments' grades given the urls to the questions and answers files.
     *
     * @param questionsUrl URL to the questions file
     * @param answersUrl URL to the answers file
     * @return a new assessment with containing questions, answers and calculated grades
     */
    public Assessment calculateGrades(URL questionsUrl, URL answersUrl) {
        Assessment assessment = null;

        try {
            assessment = AssessmentLoader.loadAssessment(questionsUrl, answersUrl, null);
            if (assessment != null) {
                assessment.calculateGrades();
            }
        } catch (IOException e) {
            // Return default value
        }

        return assessment;
    }

    /**
     * Parse command line arguments and decide what method to call next.
     *
     * @param args command line arguments
     * @throws IOException if there is any error while validating tests (-t argument only)
     */
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

    /**
     * Converts the format passed as argument to a value of Format enumeration.
     *
     * @param formatString string received as argument containing the desired format
     * @return a value of Format enumeration
     */
    private AssessmentSerializer.Format parseFormat(String formatString) {
        AssessmentSerializer.Format format;

        try {
            format = AssessmentSerializer.Format.valueOf(formatString.toUpperCase());
        } catch(Exception e) {
            format = AssessmentSerializer.Format.JSON;
        }

        return format;
    }

    /**
     * Set the command line options accepted by the program.
     */
    private void setCommandLineOptions() {
        options.addOption("q", true, "URL to the questions file");
        options.addOption("a", true, "URL to the answers file");
        options.addOption("o", true, "Generate output in the specified format (json or xml)");
        options.addOption("t", true, "Validate assessments in tests file");
        options.addOption("s", false, "Show questions statistics");
        options.addOption("h", false, "Show this help");
    }

    /**
     * Show the grades received as argument in the format specified.
     *
     * @param grades grades to show
     * @param format format of the output
     */
    private void showGrades(HashMap<Long, Grade> grades, AssessmentSerializer.Format format) {
        System.out.println("Assessment's grades:");
        System.out.println(AssessmentSerializer.serializeGrades(grades, format) + "\n");

    }

    /**
     * Shows the help message of the program with all the available options.
     */
    private void showHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java Quizzer [options]", options);
    }

    /**
     * Show the statistics received as argument in the format specified.
     *
     * @param statistics statistics to show
     * @param format format of the output
     */
    private void showStatistics(HashMap<Long, Integer> statistics, AssessmentSerializer.Format format) {
        System.out.println("Assessment's statistics:");
        System.out.println(AssessmentSerializer.serializeStatistics(statistics, format) + "\n");
    }

    /**
     * Validate tests inside of the file referenced by the URL argument.
     *
     * @param url URL to the tests file
     * @throws IOException if there is an error while loading tests
     */
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

    public static void main(String[] args) {
        Quizzer quizzer = new Quizzer();
        try {
            quizzer.parseArguments(args);
        } catch (Exception e) {
            System.err.append("There was a problem while executing the program: " + e.getMessage());
        }
    }

}
