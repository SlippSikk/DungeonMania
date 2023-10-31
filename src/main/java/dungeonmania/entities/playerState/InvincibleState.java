package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;

public class InvincibleState extends PlayerState {
    public InvincibleState(Player player) {
        super(player, true, false);
    }

    // Possible state change includes other states that aren't 'this'. if 'this' then simply remain.
    @Override
    public void applyPotion(Potion potion) {
        Player player = getPlayer();
        if (potion == null) {
            player.changeState(new BaseState(player));
        } else if (potion instanceof InvisibilityPotion) {
            player.changeState(new InvisibleState(player));
        }
    }
}
