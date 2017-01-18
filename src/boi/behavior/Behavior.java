package boi.behavior;

import battlecode.common.RobotController;

public abstract class Behavior {

    protected final RobotController mController;

    protected Behavior(RobotController controller) {
        mController = controller;
    }

    // return true if the next step may be performed during the current turn, otherwise false
    public abstract boolean next() throws Exception;

    public abstract boolean isDone() throws Exception;

    public abstract void destroy() throws Exception;

    // Todo: add mechanism for polling success
}
