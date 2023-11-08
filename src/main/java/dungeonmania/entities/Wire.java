package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.collectables.Bomb;
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
                    w.notify(map);
                }
            }
        });

        for (LogicalEntity logicalEntity : logicalEntities) {
            if (logicalEntity.checkLogic()) {
                logicalEntity.update();
                // manipulate logical entities where necessary
            }
        }

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

    public void addLogicalEntityObserver(LogicalEntity logicalEntity) {
        logicalEntities.add(logicalEntity);
    }

    public void removeLogicalEntityObserver(LogicalEntity logicalEntity) {
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
