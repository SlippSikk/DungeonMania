package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logicalEntities.LogicalEntity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Entity {
    private boolean isActive;

    private List<Switch> switches = new ArrayList<>();
    private List<Wire> wires = new ArrayList<>();
    private List<LogicalEntity> logicalEntities = new ArrayList<>();
    private List<Bomb> logicalBombs = new ArrayList<>();

    public Wire(Position position, boolean isActive) {
        super(position);
        this.isActive = isActive;

        System.out.println("Wires: " + wires);
        System.out.println("Logical Entities: " + logicalEntities);
        System.out.println("Bombs: " + logicalBombs + "\n");
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public void notify(GameMap map) {
        wires.stream().forEach(wire -> {
            List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
            for (Position p : adjPosList) {
                List<Entity> adjEntities = map.getEntities(p);
                List<Entity> adjWires = adjEntities.stream().filter(e -> e instanceof Wire)
                        .collect(Collectors.toList());
                for (Entity entity : adjWires) {
                    Wire w = (Wire) entity;
                    if (!wires.contains(w)) {
                        addWire(w);
                    }
                }
            }
            for (Wire w : wires) {
                w.setActive(true);
                w.notify(map);
            }
        });

        logicalEntities.stream().forEach(logicalEntity -> {
            if (logicalEntity.checkLogic()) {
                logicalEntity.update();
            }
        });

        logicalBombs.stream().forEach(logicalBomb -> {
            if (isActive() && logicalBomb.checkLogic()) {
                logicalBomb.notify();
            }
        });
    }

    public void addSwitch(Switch s) {
        switches.add(s);
    }

    public void removeSwitch(Switch s) {
        switches.remove(s);
    }

    public void addWire(Wire w) {
        wires.add(w);
    }

    public void removeWire(Wire w) {
        wires.remove(w);
    }

    public void addLogicalEntity(LogicalEntity le) {
        logicalEntities.add(le);
    }

    public void removeLogicalEntity(LogicalEntity le) {
        logicalEntities.remove(le);
    }

    public void addLogicalBomb(Bomb b) {
        logicalBombs.add(b);
    }

    public void removeLogicalBomb(Bomb b) {
        logicalBombs.remove(b);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        if (isActive) {
            notify();
        }
    }
}
