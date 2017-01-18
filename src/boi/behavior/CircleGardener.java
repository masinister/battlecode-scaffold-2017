package boi.behavior;

import battlecode.common.*;
import boi.units.Unit;

import java.util.ArrayList;

/**
 * Created by mason on 1/17/2017.
 */
public class CircleGardener extends Behavior {

    boolean alone=false;
    float theta=0;
    float meme=0;

    public CircleGardener(Unit actor) {
        super(actor);
    }

    ArrayList<TreeInfo> myTrees = new ArrayList<>();

    @Override
    public boolean next() throws Exception {
//        RobotInfo[] robots = mController.senseNearbyRobots();
//        if(robots.length>0 && !alone){
//            Behavior tryMove = new TryMoveDirection(unit, robots[0].getLocation().directionTo(mController.getLocation()),20,3);
//            alone=true;
//            return tryMove.next();
//        }

        theta = 0;
        if(mController.hasTreeBuildRequirements()) {
            while (theta <= 2 * Math.PI) {
                if (mController.canPlantTree(new Direction(theta))) {
                    mController.plantTree(new Direction(theta));
                    break;
                }
                theta += Math.PI / 3.0;
            }
        }

        TreeInfo[] trees = mController.senseNearbyTrees();
        for(TreeInfo tree : trees){
            if(tree.getHealth()<tree.getMaxHealth()-GameConstants.WATER_HEALTH_REGEN_RATE && mController.canWater(tree.getID()))
                mController.water(tree.getID());
            if(mController.canShake(tree.getID()))
                mController.shake(tree.getID());
        }

        if(mController.canBuildRobot(RobotType.SCOUT, new Direction(meme)))
            mController.buildRobot(RobotType.SCOUT, new Direction(meme));
        else meme += .01;
        return true;
    }

    @Override
    public boolean isDone() throws Exception {
        return false;
    }

    @Override
    public void destroy() throws Exception {

    }
}