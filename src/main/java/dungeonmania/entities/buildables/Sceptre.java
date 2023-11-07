package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public class Sceptre extends Buildable {
    private int duration;

    // Sceptre can be used 3 times
    private int durability = 3;

    public Sceptre(int duration) {
        super(null);
        this.duration = duration;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.removeBuildable(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1));
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int getDurability() {
        return durability;
    }

}
