package boi.behavior;

import battlecode.common.Direction;
import battlecode.common.RobotController;

public class TryMoveRandomDirection extends TryMoveDirection {

    public TryMoveRandomDirection(RobotController controller, float degreeOffset, int checksPerSide) {
        super(controller, null, degreeOffset, checksPerSide);
    }

    @Override
    public void step() throws Exception {
        dir = new Direction((float) (Math.random() * Math.PI * 2));
        super.step();
    }
}
