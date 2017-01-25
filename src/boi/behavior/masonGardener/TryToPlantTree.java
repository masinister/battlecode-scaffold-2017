package boi.behavior.masonGardener;

import battlecode.common.*;
import boi.RobotPlayer;
import boi.behavior.Behavior;
import boi.behavior.Repeat;
import boi.behavior.Repeatable;
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
            //mController.setIndicatorDot(mController.getLocation().add(theta,RobotType.GARDENER.bodyRadius+GameConstants.BULLET_TREE_RADIUS),0,0,0);
            if(!mController.isCircleOccupiedExceptByThisRobot(mController.getLocation().add(theta+(float)(Math.random()*2*Math.PI), RobotType.GARDENER.bodyRadius+GameConstants.BULLET_TREE_RADIUS+.1f),RobotType.TANK.bodyRadius)){
                if(mController.canPlantTree(new Direction(theta))) {
                    mController.plantTree(new Direction(theta));
                    done=true;
                    break;
                }
            }
            theta+=Math.PI/4;
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
