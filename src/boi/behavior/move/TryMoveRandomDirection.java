package boi.behavior.move;

import battlecode.common.Direction;
import battlecode.common.RobotController;

public class TryMoveRandomDirection extends TryMoveDirection {

    public TryMoveRandomDirection(RobotController controller, float degreeOffset, int checksPerSide) {
        super(controller, null, degreeOffset, checksPerSide);
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        do {
            dir = new Direction((float) (Math.random() * Math.PI * 2));
        } while (!mController.canMove(dir));
    }
}
