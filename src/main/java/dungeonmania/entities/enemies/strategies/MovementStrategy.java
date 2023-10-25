package dungeonmania.entities.enemies.strategies;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public interface MovementStrategy {
    Position nextPosition(Game game, Enemy enemy);

}
