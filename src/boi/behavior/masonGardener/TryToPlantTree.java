package boi.behavior.masonGardener;

import battlecode.common.*;
import boi.RobotPlayer;
import boi.behavior.Behavior;
import boi.behavior.Repeat;
import boi.behavior.Repeatable;
import boi.behavior.move.BehaviorMove;
import boi.behavior.move.TryMoveDirection;
import boi.behavior.move.TryMoveRandomDirection;
import boi.units.Gardener;

import java.util.Base64;

/**
 * Created by mason on 1/23/2017.
 */
public class TryToPlantTree extends Behavior implements Repeatable{

    private boolean done;

    public TryToPlantTree(RobotController controller) {
        super(controller);
    }

    @Override
    public void step() throws GameActionException {
        float theta=0;
        while (theta<=2*Math.PI){
            mController.setIndicatorDot(mController.getLocation().add(theta,RobotType.GARDENER.bodyRadius+GameConstants.BULLET_TREE_RADIUS),0,0,0);
            if(!mController.isCircleOccupiedExceptByThisRobot(mController.getLocation().add(theta, RobotType.GARDENER.bodyRadius+GameConstants.BULLET_TREE_RADIUS),RobotType.TANK.bodyRadius+1)){
                if(mController.canPlantTree(new Direction(theta))) {
                    mController.plantTree(new Direction(theta));
                    done=true;
                    break;
                }
            }
            theta+=Math.PI/2;
        }
    }

    @Override
    public void reset() {
        done=false;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public boolean canStep(){
        return !isDone() && mController.hasTreeBuildRequirements();
    }
}
