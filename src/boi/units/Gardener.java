package boi.units;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class Gardener extends Unit {

    public Gardener(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        while (true) {
            float dir = 0;
            while (!mController.canBuildRobot(RobotType.SOLDIER, new Direction(dir)))
                dir += Math.PI / 10F;
            mController.buildRobot(RobotType.SOLDIER, new Direction(dir));
            Clock.yield();
        }
    }
}
