package boi.units;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

public class Tank extends Unit {

    private RobotInfo[] robots;
    private Direction dir;

    public Tank(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        while (true) {
            robots = mController.senseNearbyRobots(-1, mController.getTeam().opponent());
            if (robots.length > 0) {
                if (mController.canFirePentadShot()) {
                    mController.firePentadShot(mController.getLocation().directionTo(robots[0].getLocation()));
                }
                if (mController.canMove(mController.getLocation().directionTo(robots[0].getLocation())))
                    mController.move(mController.getLocation().directionTo(robots[0].getLocation()));
            } else {
                dir = mController.getLocation().directionTo(mController.getInitialArchonLocations(mController.getTeam().opponent())[0]);
                if (mController.canMove(dir))
                    mController.move(dir);
            }
            Clock.yield();
        }
    }
}