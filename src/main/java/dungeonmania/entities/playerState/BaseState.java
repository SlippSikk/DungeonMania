package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class BaseState extends PlayerState {
    public BaseState(Player player) {
        super(player, false, false);
    }

    // Possible state change includes other states that aren't 'this'. if 'this' then simply remain.
    // @Override
    // public void applyPotion(Potion potion) {
    //     if (potion == null) {
    //         return;
    //     }
    //     Player player = getPlayer();
    //     if (potion instanceof InvincibilityPotion) {
    //         player.changeState(new InvincibleState(player));
    //     } else if (potion instanceof InvisibilityPotion) {
    //         player.changeState(new InvisibleState(player));
    //     }
    // }
}
