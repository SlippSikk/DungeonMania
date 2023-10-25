package dungeonmania.entities.enemies.strategies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class AlliedMovementStrategy implements MovementStrategy {
    private Mercenary mercenary;

    public AlliedMovementStrategy(Mercenary mercenary) {
        this.mercenary = mercenary;
    }

    @Override
    public Position nextPosition(Game game, Enemy enemy) {
        Position nextPos;
        GameMap map = game.getMap();
        Player player = game.getPlayer();

        nextPos = mercenary.isAdjacentToPlayer() ? player.getPreviousDistinctPosition()
                : map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);

        if (!mercenary.isAdjacentToPlayer() && Position.isAdjacent(player.getPosition(), nextPos))
            mercenary.setAdjacentToPlayer(true);
        return nextPos;
    }

}
