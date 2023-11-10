package dungeonmania.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public void notifyActivated(GameMap map) {
        if (!isActive()) {
            setActive(true);
            wires.stream().forEach(w -> w.notifyActivated(map));
            updateLogicalEntities(map);
        }
    }

    public void notifyDeactivated(GameMap map) {
        if (isActive()) {
            Set<Wire> visitedWires = new HashSet<>();
            if (!checkActiveAdjSwitch(map, visitedWires)) {
                visitedWires.forEach(w -> w.setActive(false));
                visitedWires.forEach(w -> w.updateLogicalEntities(map));
            }
        }
    }

    public boolean checkActiveAdjSwitch(GameMap map, Set<Wire> visitedWires) {
        if (visitedWires.contains(this)) {
            return false;
        }
        visitedWires.add(this);

        for (Switch adjSwitch : switches) {
            if (adjSwitch.isActivated()) {
                return true;
            }
        }

        for (Wire adjWire : wires) {
            if (adjWire.checkActiveAdjSwitch(map, visitedWires)) {
                return true;
            }
        }

        return false;
    }

    public void updateLogicalEntities(GameMap map) {
        logicalEntities.stream().forEach(logicalEntity -> {
            logicalEntity.update();
        });

        logicalBombs.stream().forEach(logicalBomb -> {
            System.out.println(isActive());
            System.out.println(logicalBomb.checkLogic());
            if (isActive() && logicalBomb.checkLogic()) {
                logicalBomb.notify(map);
            }
        });
        System.out.println("\n");
    }

    public void addSwitch(Switch s) {
        switches.add(s);
    }

    public void removeSwitch(Switch s) {
        switches.remove(s);
    }

    public void addWire(Wire w) {
        if (!wires.contains(w))
            wires.add(w);
    }

    public void addLogicalBomb(Bomb b, GameMap map) {
        logicalBombs.add(b);
        if (isActive() && b.isLogical()) {
            logicalBombs.stream().forEach(bomb -> bomb.notifyLogic(map));
        }
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
    }

    // public List<Position> getWires() {
    //     List<Position> list = new ArrayList<>();
    //     for (Wire w : wires) {
    //         if (w != this) {
    //             list.add(w.getPosition());
    //         }
    //     }
    //     return list;
    // }

    // public List<Position> getSwitches() {
    //     List<Position> list = new ArrayList<>();
    //     for (Switch w : switches) {
    //         list.add(w.getPosition());
    //     }
    //     return list;
    // }

    // public List<Position> getLogicalEntities() {
    //     List<Position> list = new ArrayList<>();
    //     for (LogicalEntity w : logicalEntities) {
    //         list.add(w.getPosition());
    //     }
    //     return list;
    // }

    public List<Position> getBombs() {
        List<Position> list = new ArrayList<>();
        for (Bomb w : logicalBombs) {
            list.add(w.getPosition());
        }
        return list;
    }
}
