package com.davidmogar.quizzer.deserializers;


import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.questions.MultichoiceQuestion;
import com.davidmogar.quizzer.domain.questions.Question;
import com.davidmogar.quizzer.domain.questions.TrueFalseQuestion;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class QuestionsDeserializer implements JsonDeserializer<HashMap<Long, Question>> {

    @Override
    public HashMap<Long, Question> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Long, Question> questions = new HashMap<>();

        JsonElement rootElement = jsonElement.getAsJsonObject().get("questions");

        for (JsonElement element : rootElement.getAsJsonArray()) {
            Question question = createQuestion(element.getAsJsonObject());
            if (question != null) {
                questions.put(question.getId(), question);
            }
        }

        return questions;
    }

    private Question createQuestion(JsonObject object) {
        Question question = null;

        try {
            switch (object.get("type").getAsString()) {
                case "multichoice":
                    question = createMultichoiceQuestion(object);
                    break;
                case "truefalse":
                    question = createTrueFalseQuestion(object);
            }
        } catch(Exception e) {
            // Impossible to parse object. Skip it
        }

        return question;
    }

    private MultichoiceQuestion createMultichoiceQuestion(JsonObject object) {
        MultichoiceQuestion question = null;

        try {

            question = new MultichoiceQuestion(object.get("id").getAsLong(), object.get("questionText").getAsString());

            for (JsonElement element : object.get("alternatives").getAsJsonArray()) {
                JsonObject alternativeObject = element.getAsJsonObject();
                question.addAlternative(alternativeObject.get("code").getAsLong(),
                        alternativeObject.get("text").getAsString(), alternativeObject.get("value").getAsDouble());

            }
        } catch(Exception e) {
            // Impossible to parse object. Skip it
        }

        return question;
    }

    private TrueFalseQuestion createTrueFalseQuestion(JsonObject object) {
        TrueFalseQuestion question = null;

        try {
            question = new TrueFalseQuestion(object.get("id").getAsLong(), object.get("questionText").getAsString());
            question.setCorrect(object.get("correct").getAsBoolean());
            question.setValueCorrect(object.get("valueOK").getAsDouble());
            question.setValueIncorrect(object.get("valueFailed").getAsDouble());
            question.setFeedback(object.get("feedback").getAsString());
        } catch (Exception e) {
            // Impossible to parse object. Skip it
        }

        return question;
    }

}
