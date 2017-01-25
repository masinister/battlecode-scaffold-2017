package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Multitask;
import boi.behavior.Repeat;
import boi.behavior.build.CircleGarden;
import boi.behavior.build.Spawn;
import boi.behavior.masonGardener.GetAlone;
import boi.behavior.masonGardener.TryToPlantTree;
import boi.behavior.masonGardener.WaterTrees;
import boi.behavior.move.Move;


public class Gardener extends Unit {

    public Gardener(RobotController rc) {
        super(rc);
    }

    private final int TREES_CHANNEL = 2000;

    @Override
    public void lifetime() throws GameActionException{
        if(mController.senseNearbyTrees().length>2) {
            spawn(RobotType.LUMBERJACK);
        }

        GetAlone getAlone = new GetAlone(mController,4);

        while (!getAlone.isDone()){
            if (getAlone.canStep())
                getAlone.step();
            Clock.yield();
        }

        CircleGarden circleGarden = new CircleGarden(mController);
        Repeat spawn = new Repeat<>(mController,new Spawn(mController,RobotType.SOLDIER,RobotType.LUMBERJACK,RobotType.SCOUT),Repeat.FOREVER);
        Multitask multitask = new Multitask(mController);
        multitask.addTask(spawn,1,null);
        multitask.addTask(circleGarden,2,null);

        while (!multitask.isDone()){
            while (multitask.canStep())
                multitask.step();
            Clock.yield();
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

    private boolean trees() throws GameActionException {
        return mController.getRoundNum() - mController.readBroadcastInt(TREES_CHANNEL)<100;
    }
}
