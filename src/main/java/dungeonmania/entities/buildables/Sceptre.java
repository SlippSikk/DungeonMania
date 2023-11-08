package dungeonmania.entities.buildables;

import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {
    private int duration;
    private static final int DEFAULT_VAL = 1;

    // Sceptre can be used 3 times
    private static int durability = 3;

    public Sceptre(int duration) {
        super(null, durability);
        this.duration = duration;
    }


    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, DEFAULT_VAL, DEFAULT_VAL));
    }

    public int getDuration() {
        return duration;
    }

}
