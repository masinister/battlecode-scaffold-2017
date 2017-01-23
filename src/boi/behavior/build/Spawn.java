package boi.behavior.build;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;

public class Spawn extends Behavior implements Repeatable {

    protected Direction mDirection;

    private final RobotType mType;
    private boolean mSpawned;

    public Spawn(RobotController controller, Direction dir, RobotType type) {
        super(controller);
        mDirection = dir;
        mType = type;
    }

    @Override
    public void step() throws GameActionException {
        mController.buildRobot(mType, mDirection);
        mSpawned = true;
    }

    @Override
    public boolean canStep() {
        return !mSpawned && mController.hasRobotBuildRequirements(mType) && mController.canBuildRobot(mType, mDirection);
    }

    @Override
    public boolean isDone() {
        return mSpawned;
    }

    @Override
    public void reset() {
        mSpawned = false;
    }
}
