package dungeonmania.entities.enemies.strategies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class InvisibilityPotionMovementStrategy implements MovementStrategy {
    @Override
    public Position nextPosition(Game game, Enemy enemy) {
        Position nextPos;
        GameMap map = game.getMap();

        // Move random
        Random randGen = new Random();
        List<Position> pos = enemy.getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = enemy.getPosition();
            map.moveTo(enemy, nextPos);
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
            map.moveTo(enemy, nextPos);
        }
        return nextPos;
    }

}
