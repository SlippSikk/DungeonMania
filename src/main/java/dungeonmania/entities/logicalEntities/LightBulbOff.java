package dungeonmania.entities.logicalEntities;

import dungeonmania.util.Position;

public class LightBulbOff extends LogicalEntity {
    public LightBulbOff(Position pos, String logic) {
        super(pos, logic);
    }

    @Override
    public void update() {
        if (checkLogic()) {
            // destory this instance and replace with light bulb on
            return;
        }
    }
}
