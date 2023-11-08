package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.Potion;

public abstract class PlayerState {
    private Player player;
    private boolean isInvincible = false;
    private boolean isInvisible = false;

    PlayerState(Player player, boolean isInvincible, boolean isInvisible) {
        this.player = player;
        this.isInvincible = isInvincible;
        this.isInvisible = isInvisible;
    }

    public boolean isInvincible() {
        return isInvincible;
    };

    public boolean isInvisible() {
        return isInvisible;
    };

    public Player getPlayer() {
        return player;
    }

    // Single method to handle potion effect and transition state
    public void applyPotion(Potion potion) {
        if (potion == null) {
            player.changeState(new BaseState(player));
        } else {
            potion.applyEffect(getPlayer());
        }
    }
}
