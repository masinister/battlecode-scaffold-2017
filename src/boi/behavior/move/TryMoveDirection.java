package boi.behavior.move;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;

public class TryMoveDirection extends Behavior implements Repeatable {

    protected Direction dir;
    private float degreeOffset;
    private int checksPerSide;
    private boolean moved;

    public TryMoveDirection(RobotController controller, Direction dir, float degreeOffset, int checksPerSide) {
        super(controller);
        this.dir=dir;
        this.degreeOffset=degreeOffset;
        this.checksPerSide=checksPerSide;
    }

    @Override
    public void step() throws GameActionException {
            // First, try intended direction
            if (mController.canMove(dir)) {
                mController.move(dir);
                moved = true;
                return;
            }

            // Now try a bunch of similar angles
            int currentCheck = 1;

            while(currentCheck<=checksPerSide) {
                // Try the offset of the left side
                if(mController.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                    mController.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                    moved = true;
                    return;
                }
                // Try the offset on the right side
                if(mController.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                    mController.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                    moved = true;
                    return;
                }
                // No move performed, try slightly further
                currentCheck++;
            }
    }

    @Override
    public boolean canStep() {
        return !(moved || mController.hasMoved());
    }

    @Override
    public boolean isDone() {
        return moved;
    }

    @Override
    public void reset() {
        moved = false;
    }
}
