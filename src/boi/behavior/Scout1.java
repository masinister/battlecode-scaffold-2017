package boi.behavior;

import battlecode.common.Direction;
import battlecode.common.RobotController;

public class Scout1 extends Behavior {

    public Scout1(RobotController controller) {
        super(controller);
    }

    @Override
    public boolean next() throws Exception {
        Behavior randomMove = new TryMoveDirection(mController, new Direction((float) (Math.random() * 2 * Math.PI)), 90, 2);
        return randomMove.next();
    }

    @Override
    public boolean isDone() throws Exception {
        return false;
    }

    @Override
    public void destroy() throws Exception {

    }
}
