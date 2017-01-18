package boi.units;

import battlecode.common.*;
import boi.behavior.Repeat;
import boi.behavior.TryMoveRandomDirection;

public class Archon extends Unit {

    public Archon(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        float g = 0;
        while (!mController.canHireGardener(new Direction(g))){
            g+=0.1;
        }
        mController.hireGardener(new Direction(g));

        Repeat scram = new Repeat(mController, new TryMoveRandomDirection(mController, 20, 3), Repeat.FOREVER);

        while (!scram.isDone()) {
            while (scram.canStep())
                scram.step();
            Clock.yield();
        }
        scram.destroy();
    }
}
