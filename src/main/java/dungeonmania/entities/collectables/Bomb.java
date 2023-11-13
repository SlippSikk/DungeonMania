package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.conductors.Switch;
import dungeonmania.entities.conductors.Wire;
import dungeonmania.map.GameMap;

public class Bomb extends CollectableEntity {
    public enum State {
        SPAWNED, INVENTORY, PLACED
    }

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;

    private boolean isLogical;
    private String logic;
    private boolean allAdjWiresSameState;

    private List<Switch> subs = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();

    public Bomb(Position position, int radius, String logic) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
        this.isLogical = true;
        this.logic = logic;
        this.allAdjWiresSameState = true;
    }

    public Bomb(Position position, int radius) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
        this.isLogical = false;
    }

    public void subscribe(Switch s) {
        this.subs.add(s);
    }

    public void addWire(Wire w) {
        if (!wires.contains(w))
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
                wires.forEach(w -> w.removeLogicalBomb(this));
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
            List<Entity> switchEntities = map.getEntities(node).stream().filter(e -> (e instanceof Switch))
                    .collect(Collectors.toList());
            List<Entity> wireEntities = map.getEntities(node).stream().filter(e -> (e instanceof Wire))
                    .collect(Collectors.toList());
            switchEntities.stream().map(Switch.class::cast).forEach(s -> s.subscribe(this, map));
            switchEntities.stream().map(Switch.class::cast).forEach(s -> this.subscribe(s));
            wireEntities.stream().map(Wire.class::cast).forEach(w -> w.addLogicalBomb(this, map));
            wireEntities.stream().map(Wire.class::cast).forEach(w -> this.addWire(w));
            if (isLogical()) {
                this.notifyLogic(map);
            }
        });
    }

    public void explode(GameMap map) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                List<Entity> entities = map.getEntities(new Position(i, j));
                entities = entities.stream().filter(e -> !(e instanceof Player)).collect(Collectors.toList());
                for (Entity e : entities) {
                    map.destroyEntity(e);
                    if (e instanceof Wire)
                        ((Wire) e).clearLists();
                }
            }
        }
    }

    public boolean checkLogic() {
        switch (getLogic()) {
        case "and":
            return wires.stream().allMatch(w -> w.isActivated())
                    && wires.stream().filter(w -> w.isActivated()).count() > 1;
        case "or":
            return wires.stream().anyMatch(w -> w.isActivated());
        case "xor":
            return wires.stream().filter(w -> w.isActivated()).count() == 1;
        case "co_and":
            return wires.stream().allMatch(w -> w.isActivated()) && isallAdjWiresSameState()
                    && wires.stream().filter(w -> w.isActivated()).count() > 1;
        default:
            return false;
        }
    }

    public void checkAllWiresSameState() {
        boolean bool = wires.stream().allMatch(w -> w.isActivated()) || wires.stream().allMatch(w -> !w.isActivated());
        setallAdjWiresSameState(bool);
    }

    public boolean isallAdjWiresSameState() {
        return allAdjWiresSameState;
    }

    public void setallAdjWiresSameState(boolean allAdjWiresSameState) {
        this.allAdjWiresSameState = allAdjWiresSameState;
    }

    public State getState() {
        return state;
    }

    public String getLogic() {
        return logic;
    }

    public boolean isLogical() {
        return isLogical;
    }

    public List<Wire> getWires() {
        return wires;
    }
}
