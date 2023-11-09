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

    private List<Wire> wires = new ArrayList<>();
    private List<LogicalEntity> logicalEntities = new ArrayList<>();
    private List<Bomb> logicalBombs = new ArrayList<>();

    public Wire(Position position, boolean isActive) {
        super(position);
        this.isActive = isActive;

        // List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        //     for (Position p : adjPosList)

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
                    w.notify(map);
                }
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

    public void addWire(Wire w) {
        wires.add(w);
    }

    public void removeWire(Wire w) {
        wires.remove(w);
    }

    public void addLogicalEntity(LogicalEntity logicalEntity) {
        logicalEntities.add(logicalEntity);
    }

    public void removeLogicalEntity(LogicalEntity logicalEntity) {
        logicalEntities.remove(logicalEntity);
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
}
