package boi.behavior;

import battlecode.common.RobotController;

public abstract class Behavior {

    protected final RobotController mController;

    protected Behavior(RobotController controller) {
        mController = controller;
    }

    public abstract void step() throws Exception;

    public boolean canStep() {
        return !isDone();
    }

    public boolean isDone() {
        return false;
    }

    public void destroy() throws Exception {}

    // Todo: add mechanism for polling success
}
