package boi.units;

import battlecode.common.*;
import boi.behavior.Multitask;
import boi.behavior.Repeat;
import boi.behavior.build.Spawn;
import boi.behavior.move.Move;

public class Archon extends Unit {

    public Archon(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        float dir = 0;
        while (!mController.canHireGardener(new Direction(dir)))
            dir += Math.PI / 6.0;
        mController.hireGardener(new Direction(dir));
        while (true) {
            Clock.yield();
        }
    }
}
