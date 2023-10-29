package dungeonmania.entities.inventory;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;

/**
 * A marker interface for InventoryItem
 */
public interface InventoryItem {
    default boolean onPickup(GameMap map, Player player) {
        boolean result = player.pickUp((Entity) this);
        if (result) {
            map.destroyEntity((Entity) this);
        }
        return result;
    }
}
