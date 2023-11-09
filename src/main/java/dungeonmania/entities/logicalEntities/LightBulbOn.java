package dungeonmania.entities.logicalEntities;

import dungeonmania.util.Position;

public class LightBulbOn extends LogicalEntity {
    public LightBulbOn(Position pos, String logic) {
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
