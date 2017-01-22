package boi.behavior.move;

import battlecode.common.*;
import boi.behavior.Behavior;

public strictfp class BehaviorMove extends Behavior {

    private static final float DEGREE_INTERVAL = 10;
    private final MapLocation mTarget;
    private float mInterval;

    public BehaviorMove(RobotController controller, float targetX, float targetY) {
        super(controller);
        mTarget = new MapLocation(targetX, targetY);
    }

    @Override
    public void step() throws GameActionException {
        MapLocation current = mController.getLocation();
        float remaining = current.distanceTo(mTarget);
        if (remaining > mController.getType().strideRadius || !mController.canMove(mTarget)) {
            Direction forward = new Direction(current, mTarget);
            Direction proposed = forward;
            int trial = 0;
            while (!mController.canMove(proposed) && ++trial + 1 < 360 / mInterval) {
                if (trial % 2 == 0)
                    proposed = forward.rotateRightDegrees(trial / 2 * mInterval);
                else proposed = forward.rotateLeftDegrees((trial - 1) / 2 * mInterval);
            }
            if (mController.canMove(proposed)) {
                mController.move(proposed);
                mInterval = DEGREE_INTERVAL;
            } else mInterval /= 2;
        } else mController.move(mTarget);
    }

    @Override
    public boolean canStep() {
        return !(mController.hasMoved() || isDone());
    }

    @Override
    public boolean isDone() {
        return mTarget.distanceTo(mController.getLocation()) == 0;
    }
}
