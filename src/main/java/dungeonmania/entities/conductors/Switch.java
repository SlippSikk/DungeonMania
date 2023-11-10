package dungeonmania.entities.conductors;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.entityHelpers.OnMovedAway;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Conductor implements OnMovedAway {
    private List<Bomb> bombs = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (isActivated()) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            setActivated(true);
            map.checkCoAnd();
            getWires().stream().forEach(w -> w.notifyActivated(map));
            getLogicalEntities().stream().forEach(le -> le.update());
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            setActivated(false);
            map.checkCoAnd();
            getWires().stream().forEach(w -> w.notifyDeactivated(map));
            getLogicalEntities().stream().forEach(le -> le.update());
        }
    }
}
