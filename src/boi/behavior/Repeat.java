package boi.behavior;

import battlecode.common.RobotController;

public class Repeat extends RepeatableBehavior {

    public static final int FOREVER = -1;

    private final RepeatableBehavior mBehavior;
    private final int mTimes;
    private int mCount;

    public Repeat(RobotController controller, RepeatableBehavior b, int times) {
        super(controller);
        mBehavior = b;
        mTimes = times;
        mCount = 0;
    }

    @Override
    public void step() throws Exception {
        if (!isDone()) {
            if (mBehavior.canStep())
                mBehavior.step();
            if (mBehavior.isDone())
                if (mTimes < 0 || ++mCount < mTimes)
                    mBehavior.reset();
        } else throw new IllegalStateException("I should be done");
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
