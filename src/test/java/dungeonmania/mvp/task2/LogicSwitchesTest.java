package dungeonmania.mvp.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    @Tag("18-1")
    @DisplayName("Test lightbulb with logic OR switched on and off by boulder and floor switch")
    public void lightBulbOr() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbOr", "c_logicSwitchesTest_lightBulbOr");

        assertTrue(boulderAt(res, 2, 1));
        // assertTrue(lightBulbOff(res, 6, 1));
        // assertFalse(lightBulbOn(res, 6, 1));

        // Player moves boulder onto floor switch to turn light bulb on
        res = dmc.tick(Direction.RIGHT);
        assertFalse(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 3, 1));
        assertEquals(new Position(2, 1), TestUtils.getPlayer(res).get().getPosition());
        // assertFalse(lightBulbOff(res, 6, 1));
        // assertTrue(lightBulbOn(res, 6, 1));

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
        // assertTrue(lightBulbOff(res, 6, 1));
        // assertFalse(lightBulbOn(res, 6, 1));
    }

    @Test
    @Tag("18-2")
    @DisplayName("Test lightbulb with logic AND switched on and off by boulder and floor switch")
    public void lightBulbAnd() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbAnd", "c_logicSwitchesTest_lightBulbAnd");

        assertTrue(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 2, 2));
        // assertTrue(lightBulbOff(res, 6, 1));
        // assertFalse(lightBulbOn(res, 6, 1));

        res = dmc.tick(Direction.RIGHT);
        assertFalse(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 3, 1));
        // assertTrue(lightBulbOff(res, 6, 1));
        // assertFalse(lightBulbOn(res, 6, 1));

        // Boulders on both switches - light bulb on
        res = dmc.tick(Direction.DOWN);
        assertFalse(boulderAt(res, 2, 2));
        assertTrue(boulderAt(res, 2, 3));
        // assertFalse(lightBulbOff(res, 6, 1));
        // assertTrue(lightBulbOn(res, 6, 1));

        // One boulder moved off floor switch to turn light bulb off
        res = dmc.tick(Direction.DOWN);
        assertFalse(boulderAt(res, 2, 3));
        assertTrue(boulderAt(res, 2, 4));
        // assertTrue(lightBulbOff(res, 6, 1));
        // assertFalse(lightBulbOn(res, 6, 1));
    }

    @Test
    @Tag("18-4")
    @DisplayName("Test lightbulb with logic XOR switched on and off by boulder and floor switch")
    public void lightBulbXor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbXor", "c_logicSwitchesTest_lightBulbXor");

    }

    @Test
    @Tag("18-4")
    @DisplayName("Test lightbulbs with logic CO_AND switched on by boulder and floor switch")
    public void lightBulbCoAnd() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbCoand", "c_logicSwitchesTest_lightBulbCoand");

        // assertTrue(lightBulbOff(res, 6, 1));
        // assertFalse(lightBulbOn(res, 6, 1));
        // assertTrue(lightBulbOff(res, 4, 3));
        // assertFalse(lightBulbOn(res, 4, 3));

        // light bulb at (6, 1) switched on
        res = dmc.tick(Direction.RIGHT);
        assertFalse(boulderAt(res, 2, 1));
        assertTrue(boulderAt(res, 3, 1));
        // assertTrue(lightBulbFalse(res, 6, 1));
        // assertFalse(lightBulbTrue(res, 6, 1));
        // assertTrue(lightBulbOff(res, 4, 3));
        // assertFalse(lightBulbOn(res, 4, 3));

        // light bulb at (4, 3) should stay off
        res = dmc.tick(Direction.DOWN);
        assertFalse(boulderAt(res, 2, 2));
        assertTrue(boulderAt(res, 2, 3));
        // assertTrue(lightBulbFalse(res, 6, 1));
        // assertFalse(lightBulbTrue(res, 6, 1));
        // assertTrue(lightBulbOff(res, 4, 3));
        // assertFalse(lightBulbOn(res, 4, 3));
    }

    @Test
    @Tag("18-5")
    @DisplayName("Test additional cases regarding logic with lightbulbs")
    public void lightBulbAdditionalCases() {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_logicSwitchesTest_lightBulbAdditionalCases";
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_lightBulbAdditionalCases", config);

    }

    @Test
    @Tag("18-6")
    @DisplayName("Test logic with door switch")
    public void logicDoorSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_doorSwitch", "c_logicSwitchesTest_doorSwitch");
    }

    @Test
    @Tag("18-7")
    @DisplayName("Test logic with bombs")
    public void logicBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicSwitchesTest_bomb", "c_logicSwitchesTest_bomb");
    }
}
