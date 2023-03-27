public class ScoreBoard implements EnemyObserver {

    private int score;
    public ScoreBoard() {
        this.score = 0;
    }
    public void onEnemyKilled(Enemy enemy) {
        score += enemy.getPoints();

    }

    public int getScore() {
        return score;
    }
}