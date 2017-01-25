package boi.behavior.build;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;

public class Spawn extends Behavior implements Repeatable {

    protected Direction mDirection;

    private final RobotType[] mTypes;
    int current=0;
    private boolean mSpawned;

    public Spawn(RobotController controller, RobotType... type) {
        super(controller);
        mDirection = new Direction(0);
        mTypes = type;
    }

    public Direction getDir(){
        float theta=0;
        while (theta < Math.PI * 2){
            if(mController.canBuildRobot(mTypes[current],new Direction(theta)))
                return new Direction(theta);
            theta+=Math.PI/4d;
        }
        return null;
    }

    @Override
    public void step() throws GameActionException {
        mController.buildRobot(mTypes[current], getDir());
        current=(current+1)% mTypes.length;
        mSpawned = true;
    }

    @Override
    public boolean canStep() {
        return !mSpawned && mController.hasRobotBuildRequirements(mTypes[current]) && getDir()!=null;
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
