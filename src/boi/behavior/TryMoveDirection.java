package boi.behavior;

import battlecode.common.Direction;
import battlecode.common.RobotController;

public class TryMoveDirection extends Behavior {

    private Direction dir;
    private float degreeOffset;
    private int checksPerSide;

    public TryMoveDirection(RobotController controller, Direction dir, float degreeOffset, int checksPerSide) {
        super(controller);
        this.dir=dir;
        this.degreeOffset=degreeOffset;
        this.checksPerSide=checksPerSide;
    }

    @Override
    public boolean next() throws Exception {
            // First, try intended direction
            if (mController.canMove(dir)) {
                mController.move(dir);
                return true;
            }

            // Now try a bunch of similar angles
            boolean moved = false;
            int currentCheck = 1;

            while(currentCheck<=checksPerSide) {
                // Try the offset of the left side
                if(mController.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                    mController.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                    return true;
                }
                // Try the offset on the right side
                if(mController.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                    mController.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                    return true;
                }
                // No move performed, try slightly further
                currentCheck++;
            }

            // A move never happened, so return false.
            return false;
    }

    @Override
    public boolean isDone() throws Exception {
        return false;
    }

    @Override
    public void destroy() throws Exception {

    }
}
