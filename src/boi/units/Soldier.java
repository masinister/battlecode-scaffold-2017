package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.fight.FormSquad;

public class Soldier extends Unit {

    public Soldier(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        Behavior hunt = new FormSquad(mController);
        while (!hunt.isDone()) {
            while (hunt.canStep())
                hunt.step();
            Clock.yield();
        }
    }
}
