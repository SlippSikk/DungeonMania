package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.conductors.Switch;

public class GoalBoulders implements GoalStrategy {
    @Override
    public boolean achieved(Game game) {
        return game.getMap().getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
    }

    @Override
    public String toString(Game game) {
        return ":boulders";
    }
}
