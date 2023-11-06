package dungeonmania.entities.enemies;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Enemy implements Interactable {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position, 1, 0);
    }

    public void spawn(Game game) {
        game.spawnZombie(game, this);
    }

    @Override
    public void interact(Player player, Game game) {
        player.useWeapon(game);
    }

    @Override
    public void move(Game game) {
        return;
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }

    public List<Position> getCardinallyAdjacentPositions() {
        return getPosition().getCardinallyAdjacentPositions();
    }
}
