package boi.behavior;

import battlecode.common.RobotController;

public abstract class RepeatableBehavior extends Behavior {

    public RepeatableBehavior(RobotController controller) {
        super(controller);
    }

    public void reset() {}
}
