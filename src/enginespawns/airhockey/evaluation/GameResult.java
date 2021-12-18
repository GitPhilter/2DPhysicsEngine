package enginespawns.airhockey.evaluation;

public class GameResult {
    private final int homeGoals;
    private final int awayGoals;

    public GameResult(int homeGoals, int awayGoals){
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }
}
