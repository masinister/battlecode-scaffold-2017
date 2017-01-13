package boi.behavior;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import boi.units.Unit;

public strictfp class BehaviorMove extends Behavior {

    private static final float DEGREE_INTERVAL = 90;
    private final MapLocation mTarget;
    private float mInterval;

    public BehaviorMove(Unit actor, float targetX, float targetY) {
        super(actor);
        mTarget = new MapLocation(targetX, targetY);
    }

    @Override
    public boolean next() throws Exception {
        if (!mController.hasMoved()) {
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

            return true;
        } else return false;
    }

    @Override
    public boolean isDone() throws Exception {
        return mTarget.distanceTo(mController.getLocation()) == 0;
    }

    @Override
    public void destroy() throws Exception {

    }
}
