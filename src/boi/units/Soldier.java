package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.fight.Hunting;

public class Soldier extends Unit {

    public Soldier(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        Behavior hunt = new Hunting(mController);
        while (!hunt.isDone()) {
            while (hunt.canStep())
                hunt.step();
            Clock.yield();
        }
    }
}
