public class MiniMax {
    List<Movement> possibleMovements;

    public MiniMax(List<Movement> possibleMovements) {
        this.possibleMovements = possibleMovements;
    }

    public int minMax(int level, boolean player) {
        int maxScore = 0, currentScore, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        boolean game_over = false;
        if (game_over || level == 0) {
            return currentScore = 0;
        }
        Movement bestMove;
        if (player) { // =Joueur
            //maxScore = (int) Double.NEGATIVE_INFINITY;
            for (Movement m : possibleMovements) {
                // Player.move()
                currentScore = minMax(level + 1, false);
                // annuler le coup m;
                if (currentScore > max) {
                    max = currentScore;
                    bestMove = m;
                }
            }
        } else { // =Ordinateur
            //maxScore = (int) Double.POSITIVE_INFINITY;
            for (Movement m : possibleMovements) {
                // Player.move()
                currentScore = minMax(level + 1, true);
                // annuler le coup m;
                if (currentScore < min) {
                    min = currentScore;
                    bestMove = m;
                }
            }
        }
        return maxScore;
    }

    public int evaluate() {
        return 0;
    }

}