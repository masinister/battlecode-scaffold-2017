package boi.behavior;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Repeat<B extends Behavior & Repeatable> extends Behavior implements Repeatable {

    public static final int FOREVER = -1;

    private final B mBehavior;
    private final int mTimes;
    private int mCount;

    public Repeat(RobotController controller, B b, int times) {
        super(controller);
        mBehavior = b;
        mTimes = times;
        mCount = 0;
    }

    @Override
    public void step() throws GameActionException {
        mBehavior.step();
        if (mBehavior.isDone())
            if (mTimes < 0 || ++mCount < mTimes)
                mBehavior.reset();
    }

    @Override
    public boolean canStep() {
        return !isDone() && mBehavior.canStep();
    }

    @Override
    public boolean isDone() {
        return mCount == mTimes && mBehavior.isDone();
    }

    @Override
    public void reset() {
        mBehavior.reset();
        mCount = 0;
    }
}
