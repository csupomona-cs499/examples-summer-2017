package edu.cpp.l01_demo_basics.data;

/**
 * Created by yusun on 6/21/17.
 */

public class QuizQuestion {

    private String description;
    private boolean correctAnswer;

    public QuizQuestion() {
    }

    public QuizQuestion(String description, boolean correctAnswer) {
        this.description = description;
        this.correctAnswer = correctAnswer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
