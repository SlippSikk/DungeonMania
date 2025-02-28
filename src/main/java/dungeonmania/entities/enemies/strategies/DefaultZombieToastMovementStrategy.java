package dungeonmania.entities.enemies.strategies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class DefaultZombieToastMovementStrategy implements MovementStrategy {
    private Random randGen = new Random();

    @Override
    public Position nextPosition(Game game, Enemy enemy) {
        Position nextPos;
        GameMap map = game.getMap();
        List<Position> pos = enemy.getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = enemy.getPosition();
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
        }

        return nextPos;
    }

}
