package dungeonmania.goals;

import dungeonmania.Game;

public class GoalTreasure implements GoalStrategy {
    private int target;

    public GoalTreasure(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getCollectedTreasureCount() >= target;
    }

    @Override
    public String toString(Game game) {
        return ":treasure";
    }
}
