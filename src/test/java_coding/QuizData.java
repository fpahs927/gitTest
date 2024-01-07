package test.java_coding;

import java.util.ArrayList;

public class QuizData {
    String quest;
    String answer;
    int score;

    public QuizData(String quest, String answer, int score) {
        this.quest = quest;
        this.answer = answer;
        this.score= score;
        //Float.parseFloat(num.replace("점", "").replace("수", "").trim());
    }
    public void QuizSet(String quest, String answer, int score){
        this.quest = quest;
        this.answer = answer;
        this.score= score;
    }

    @Override
    public String toString() {
        return quest;
    }
    public static void ParsingData(ArrayList<QuizData> data, ArrayList<String> quizData) {
        String quest = null;
        String answer = null;
        int num;
        for (String line : quizData) {
            if (line.startsWith("문제:")) {
                quest = line.trim();
            } else if (line.startsWith("답:")) {
                answer = line.trim();
            } else if (line.startsWith("점수:")) {
                num = 10;
                if (quest != null && answer != null)
                    data.add(new QuizData(quest, answer, num));
            }
        }
    }

    public boolean answerCheck(String answer) {
        if(answer == this.answer){
            return true;
        }
        return false;
    }
}