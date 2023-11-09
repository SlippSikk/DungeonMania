package dungeonmania.entities.logicalEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Wire;
import dungeonmania.util.Position;

public class LogicalEntity extends Entity {
    private String logic;
    private boolean allPrevWiresUnactivated;
    private List<Wire> wires = new ArrayList<>();

    public LogicalEntity(Position pos, String logic) {
        super(pos);
        this.logic = logic;
        this.allPrevWiresUnactivated = true;
    }

    public boolean checkLogic() {
        switch (getLogic()) {
        case "and":
            System.out.println("CHECK LOGIC");
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

    public void addWire(Wire w) {
        if (!wires.contains(w))
            wires.add(w);
    }

    public void removeWire(Wire w) {
        wires.remove(w);
    }

    public String getLogic() {
        return logic;
    }

    public boolean isAllPrevWiresUnactivated() {
        return allPrevWiresUnactivated;
    }

    public void update() {
        return;
    }

    public List<Position> getWires() {
        List<Position> list = new ArrayList<>();
        for (Wire w : wires) {
            list.add(w.getPosition());
        }
        return list;
    }
}
