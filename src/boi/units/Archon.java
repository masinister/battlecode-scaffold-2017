package boi.units;

import battlecode.common.*;
import boi.behavior.Archon1;
import boi.behavior.Behavior;
import boi.behavior.BehaviorMove;

public class Archon extends Unit {

    private boolean builder = false;

    public Archon(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        float g = 0;
        while (!mController.canHireGardener(new Direction(g))) {
            g += 0.1;
        }
        mController.hireGardener(new Direction(g));

        if (mController.getTeamBullets() == 400 && !builder) {
            g = 0;
            while (!mController.canHireGardener(new Direction(g))) {
                g += 0.1;
            }
            mController.hireGardener(new Direction(g));
            mController.broadcast(0, 10);
            builder = true;
        }

        Behavior archon = new Archon1(this);

        while (!archon.isDone()) {
            if (archon.next())
                Clock.yield();
        }
        archon.destroy();
    }
}