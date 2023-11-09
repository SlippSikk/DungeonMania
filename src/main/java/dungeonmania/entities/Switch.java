package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.entityHelpers.OnMovedAway;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Entity implements OnMovedAway {
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void addWire(Wire w) {
        if (!wires.contains(w))
            wires.add(w);
    }

    public void removeWire(Wire w) {
        wires.remove(w);
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            map.checkCoAnd();
            wires.stream().forEach(w -> w.notifyActivated(map));
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            map.checkCoAnd();
            wires.stream().forEach(w -> w.notifyDeactivated(map));
        }
    }

    public boolean isActivated() {
        return activated;
    }
}
