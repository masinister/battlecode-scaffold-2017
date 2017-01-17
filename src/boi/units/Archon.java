package boi.units;

import battlecode.common.*;
import boi.behavior.Archon1;
import boi.behavior.Behavior;
import boi.behavior.BehaviorMove;

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

        Behavior archon = new Archon1(this);

        while (!archon.isDone()){
            if (archon.next())
                Clock.yield();
        }
        archon.destroy();
    }
}
