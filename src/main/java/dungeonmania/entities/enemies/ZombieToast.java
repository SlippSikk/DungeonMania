package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.enemies.strategies.DefaultZombieToastMovementStrategy;
import dungeonmania.entities.enemies.strategies.InvincibilityPotionMovementStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
        setMovementStrategy(new DefaultZombieToastMovementStrategy());
    }

    public void movement(Game game) {
        GameMap map = game.getMap();
        if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            setMovementStrategy(new InvincibilityPotionMovementStrategy());
        } else {
            setMovementStrategy(new DefaultZombieToastMovementStrategy());
        }
        move(game);

    }

}
