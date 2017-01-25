package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Multitask;
import boi.behavior.Repeat;
import boi.behavior.build.Spawn;
import boi.behavior.masonGardener.TryToPlantTree;
import boi.behavior.masonGardener.WaterTrees;


public class Gardener extends Unit {

    public Gardener(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws GameActionException{
        TreeInfo[] trees = mController.senseNearbyTrees();
        if(trees.length>2 && mController.getRoundNum()<100)
            spawn(RobotType.LUMBERJACK);

        if(mController.isCircleOccupiedExceptByThisRobot(mController.getLocation(),4*GameConstants.BULLET_TREE_RADIUS+2*RobotType.GARDENER.bodyRadius));

        while (true)
            Clock.yield();
    }

    private void spawn(RobotType robotType) throws GameActionException {
        Spawn spawn = new Spawn(mController,robotType);
        if(spawn.canStep())
            spawn.step();
    }
}
