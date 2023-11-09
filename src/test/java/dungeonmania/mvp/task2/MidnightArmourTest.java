package dungeonmania.mvp.task2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;

public class MidnightArmourTest {
    @Test
    @Tag("12-1")
    @DisplayName("Test building midnight armour")
    public void buildMidnightArmour() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest", "c_midnightArmourTest");
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // pickup sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build midnight armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("12-2")
    @DisplayName("Test building midnight armour with zombies")
    public void buildMidnightArmourZombies() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest_zombies", "c_midnightArmourTest");
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // pickup sword
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build midnight armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

    }

    @Test
    @Tag("12-3")
    @DisplayName("Test building midnight armour and defeating mercenary")
    public void buildMidnightArmourDefeatMercenary() {
        DungeonManiaController dmc;
        String config = "c_midnightArmourTest";
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest", config);

        // Check mercenary and player on map
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "player"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "mercenary"));

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);

        // pickup sword
        res = dmc.tick(Direction.RIGHT);
        // Pick up sunstone
        res = dmc.tick(Direction.RIGHT);

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT); // Battle occurs

        BattleResponse battle = res.getBattles().get(0);
        RoundResponse firstRound = battle.getRounds().get(0);

        // check the damage done to mercenary
        double playerAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("player_attack", config));
        double attackBuff = Integer.parseInt(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        // Delta health is negative so take negative here
        assertEquals((playerAttack + attackBuff) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);

        // check damage done to player
        double enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("mercenary_attack", config));
        double defenceBuff = Integer.parseInt(TestUtils.getValueFromConfigFile("midnight_armour_defence", config));

        // Delta health is negative so take negative here
        assertEquals((enemyAttack - defenceBuff) / 10, -firstRound.getDeltaCharacterHealth(), 0.001);

        // Check mercenary has been defeated and player is alive
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "player"));
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "mercenary"));

    }

    @Test
    @Tag("12-4")
    @DisplayName("Test player dies when battle mercenary without midnight armour")
    public void playerDiesNoMidnightArmour() {
        DungeonManiaController dmc;
        String config = "c_midnightArmourTest";
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_midnightArmourTest", config);

        // Check mercenary and player on map
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "player"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "mercenary"));

        // Pick up Wood
        dmc.tick(Direction.RIGHT);

        // pickup sword
        dmc.tick(Direction.RIGHT);
        // Pick up sunstone
        dmc.tick(Direction.RIGHT);

        dmc.tick(Direction.RIGHT);

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT); // Battle occurs

        BattleResponse battle = res.getBattles().get(0);
        RoundResponse firstRound = battle.getRounds().get(0);

        // check the damage done to mercenary
        double playerAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("player_attack", config));
        double swordAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("sword_attack", config));

        // Delta health is negative so take negative here
        assertEquals((playerAttack + swordAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);

        // check damage done to player
        double enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("mercenary_attack", config));

        // Delta health is negative so take negative here
        assertEquals((enemyAttack) / 10, -firstRound.getDeltaCharacterHealth(), 0.001);

        // Check mercenary has been defeated and player is alive
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "player"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "mercenary"));

    }

}
