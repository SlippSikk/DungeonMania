package dungeonmania.entities.logicalEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.conductors.Switch;
import dungeonmania.entities.conductors.Wire;
import dungeonmania.util.Position;

public class LogicalEntity extends Entity {
    private String logic;
    private boolean allAdjWiresSameState;
    private List<Wire> wires = new ArrayList<>();
    private List<Switch> switches = new ArrayList<>();

    public LogicalEntity(Position pos, String logic) {
        super(pos);
        this.logic = logic;
        this.allAdjWiresSameState = true;
    }

    public boolean checkLogic() {
        switch (getLogic()) {
        case "and":
            return wires.stream().allMatch(w -> w.isActivated()) && switches.stream().allMatch(s -> s.isActivated());
        case "or":
            return wires.stream().anyMatch(w -> w.isActivated()) || switches.stream().anyMatch(s -> s.isActivated());
        case "xor":
            return wires.stream().filter(w -> w.isActivated()).count()
                    + switches.stream().filter(s -> s.isActivated()).count() == 1;
        case "co_and":
            return wires.stream().allMatch(w -> w.isActivated()) && isallAdjWiresSameState();
        default:
            return false;
        }
    }

    public void checkAllAdjWiresSameState() {
        setallAdjWiresSameState(
                wires.stream().allMatch(w -> w.isActivated()) || wires.stream().allMatch(w -> !w.isActivated()));
    }

    public boolean isallAdjWiresSameState() {
        return allAdjWiresSameState;
    }

    public void setallAdjWiresSameState(boolean allAdjWiresSameState) {
        this.allAdjWiresSameState = allAdjWiresSameState;
    }

    public void addWire(Wire w) {
        if (!wires.contains(w))
            wires.add(w);
    }

    public void removeWire(Wire w) {
        wires.remove(w);
    }

    public void addSwitch(Switch s) {
        if (!switches.contains(s))
            switches.add(s);
    }

    public String getLogic() {
        return logic;
    }

    public void update() {
        return;
    }
}
