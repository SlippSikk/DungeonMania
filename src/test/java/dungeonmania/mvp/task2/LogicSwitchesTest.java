package dungeonmania.mvp.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LogicSwitchesTest {
    private boolean boulderAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "boulder").anyMatch(it -> it.getPosition().equals(pos));
    }

    private boolean lightBulbOffAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "light_bulb_off").anyMatch(it -> it.getPosition().equals(pos));
    }

    private boolean lightBulbOnAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "light_bulb_on").anyMatch(it -> it.getPosition().equals(pos));
    }

    private boolean wireAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "wire").anyMatch(it -> it.getPosition().equals(pos));
    }

    private boolean switchDoorClosedAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "switch_door").anyMatch(it -> it.getPosition().equals(pos));
    }

    private boolean switchDoorOpenAt(DungeonResponse res, int x, int y) {
        Position pos = new Position(x, y);
        return TestUtils.getEntitiesStream(res, "switch_door_open").anyMatch(it -> it.getPosition().equals(pos));
    }

    @Test
    @Tag("18-1")
    @DisplayName("Test lightbulb with logic AND switched on and off by boulder and floor switch")
    public void lightBulbAnd() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbAnd", "c_logicSwitchesTest_lightBulbAnd");

        assertTrue(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 2, 2));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));

        res = dmc.tick(Direction.RIGHT);
        assertFalse(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 3, 1));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));

        // light bulb still off
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        assertFalse(boulderAt(res, 3, 0));
        assertTrue(boulderAt(res, 4, 0));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));

        // light bulb turned on
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertFalse(boulderAt(res, 2, 2));
        assertTrue(boulderAt(res, 2, 3));
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));

        // light bulb still on
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertFalse(boulderAt(res, 3, 1));
        assertTrue(boulderAt(res, 3, 0));
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));

        // Boulder moved off floor switch to turn light bulb off
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        assertFalse(boulderAt(res, 2, 3));
        assertTrue(boulderAt(res, 2, 4));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));
    }

    @Test
    @Tag("18-2")
    @DisplayName("Test lightbulb with logic OR switched on and off by boulder and floor switch")
    public void lightBulbOr() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbOr", "c_logicSwitchesTest_lightBulbOr");

        assertTrue(boulderAt(res, 2, 1));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));

        // Player moves boulder onto floor switch to turn light bulb on
        res = dmc.tick(Direction.RIGHT);
        assertFalse(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 3, 1));
        assertEquals(new Position(2, 1), TestUtils.getPlayer(res).get().getPosition());
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));

        res = dmc.tick(Direction.UP);
        assertEquals(new Position(2, 0), TestUtils.getPlayer(res).get().getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 0), TestUtils.getPlayer(res).get().getPosition());

        // Boulder moved on floor switch to turn light bulb on
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(3, 1), TestUtils.getPlayer(res).get().getPosition());
        assertFalse(boulderAt(res, 3, 1));
        assertTrue(boulderAt(res, 3, 2));

        // Boulder moved off floor switch to turn light bulb off
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));
    }

    @Test
    @Tag("18-3")
    @DisplayName("Test lightbulb with logic XOR switched on and off by boulder and floor switch")
    public void lightBulbXor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbXor", "c_logicSwitchesTest_lightBulbXor");

        assertTrue(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 2, 2));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));

        // switched on
        res = dmc.tick(Direction.RIGHT);
        assertTrue(boulderAt(res, 3, 1));
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));

        // switched off
        res = dmc.tick(Direction.DOWN);
        assertTrue(boulderAt(res, 2, 3));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));

        // switched on again
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertTrue(boulderAt(res, 3, 0));
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));
    }

    @Test
    @Tag("18-4")
    @DisplayName("Test lightbulbs with logic CO_AND switched on by boulder and floor switch")
    public void lightBulbCoAnd() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbCoand", "c_logicSwitchesTest_lightBulbCoand");

        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));

        // light bulb at (6, 1) switched on
        res = dmc.tick(Direction.RIGHT);
        assertFalse(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 3, 1));
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));

        // light bulb at (4, 3) should stay off
        res = dmc.tick(Direction.DOWN);
        assertFalse(boulderAt(res, 2, 2));
        assertTrue(boulderAt(res, 2, 3));
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));

        // light bulb at (6, 1) switched off
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        assertFalse(boulderAt(res, 3, 1));
        assertTrue(boulderAt(res, 3, 0));
        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));
    }

    @Test
    @Tag("18-5")
    @DisplayName("Test additional cases regarding logic with lightbulbs")
    public void lightBulbAdditionalCases() {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_logicSwitchesTest_lightBulbAdditionalCases";
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbAdditionalCases", config);

        assertTrue(lightBulbOffAt(res, 6, 1));
        assertFalse(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));
        assertTrue(lightBulbOffAt(res, 1, 5));
        assertFalse(lightBulbOnAt(res, 1, 5));

        res = dmc.tick(Direction.RIGHT);
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));
        assertTrue(lightBulbOffAt(res, 1, 5));
        assertFalse(lightBulbOnAt(res, 1, 5));

        res = dmc.tick(Direction.DOWN);
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));
        assertFalse(lightBulbOffAt(res, 1, 5));
        assertTrue(lightBulbOnAt(res, 1, 5));

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));
        assertFalse(lightBulbOffAt(res, 4, 3));
        assertTrue(lightBulbOnAt(res, 4, 3));
        assertFalse(lightBulbOffAt(res, 1, 5));
        assertTrue(lightBulbOnAt(res, 1, 5));

        res = dmc.tick(Direction.RIGHT);
        assertFalse(lightBulbOffAt(res, 6, 1));
        assertTrue(lightBulbOnAt(res, 6, 1));
        assertTrue(lightBulbOffAt(res, 4, 3));
        assertFalse(lightBulbOnAt(res, 4, 3));
        assertFalse(lightBulbOffAt(res, 1, 5));
        assertTrue(lightBulbOnAt(res, 1, 5));
    }

    @Test
    @Tag("18-6")
    @DisplayName("Test logic with switch door")
    public void logicDoorSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_doorSwitch", "c_logicSwitchesTest_doorSwitch");

        assertTrue(switchDoorClosedAt(res, 4, 0));
        assertFalse(switchDoorOpenAt(res, 4, 0));
        assertTrue(switchDoorClosedAt(res, 6, 1));
        assertFalse(switchDoorOpenAt(res, 6, 1));
        assertTrue(switchDoorClosedAt(res, 3, 2));
        assertFalse(switchDoorOpenAt(res, 3, 2));
        assertTrue(switchDoorClosedAt(res, 4, 2));
        assertFalse(switchDoorOpenAt(res, 4, 2));
        assertTrue(switchDoorClosedAt(res, 5, 2));
        assertFalse(switchDoorOpenAt(res, 5, 2));

        res = dmc.tick(Direction.RIGHT);
        // assertFalse(switchDoorClosedAt(res, 4, 0));
        assertTrue(switchDoorOpenAt(res, 4, 0));
        assertTrue(switchDoorClosedAt(res, 6, 1));
        assertFalse(switchDoorOpenAt(res, 6, 1));
        // assertFalse(switchDoorClosedAt(res, 3, 2));
        assertTrue(switchDoorOpenAt(res, 3, 2));
        // assertFalse(switchDoorClosedAt(res, 4, 2));
        assertTrue(switchDoorOpenAt(res, 4, 2));
        assertTrue(switchDoorClosedAt(res, 5, 2));
        assertFalse(switchDoorOpenAt(res, 5, 2));

        res = dmc.tick(Direction.DOWN);
        // assertFalse(switchDoorClosedAt(res, 4, 0));
        assertTrue(switchDoorOpenAt(res, 4, 0));
        // assertFalse(switchDoorClosedAt(res, 6, 1));
        assertTrue(switchDoorOpenAt(res, 6, 1));
        // assertFalse(switchDoorClosedAt(res, 3, 2));
        assertTrue(switchDoorOpenAt(res, 3, 2));
        assertTrue(switchDoorClosedAt(res, 4, 2));
        assertFalse(switchDoorOpenAt(res, 4, 2));
        assertTrue(switchDoorClosedAt(res, 5, 2));
        assertFalse(switchDoorOpenAt(res, 5, 2));

        res = dmc.tick(Direction.DOWN);
        // assertFalse(switchDoorClosedAt(res, 4, 0));
        assertTrue(switchDoorOpenAt(res, 4, 0));
        assertTrue(switchDoorClosedAt(res, 6, 1));
        assertFalse(switchDoorOpenAt(res, 6, 1));
        // assertFalse(switchDoorClosedAt(res, 3, 2));
        assertTrue(switchDoorOpenAt(res, 3, 2));
        // assertFalse(switchDoorClosedAt(res, 4, 2));
        assertTrue(switchDoorOpenAt(res, 4, 2));
        assertTrue(switchDoorClosedAt(res, 5, 2));
        assertFalse(switchDoorOpenAt(res, 5, 2));

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        // can walk through open switch door
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        // cannot walk through switch door
        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);

        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());

        pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.DOWN);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    // @Test
    // @Tag("18-7")
    // @DisplayName("Test logic with bombs")
    // public void logicBomb() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse res = dmc.newGame("d_logicSwitchesTest_bomb", "c_logicSwitchesTest_bomb");

    //     res = dmc.tick(Direction.RIGHT);
    //     assertTrue(wireAt(res, 7, 1));
    //     assertTrue(wireAt(res, 6, 2));
    //     assertFalse(wireAt(res, 3, 1));
    //     assertFalse(wireAt(res, 4, 1));
    //     assertFalse(wireAt(res, 5, 1));

    //     res = dmc.tick(Direction.DOWN);
    //     assertFalse(wireAt(res, 0, 4));
    //     assertFalse(wireAt(res, 1, 5));
    //     assertTrue(wireAt(res, 4, 4));
    //     assertTrue(wireAt(res, 3, 5));

    //     res = dmc.tick(Direction.RIGHT);
    //     res = dmc.tick(Direction.RIGHT);
    //     res = dmc.tick(Direction.RIGHT);
    //     res = dmc.tick(Direction.RIGHT);
    //     res = dmc.tick(Direction.RIGHT);
    //     res = dmc.tick(Direction.DOWN);

    //     res = dmc.tick(Direction.RIGHT);
    //     assertFalse(wireAt(res, 7, 1));
    //     assertFalse(wireAt(res, 6, 2));
    // }

    // @Test
    // @Tag("18-8")
    // @DisplayName("Test case where bomb destroys circuit")
    // public void bombDestroyCircuit() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     String config = "c_logicSwitchesTest_bombDestroyCircuit";
    //     DungeonResponse res = dmc.newGame("d_logicSwitchesTest_bombDestoryCircuit", config);

    //     // logical bomb explodes
    //     res = dmc.tick(Direction.RIGHT);
    //     assertFalse(wireAt(res, 4, 2));
    //     assertFalse(wireAt(res, 3, 3));
    //     assertFalse(wireAt(res, 5, 3));
    //     assertFalse(wireAt(res, 3, 4));
    //     assertFalse(wireAt(res, 4, 4));
    //     assertFalse(wireAt(res, 5, 4));

    //     // logical entities are now unaffected since current to them has been cut off
    //     res = dmc.tick(Direction.DOWN);
    //     assertTrue(lightBulbOffAt(res, 3, 5));
    //     assertFalse(lightBulbOnAt(res, 3, 5));
    //     assertTrue(wireAt(res, 6, 4));
    // }

    // @Test
    // @Tag("18-9")
    // @DisplayName("Test case where bomb is picked up by player and placed in circuit")
    // public void logicalBombPlacedByPLayer() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     String config = "c_logicSwitchesTest_bombPlacedByPlayer";
    //     DungeonResponse res = dmc.newGame("d_logicSwitchesTest_bombPlacedByPlayer", config);

    //     res = dmc.tick(Direction.RIGHT);
    // }

    public static void main(String[] args) {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbCoand", "c_logicSwitchesTest_lightBulbCoand");

        // light bulb at (6, 1) switched on
        res = dmc.tick(Direction.RIGHT);

        // light bulb at (4, 3) should stay off
        res = dmc.tick(Direction.DOWN);

        // light bulb at (6, 1) switched off
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
    }
}
