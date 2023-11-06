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
    @DisplayName("Test achieving a basic enemy goal")
    public void testEnemyGoal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_microevolutionTest_enemy", "c_microevolutionTest_enemy");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    }

    // @Test
    // @Tag("16-1")
    // @DisplayName("Test achieving a basic enemy goal")
    // public void enemy() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse res = dmc.newGame("d_microevolutionTest_enemy", "c_microevolutionTest_enemy");

    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    //     // move player right and kill spider
    //     res = dmc.tick(Direction.RIGHT);
    //     assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    // }

    // @Test
    // @Tag("16-2")
    // @DisplayName("Test achieving a basic enemy goal with a spawner")
    // public void enemyAndSpawner() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse res = dmc.newGame("d_microevolutionTest_spawner", "c_microevolutionTest_spawner");

    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    //     // move player right and kill spider
    //     res = dmc.tick(Direction.DOWN);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    //     // move right destroy spawner
    //     res = dmc.tick(Direction.RIGHT);
    //     assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    // }

    // @Test
    // @Tag("16-3")
    // @DisplayName("Test achieving a basic goal with multiple enemies")
    // public void multipleEnemies() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     String config = "c_microevolutionTest_multipleEnemies";
    //     DungeonResponse res = dmc.newGame("d_microevolutionTest_multipleEnemies", config);

    //     // move player right and kill spider
    //     res = dmc.tick(Direction.RIGHT);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    //     // move right destroy spawner
    //     res = dmc.tick(Direction.RIGHT);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    //     // player (3, 2) spider (4, 3)
    //     res = dmc.tick(Direction.DOWN);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    //     // player (3, 3) spider (4, 4)
    //     res = dmc.tick(Direction.DOWN);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));

    //     // player kills spider at (3, 4)
    //     res = dmc.tick(Direction.DOWN);
    //     assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    // }

    // @Test
    // @Tag("16-4")
    // @DisplayName("Test complex goal: enemies and exit goal")
    // public void complexGoalEnemies() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     String config = "c_microevolutionTest_complexGoalEnemies";
    //     DungeonResponse res = dmc.newGame("d_microevolutionTest_complexGoalEnemies", config);

    //     // move player right and kill spider
    //     res = dmc.tick(Direction.RIGHT);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    //     assertTrue(TestUtils.getGoals(res).contains(":exit"));

    //     // move right destroy spawner
    //     res = dmc.tick(Direction.RIGHT);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    //     assertTrue(TestUtils.getGoals(res).contains(":exit"));

    //     // player (3, 2) spider (4, 3)
    //     res = dmc.tick(Direction.DOWN);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    //     assertTrue(TestUtils.getGoals(res).contains(":exit"));

    //     // player (3, 3) spider (4, 4)
    //     res = dmc.tick(Direction.DOWN);
    //     assertTrue(TestUtils.getGoals(res).contains(":enemies"));
    //     assertTrue(TestUtils.getGoals(res).contains(":exit"));

    //     // player kills spider at (3, 4)
    //     res = dmc.tick(Direction.DOWN);
    //     assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    //     assertTrue(TestUtils.getGoals(res).contains(":exit"));

    //     res = dmc.tick(Direction.DOWN);
    //     assertFalse(TestUtils.getGoals(res).contains(":enemies"));
    //     assertFalse(TestUtils.getGoals(res).contains(":exit"));
    // }

    // @Test
    // @Tag("16-5")
    // @DisplayName("Test complex goal: enemies AND boulders AND exit")
    // public void complexGoalEnemies2() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     String config = "c_microevolutionTest_complexGoalEnemies2";
    //     DungeonResponse res = dmc.newGame("d_microevolutionTest_complexGoalEnemies2", config);
    // }

    // @Test
    // @Tag("16-6")
    // @DisplayName("Test complex goal: boulders AND treasure OR enemies")
    // public void complexGoalEnemies3() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     String config = "c_microevolutionTest_complexGoalEnemies3";
    //     DungeonResponse res = dmc.newGame("d_microevolutionTest_complexGoalEnemies3", config);
    // }

    // @Test
    // @Tag("16-7")
    // @DisplayName("Test complex goal enemies: boulders AND treasure OR enemies 2")
    // public void complexGoalEnemies4() {
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     String config = "c_microevolutionTest_complexGoalEnemies3";
    //     DungeonResponse res = dmc.newGame("d_microevolutionTest_complexGoalEnemies3", config);
    // }
}
