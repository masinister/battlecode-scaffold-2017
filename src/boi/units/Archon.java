package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.BehaviorMove;

public class Archon extends Unit {

    public Archon(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        if (mController.getTeamBullets() > 200) {
            float dir = 0;
            while (!mController.canHireGardener(new Direction(dir)))
                dir += Math.PI / 10F;
            mController.hireGardener(new Direction(dir));
        }
        while (true) {
            Clock.yield();
        }
    }
}