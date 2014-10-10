package com.davidmogar.quizzer.deserializers;

import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestsDeserializerTest {

    private static final String TESTS_JSON = "{ \"tests\": [ { \"type\": \"score\", " +
            "\"quizz\" : \"http://di002.edv.uniovi.es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/quizz1.json\", " +
            "\"assessment\": \"http://di002.edv.uniovi.es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/assessment1" +
            ".json\", \"scores\": \"http://di002.edv.uniovi.es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/scores11" +
            ".json\" }, { \"type\": \"score\", \"quizz\" : \"http://di002.edv.uniovi" +
            ".es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/quizz1.json\", \"assessment\": \"http://di002.edv.uniovi" +
            ".es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/assessment2.json\", " +
            "\"scores\": \"http://di002.edv.uniovi.es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/scores12.json\" } ]" +
            " }";

    @Test
    public void testDeserialize() throws Exception {
        List<com.davidmogar.quizzer.domain.Test> tests = TestsDeserializer.deserialize(TESTS_JSON);

        assertNotNull("Answers is null", tests);
        assertTrue("Unexpected size for answers list", tests.size() == 2);
        assertTrue("Unexpected value for questions url of first test", tests.get(0).getQuestionsUrl().equals
                (new URL("http://di002.edv.uniovi.es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/quizz1.json")));
        assertTrue("Unexpected value for grades url of second test", tests.get(1).getGradesUrl().equals
                (new URL("http://di002.edv.uniovi.es/~labra/cursos/MIW/POO/Curso1415/Ejercicios/scores12.json")));
    }
}