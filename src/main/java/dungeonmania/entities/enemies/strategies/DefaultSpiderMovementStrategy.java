package dungeonmania.entities.enemies.strategies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.util.Position;

public class DefaultSpiderMovementStrategy implements MovementStrategy {
    @Override
    public Position nextPosition(Game game, Enemy enemy) {
        Spider spider = (Spider) enemy;
        Position nextPos = spider.getMovementTrajectory().get(spider.getNextPositionElement());
        List<Entity> entities = game.getMap().getEntities(nextPos);

        if (entities != null && entities.size() > 0 && entities.stream().anyMatch(e -> e instanceof Boulder)) {
            spider.setForward(!spider.isForward());
            spider.updateNextPosition();
            spider.updateNextPosition();
        }
        nextPos = spider.getMovementTrajectory().get(spider.getNextPositionElement());
        entities = game.getMap().getEntities(nextPos);

        if (entities == null || entities.size() == 0
                || entities.stream().allMatch(e -> e.canMoveOnto(game.getMap(), enemy))) {
            spider.updateNextPosition();
            return nextPos;
        }

        return spider.getPosition(); // Return current position if spider can't move
    }
}
