package dungeonmania.entities.entityHelpers;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface OnMovedAway {
    public void onMovedAway(GameMap map, Entity entity);
}
