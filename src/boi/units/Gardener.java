package boi.units;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

import java.util.Random;

public class Gardener extends Unit {

    private Random rnd = new Random();

    public Gardener(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {

        while (true) {
            float dir = 0;
            while (!mController.canBuildRobot(RobotType.LUMBERJACK, new Direction(dir)))
                dir += Math.PI / 3.0;
            mController.buildRobot(RobotType.LUMBERJACK, new Direction(dir));
            Clock.yield();
        }
    }
}
