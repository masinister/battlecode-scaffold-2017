package boi.units;

import battlecode.common.*;

public class Archon extends Unit {

    private float backwards;

    public Archon(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        float dir = 0;
        while (!mController.canHireGardener(new Direction(dir)))
            dir += Math.PI / 6.0;
        mController.hireGardener(new Direction(dir));
        MapLocation[] archons = mController.getInitialArchonLocations(mController.getTeam().opponent());
        backwards = mController.getLocation().directionTo(archons[0]).radians + (float)Math.PI;
        mController.broadcastFloat(0, mController.getLocation().directionTo(archons[0]).radians);
        while (true) {
            if (mController.canMove(new Direction(backwards)))
                mController.move(new Direction(backwards));
            Clock.yield();
        }
    }
}
