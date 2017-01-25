package boi.behavior.build;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;

/**
 * Created by mason on 1/24/2017.
 */
public class CircleGarden extends Behavior {

    public CircleGarden(RobotController controller) {
        super(controller);
    }

    @Override
    public void step() throws GameActionException {
        MapLocation[] archons = mController.getInitialArchonLocations(mController.getTeam().opponent());
        for (int i = 0; i < 5; i++) {
            Direction d = mController.getLocation().directionTo(archons[0]).rotateLeftDegrees((float)(i*Math.PI/3));
            if(mController.canPlantTree(d))
                mController.plantTree(d);
        }
        TreeInfo[] trees = mController.senseNearbyTrees();
        for (TreeInfo tree : trees){
            if(mController.canWater(tree.getID())){
                if(tree.getHealth()<tree.maxHealth*0.9){
                    mController.water(tree.getID());
                }
            }
        }
    }

    @Override
    public boolean canStep() {
        return mController.hasTreeBuildRequirements() || mController.canWater();
    }
}