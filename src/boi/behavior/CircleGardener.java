package boi.behavior;

import battlecode.common.*;

import java.util.ArrayList;

public class CircleGardener extends Behavior {

    ArrayList<TreeInfo> myTrees = new ArrayList<>();
    private boolean alone = false;
    private float theta = 0;
    private float meme = 0;

    public CircleGardener(RobotController controller) {
        super(controller);
    }

    @Override
    public void step() throws Exception {
        RobotInfo[] robots = mController.senseNearbyRobots();
        if(robots.length>0 && !alone){
            Behavior tryMove = new TryMoveDirection(mController, robots[0].getLocation().directionTo(mController.getLocation()), 20, 3);
            alone=true;
            tryMove.step();
        }

        if(mController.hasTreeBuildRequirements()) {
            while (theta < 2 * Math.PI) {
                theta += .01;
                if (mController.canPlantTree(new Direction(theta))) {
                    mController.plantTree(new Direction(theta));
                    break;
                }
            }
        }

        TreeInfo[] trees = mController.senseNearbyTrees();

        if(trees.length<5)
            theta=0;

        for(TreeInfo tree : trees){
            if(tree.getHealth()<tree.getMaxHealth()-GameConstants.WATER_HEALTH_REGEN_RATE && mController.canWater(tree.getID()))
                mController.water(tree.getID());
            if(mController.canShake(tree.getID()))
                mController.shake(tree.getID());
        }

        if(mController.canBuildRobot(RobotType.SCOUT,new Direction(meme)))
            mController.buildRobot(RobotType.SCOUT,new Direction(meme));
        else meme+=.01;
    }
}
