package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.BehaviorMove;
import boi.behavior.PredictiveShoot;

public class Soldier extends Unit {

    public Soldier(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        while (true) {
            MapLocation target = null;
            while (target == null) {
                MapLocation[] broadcasts = mController.senseBroadcastingRobotLocations();
                if (broadcasts.length > 0)
                    target = broadcasts[0];
            }
            Behavior behavior = new BehaviorMove(this, target.x, target.y);
            while (!behavior.isDone()) {
                if (behavior.next()) {
                    Clock.yield();
                }
            }
            while (true) {
                RobotInfo[] robots = mController.senseNearbyRobots();
                if (robots.length>0){
                    if(mController.canFireSingleShot()){
                        mController.fireSingleShot(mController.getLocation().directionTo(robots[0].getLocation()));
                    }
                }Clock.yield();
            }

        }
    }
}
