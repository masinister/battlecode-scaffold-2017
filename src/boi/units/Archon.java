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
        MapLocation target = null;
        while (target == null) {
            MapLocation[] broadcasts = mController.senseBroadcastingRobotLocations();
            if (broadcasts.length > 0)
                target = broadcasts[0];
        }
        Behavior behavior = new BehaviorMove(this, target.x, target.y);
        while (!behavior.isDone()) {
            if (behavior.next())
                Clock.yield();
            mController.setIndicatorDot(target, 255, 0, 0);
        }
        behavior.destroy();
        while (true) {
            mController.setIndicatorDot(target, 255, 0, 0);
        }
    }
}
