package vn.com.orismaster;

public class Game {
    private Puzzle question;
    private Puzzle answer;
    private int score;

    public Game(Puzzle question, Puzzle answer) {
        this.question = question;
        this.answer = answer;
        this.score = question.difficultyScore(answer);
    }

    public Puzzle getAnswer() {
        return answer;
    }

    public Puzzle getQuestion() {
        return question;
    }

    public int getScore() {
        return score;
    }
}
