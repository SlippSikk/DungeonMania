package dungeonmania.entities.conductors;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logicalEntities.LogicalEntity;
import dungeonmania.util.Position;

public class Conductor extends Entity {
    private boolean activated;
    private List<Wire> wires = new ArrayList<>();
    private List<LogicalEntity> logicalEntities = new ArrayList<>();
    private List<Bomb> logicalBombs = new ArrayList<>();

    public Conductor(Position position) {
        super(position);
        this.activated = false;
    }

    public void addWire(Wire w) {
        if (!wires.contains(w))
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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public List<Wire> getWires() {
        return wires;
    }

    public List<LogicalEntity> getLogicalEntities() {
        return logicalEntities;
    }

    public List<Bomb> getLogicalBombs() {
        return logicalBombs;
    }
}
