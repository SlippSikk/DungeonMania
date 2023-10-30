package dungeonmania.goals;

import dungeonmania.Game;

public class Goal {
    private GoalStrategy goalStrategy;

    public Goal(GoalStrategy goalStrategy) {
        this.goalStrategy = goalStrategy;
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;
        return goalStrategy.achieved(game);
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return goalStrategy.toString(game);
    }
}
