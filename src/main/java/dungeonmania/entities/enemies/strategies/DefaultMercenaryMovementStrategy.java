package dungeonmania.entities.enemies.strategies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class DefaultMercenaryMovementStrategy implements MovementStrategy {
    @Override
    public Position nextPosition(Game game, Enemy enemy) {
        GameMap map = game.getMap();
        Player player = game.getPlayer();

        return map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
    }

}
