package dungeonmania.entities.conductors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Conductor {
    private List<Switch> switches = new ArrayList<>();

    public Wire(Position position) {
        super(position);
    }

    public void notifyActivated(GameMap map) {
        if (!isActivated()) {
            setActivated(true);
            getWires().stream().forEach(w -> w.notifyActivated(map));
            updateLogicalEntities(map);
        }
    }

    public void notifyDeactivated(GameMap map) {
        if (isActivated()) {
            Set<Wire> visitedWires = new HashSet<>();
            if (!checkActiveAdjSwitch(map, visitedWires)) {
                visitedWires.forEach(w -> w.setActivated(false));
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

        for (Wire adjWire : getWires()) {
            if (adjWire.checkActiveAdjSwitch(map, visitedWires)) {
                return true;
            }
        }

        return false;
    }

    public void updateLogicalEntities(GameMap map) {
        getLogicalEntities().stream().forEach(logicalEntity -> {
            logicalEntity.update();
        });

        getLogicalBombs().stream().forEach(logicalBomb -> {
            // System.out.println(isActivated());
            // System.out.println(logicalBomb.checkLogic());
            if (isActivated() && logicalBomb.checkLogic()) {
                logicalBomb.notify(map);
            }
        });
        // System.out.println("\n");
    }

    public void addSwitch(Switch s) {
        switches.add(s);
    }

    public void removeSwitch(Switch s) {
        switches.remove(s);
    }

    public void addLogicalBomb(Bomb b, GameMap map) {
        getLogicalBombs().add(b);
        if (isActivated() && b.isLogical()) {
            getLogicalBombs().stream().forEach(bomb -> bomb.notifyLogic(map));
        }
    }
}
