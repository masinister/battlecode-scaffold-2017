package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Multitask;
import boi.behavior.Repeat;
import boi.behavior.build.Spawn;
import boi.behavior.masonGardener.TryToPlantTree;
import boi.behavior.masonGardener.WaterTrees;
import boi.behavior.move.Move;


public class Gardener extends Unit {

    public Gardener(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws GameActionException{
        if(mController.senseNearbyTrees().length>2) {
            spawn(RobotType.LUMBERJACK);
        }

        while (true)
            Clock.yield();
    }

    private void spawn(RobotType robotType) throws GameActionException {
        Spawn spawn = new Spawn(mController,robotType);
        while (!spawn.isDone()) {
            if (spawn.canStep())
                spawn.step();
        }
    }
}
