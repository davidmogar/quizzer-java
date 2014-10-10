package com.davidmogar.quizzer.deserializers;

import com.davidmogar.quizzer.deserializers.AssessmentDeserializer;
import com.davidmogar.quizzer.domain.Answer;
import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.questions.MultichoiceQuestion;
import com.davidmogar.quizzer.domain.questions.Question;
import com.davidmogar.quizzer.domain.questions.TrueFalseQuestion;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AssessmentDeserializerTest {

    private static final String QUESTIONS_JSON = "{ \"questions\": [ { \"type\": \"multichoice\", \"id\" : 1, " +
            "\"questionText\": \"Scala fue creado por...\", \"alternatives\": [ { \"text\": \"Martin Odersky\", " +
            "\"code\": 1, \"value\": 1 }, { \"text\": \"James Gosling\", \"code\": 2, \"value\": -0.25 }, " +
            "{ \"text\": \"Guido van Rossum\", \"code\": 3, \"value\": -0.25 } ] }, { \"type\" : \"truefalse\", " +
            "\"id\" : 2, \"questionText\": \"El creador de Ruby es Yukihiro Matsumoto\", \"correct\": true, " +
            "\"valueOK\": 1, \"valueFailed\": -0.25, \"feedback\": \"Yukihiro Matsumoto es el principal " +
            "desarrollador de Ruby desde 1996\" } ] }";

    private static final String ANSWERS_JSON = "{ \"items\": [ { \"studentId\": 234 , " +
            "\"answers\": [ { \"question\" : 1, \"value\": 1 }, { \"question\" : 2, \"value\": false } ] }, " +
            "{ \"studentId\": 245 , \"answers\": [ { \"question\" : 1, \"value\": 1 }, { \"question\" : 2, " +
            "\"value\": true } ] }, { \"studentId\": 221 , \"answers\": [ { \"question\" : 1, \"value\": 2 } ] } ] }";

    private static final String GRADES_JSON = "{ \"scores\": [ { \"studentId\": 234, \"value\": 0.75 } ," +
            "{ \"studentId\": 245, \"value\": 2.0 } , { \"studentId\": 221, \"value\": 0.75 } ] }";

    @Test
    public void testDeserializeAnswers() throws Exception {
        HashMap<Long, List<Answer>> answers = AssessmentDeserializer.deserializeAnswers(ANSWERS_JSON);

        assertNotNull("Answers is null", answers);
        assertTrue("Unexpected size for answers map", answers.size() == 3);
        assertTrue("Unexpected size for answers of student id 234", answers.get(234L).size() == 2);
        assertTrue("Unexpected size for answers of student id 245", answers.get(245L).size() == 2);
        assertTrue("Unexpected size for answers of student id 221", answers.get(221L).size() == 1);
    }

    @Test
    public void testDeserializeGrades() throws Exception {
        HashMap<Long, Grade> grades = AssessmentDeserializer.deserializeGrades(GRADES_JSON);

        assertNotNull("Grades is null", grades);
        assertTrue("Unexpected size for grades map", grades.size() == 3);
        assertEquals("Grade value for id 234 doesn't match", grades.get(234L).getGrade(), 0.75, 0.05);
        assertEquals("Grade value for id 245 doesn't match", grades.get(245L).getGrade(), 2, 0.05);
        assertEquals("Grade value for id 221 doesn't match", grades.get(221L).getGrade(), 0.75, 0.05);
    }

    @Test
    public void testDeserializeQuestions() throws Exception {
        HashMap<Long, Question> questions = AssessmentDeserializer.deserializeQuestions(QUESTIONS_JSON);

        assertNotNull("Questions is null", questions);
        assertTrue("Unexpected size for questions map", questions.size() == 2);
        assertTrue("Unexpected type for question 1", questions.get(1L) instanceof MultichoiceQuestion);
        assertTrue("Unexpected type for question 2", questions.get(2L) instanceof TrueFalseQuestion);
        assertTrue("Unexpected value for questions 2", ((TrueFalseQuestion) questions.get(2L)).isCorrect());

        HashMap<Long, List<Answer>> answers = AssessmentDeserializer.deserializeAnswers(ANSWERS_JSON);
        assertEquals("Unexpected score for answer 1 of student 234",
                questions.get(1L).getScore(answers.get(234L).get(0)), 1, 0.05);
    }
}