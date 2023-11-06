package dungeonmania.goals;

import dungeonmania.Game;

public class GoalEnemies implements GoalStrategy {
    @Override
    public boolean achieved(Game game) {
        // TODO implement
        return false;
    }

    @Override
    public String toString(Game game) {
        return ":enemies";
    }
}
