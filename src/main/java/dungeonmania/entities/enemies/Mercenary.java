package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.strategies.AlliedMovementStrategy;
import dungeonmania.entities.enemies.strategies.DefaultMercenaryMovementStrategy;
import dungeonmania.entities.enemies.strategies.InvincibilityPotionMovementStrategy;
import dungeonmania.entities.enemies.strategies.InvisibilityPotionMovementStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;

    private boolean isMindControlled = false;

    private int endMindControlTick = 0;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
        setMovementStrategy(new DefaultMercenaryMovementStrategy());
    }

    public void setAdjacentToPlayer(boolean isAdjacent) {
        this.isAdjacentToPlayer = isAdjacent;
    }

    public boolean isAdjacentToPlayer() {
        return isAdjacentToPlayer;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {

        // Get the mercenary's position
        int mercX = this.getPosition().getX();
        int mercY = this.getPosition().getY();

        // Get the player's position
        int playerX = player.getPosition().getX();
        int playerY = player.getPosition().getY();

        // Check if the player is within the bribe radius
        boolean isWithinBribeRadius = Math.abs(mercX - playerX) <= bribeRadius
                && Math.abs(mercY - playerY) <= bribeRadius;

        return isWithinBribeRadius && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * check whether the current merc can be mind controlled
     * @param player
     * @return
     */
    private boolean canBeControlled(Player player) {
        return player.countEntityOfType(Sceptre.class) >= 1 && !isMindControlled;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    /*
     * mind control the mercenary
     */
    private void mindControl(Player player, Game game) {
        Sceptre s = player.getSceptre();
        s.use(game);
        isMindControlled = true;
        endMindControlTick = game.getTick() + s.getDuration();
    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;

        if (canBeBribed(player)) {
            bribe(player);

        } else if (canBeControlled(player)) {
            mindControl(player, game);
        }

        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            isAdjacentToPlayer = true;

    }

    @Override
    public void move(Game game) {
        Potion potion = game.getEffectivePotion();
        if (isMindControlled) {
            if (game.getTick() == endMindControlTick) {
                isMindControlled = false;
                allied = false;
            }
        }

        if (allied) {
            setMovementStrategy(new AlliedMovementStrategy());
        } else if (potion instanceof InvisibilityPotion) {
            setMovementStrategy(new InvisibilityPotionMovementStrategy());
        } else if (potion instanceof InvincibilityPotion) {
            setMovementStrategy(new InvincibilityPotionMovementStrategy());
        } else {
            // Follow hostile
            setMovementStrategy(new DefaultMercenaryMovementStrategy());
        }
        super.move(game);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && (canBeBribed(player) || canBeControlled(player));
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }
}
