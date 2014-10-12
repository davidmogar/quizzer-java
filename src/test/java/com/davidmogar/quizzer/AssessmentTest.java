package com.davidmogar.quizzer;

import com.davidmogar.quizzer.domain.Answer;
import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.questions.MultichoiceQuestion;
import com.davidmogar.quizzer.domain.questions.NumericalQuestion;
import com.davidmogar.quizzer.domain.questions.Question;
import com.davidmogar.quizzer.domain.questions.TrueFalseQuestion;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class AssessmentTest {

    private Assessment assessment;

    public AssessmentTest() {
        assessment = new Assessment();

        HashMap<Long, Question> questions = new HashMap<Long, Question>();
        HashMap<Long, List<Answer>> answers = new HashMap<Long, List<Answer>>();

        MultichoiceQuestion multichoiceQuestion = new MultichoiceQuestion(1, "Question 1");
        multichoiceQuestion.addAlternative(1, "Alternative 1", 0);
        multichoiceQuestion.addAlternative(2, "Alternative 2", 0.75);
        questions.put(1L, multichoiceQuestion);

        NumericalQuestion numericalQuestion = new NumericalQuestion(3, "Question 2");
        numericalQuestion.setCorrect(4.3);
        numericalQuestion.setValueCorrect(1);
        numericalQuestion.setValueIncorrect(-0.5);
        questions.put(2L, numericalQuestion);

        TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion(2, "Question 3");
        trueFalseQuestion.setCorrect(true);
        trueFalseQuestion.setValueCorrect(1);
        trueFalseQuestion.setValueIncorrect(-0.25);
        questions.put(3L, trueFalseQuestion);

        List<Answer> studentAnswers = new ArrayList<>();
        studentAnswers.add(new Answer(1, "2"));
        studentAnswers.add(new Answer(2, "4.3"));
        studentAnswers.add(new Answer(3, "true"));
        answers.put(1L, studentAnswers);

        studentAnswers = new ArrayList<>();
        studentAnswers.add(new Answer(1, "1"));
        studentAnswers.add(new Answer(2, "2"));
        studentAnswers.add(new Answer(3, "false"));
        answers.put(2L, studentAnswers);

        assessment.setQuestions(questions);
        assessment.setAnswers(answers);
    }

    @Test
    public void testCalculateGrades() throws Exception {
        assessment.calculateGrades();

        assertTrue("Unexpected grades size", assessment.getGrades().size() == 2);
        assertEquals("Unexpected grade value for id 1", assessment.getGrades().get(1L).getGrade(), 2.75, 0.05);
        assertEquals("Unexpected grade value for id 2", assessment.getGrades().get(2L).getGrade(), -0.75, 0.05);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCalculateStudentGrade() throws Exception {
        assertEquals("Unexpected grade value for id 1", assessment.calculateStudentGrade(1), 2.75, 0.05);
        assertEquals("Unexpected grade value for id 2", assessment.calculateStudentGrade(2), -0.75, 0.05);
    }

    @Test
    public void testValidateGrade() throws Exception {
        assertTrue("Unexpected grade validation result for id 1", assessment.validateGrade(new Grade(1, 2.75)));
        assertFalse("Unexpected grade validation result for id 2", assessment.validateGrade(new Grade(1, 0.75)));
    }

    @Test
    public void testValidateGrades() throws Exception {
        HashMap<Long, Grade> grades = new HashMap<>();
        grades.put(1L, new Grade(1, 2.75));
        grades.put(2L, new Grade(2, -0.75));

        assessment.setGrades(grades);
        assertTrue("Unexpected validation result. Expected a valid grade", assessment.validateGrades());

        grades = new HashMap<>();
        grades.put(1L, new Grade(1, 2.75));
        grades.put(2L, new Grade(2, 0.25));

        assessment.setGrades(grades);
        assertFalse("Unexpected validation result. Expected a not valid grade", assessment.validateGrades());
    }
}