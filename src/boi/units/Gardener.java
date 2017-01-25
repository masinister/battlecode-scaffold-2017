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
        final Multitask garden = new Multitask(mController);
        Repeat water = new Repeat<>(mController,new WaterTrees(mController),Repeat.FOREVER);
        Repeat plant = new Repeat<>(mController,new TryToPlantTree(mController),Repeat.FOREVER);
        Repeat spawn = new Repeat<>(mController, new Spawn(mController, RobotType.SOLDIER, RobotType.LUMBERJACK), 4);

        garden.addTask(plant,3,null);
        garden.addTask(water,2,null);
        garden.addTask(spawn,1,null);

        while (!garden.isDone()){
            while(garden.canStep())
                garden.step();
            Clock.yield();
        }
    }
}
