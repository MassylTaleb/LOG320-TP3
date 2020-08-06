public enum GameScores {

    SCORE_WIN(1000000000), SCORE_LOST(-1000000000);

    private final int score;

    GameScores(int score) {
        this.score = score;
    }

    public int getValue(){return score;}
}
