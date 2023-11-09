package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
import dungeonmania.entities.Wire;
import dungeonmania.map.GameMap;

public class Bomb extends CollectableEntity {
    public enum State {
        SPAWNED, INVENTORY, PLACED
    }

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;

    private String logic;
    private boolean allPrevWiresUnactivated;

    private List<Switch> subs = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();

    public Bomb(Position position, int radius, String logic) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
        this.logic = logic;
        this.allPrevWiresUnactivated = true;
    }

    public void subscribe(Switch s) {
        this.subs.add(s);
    }

    public void addWire(Wire w) {
        wires.add(w);
    }

    public void removeWire(Wire w) {
        wires.remove(w);
    }

    public void notify(GameMap map) {
        explode(map);
    }

    public void notifyLogic(GameMap map) {
        if (checkLogic()) {
            explode(map);
        }
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return;

        if (entity instanceof Player) {
            boolean wasPickedUp = onPickup(map, (Player) entity);
            if (wasPickedUp) {
                subs.forEach(s -> s.unsubscribe(this));
                state = State.INVENTORY;
            }
        }
    }

    public void onPutDown(GameMap map, Position p) {
        translate(Position.calculatePositionBetween(getPosition(), p));
        map.addEntity(this);
        this.state = State.PLACED;
        List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(e -> (e instanceof Switch))
                    .collect(Collectors.toList());
            entities.stream().map(Switch.class::cast).forEach(s -> s.subscribe(this, map));
            entities.stream().map(Switch.class::cast).forEach(s -> this.subscribe(s));
        });
    }

    public void explode(GameMap map) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                List<Entity> entities = map.getEntities(new Position(i, j));
                entities = entities.stream().filter(e -> !(e instanceof Player)).collect(Collectors.toList());
                for (Entity e : entities)
                    map.destroyEntity(e);
            }
        }
    }

    public boolean checkLogic() {
        switch (getLogic()) {
        case "and":
            return wires.stream().allMatch(wire -> wire.isActive());
        case "or":
            return wires.stream().anyMatch(wire -> wire.isActive());
        case "xor":
            return wires.stream().filter(wire -> wire.isActive()).count() == 1;
        case "co_and":
            return wires.stream().allMatch(wire -> wire.isActive()) && isAllPrevWiresUnactivated();
        default:
            return false;
        }
    }

    public State getState() {
        return state;
    }

    public String getLogic() {
        return logic;
    }

    public boolean isAllPrevWiresUnactivated() {
        return allPrevWiresUnactivated;
    }
}
