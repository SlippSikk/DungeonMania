package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public class InvisibleState extends PlayerState {
    public InvisibleState(Player player) {
        super(player, false, true);
    }

    // // Possible state change includes other states that aren't 'this'. if 'this' then simply remain.
    // @Override
    // public void applyPotion(Potion potion) {
    //     Player player = getPlayer();
    //     if (potion == null) {
    //         player.changeState(new BaseState(player));

    //     } else if (potion instanceof InvincibilityPotion) {
    //         player.changeState(new InvincibleState(player));
    //     }
    // }
}
