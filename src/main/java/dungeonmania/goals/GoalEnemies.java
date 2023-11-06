package dungeonmania.goals;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class GoalEnemies implements GoalStrategy {
    private int target;

    public GoalEnemies(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        List<ZombieToastSpawner> spawners = game.getMap().getEntities(ZombieToastSpawner.class);
        if (spawners.isEmpty()) {
            if (game.getEnemiesKilled() >= target) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(Game game) {
        return ":enemies";
    }
}
