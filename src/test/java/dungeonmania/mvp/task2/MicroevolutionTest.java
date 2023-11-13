package dungeonmania.mvp.task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MicroevolutionTest {
    @Test
    @Tag("16-0")
    @DisplayName("Test dungeonmania controller can get enemies goal")
    public void testEnemyGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_microevolutionTest_enemy", "c_microevolutionTest_enemy");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        res = dmc.tick(Direction.LEFT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @Tag("16-1")
    @DisplayName("Test achieving a basic enemy goal")
    public void enemy() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_microevolutionTest_enemy", "c_microevolutionTest_enemy");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // move player right and kill spider
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @Tag("16-2")
    @DisplayName("Test achieving a basic enemy goal with a spawner")
    public void enemyAndSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_microevolutionTest_spawner", "c_microevolutionTest_spawner");

        String zombieSp = TestUtils.getEntitiesStream(res, "zombie_toast_spawner").findFirst().get().getId();

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // move player down and kill spider
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // move player right to get sword
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // destroy spawner
        res = assertDoesNotThrow(() -> dmc.interact(zombieSp));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @Tag("16-3")
    @DisplayName("Test achieving a basic goal with multiple enemies")
    public void multipleEnemies() {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_microevolutionTest_multipleEnemies";
        DungeonResponse res = dmc.newGame("d_microevolutionTest_multipleEnemies", config);

        String zombieSp = TestUtils.getEntitiesStream(res, "zombie_toast_spawner").findFirst().get().getId();

        // player begins at (2, 0) and moves down to (2, 1) to collect sword
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // player moves left to (1, 1) and kills spider
        res = dmc.tick(Direction.LEFT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // destroy spawner
        res = assertDoesNotThrow(() -> dmc.interact(zombieSp));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // player moves to (1, 2) and kills spider
        res = dmc.tick(Direction.DOWN);
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    }

    @Test
    @Tag("16-4")
    @DisplayName("Test complex goal: enemies and exit goal")
    public void complexGoalEnemies() {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_microevolutionTest_complexGoalEnemies";
        DungeonResponse res = dmc.newGame("d_microevolutionTest_complexGoalEnemies", config);

        String zombieSp = TestUtils.getEntitiesStream(res, "zombie_toast_spawner").findFirst().get().getId();

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // player begins at (2, 0) and moves down to (2, 1) to collect sword
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // player moves left to (1, 1) and kills spider
        res = dmc.tick(Direction.LEFT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // destroy spawner
        res = assertDoesNotThrow(() -> dmc.interact(zombieSp));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        String zombieSp2 = TestUtils.getEntitiesStream(res, "zombie_toast_spawner").findFirst().get().getId();

        // player moves to (1, 2)
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // player moves to (1, 3)
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // player moves to (1, 4) and kills spider
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // destroy spawner
        res = assertDoesNotThrow(() -> dmc.interact(zombieSp2));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // player moves to (1, 5)
        res = dmc.tick(Direction.DOWN);
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // move player down and exit at (1, 6)
        res = dmc.tick(Direction.DOWN);
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertFalse(TestUtils.getGoals(res).contains(":exit"));
    }

    @Test
    @Tag("16-5")
    @DisplayName("Test 0 enemies but spawner exists")
    public void zeroEnemiesAndSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        String config = "c_microevolutionTest_zeroEnemiesAndSpawner";
        DungeonResponse res = dmc.newGame("d_microevolutionTest_zeroEnemiesAndSpawner", config);

        String zombieSp = TestUtils.getEntitiesStream(res, "zombie_toast_spawner").findFirst().get().getId();

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // move player down
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // move player right to get sword
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // destroy spawner
        res = assertDoesNotThrow(() -> dmc.interact(zombieSp));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    }
}
