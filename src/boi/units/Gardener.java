package boi.units;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Multitask;
import boi.behavior.Repeat;
import boi.behavior.masonGardener.TryToPlantTree;
import boi.behavior.masonGardener.WaterTrees;
import boi.behavior.move.BehaviorMove;
import boi.behavior.move.TryMoveRandomDirection;

public class Gardener extends Unit {

    public Gardener(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws GameActionException{
        final Multitask garden = new Multitask(mController);
        Repeat water = new Repeat<>(mController,new WaterTrees(mController),Repeat.FOREVER);
        Repeat plant = new Repeat<>(mController,new TryToPlantTree(mController),Repeat.FOREVER);
        garden.addTask(plant,2,meme -> System.out.println("meme"));
        garden.addTask(water,1,null);

        while (!garden.isDone()){
            while(garden.canStep())
                garden.step();
            Clock.yield();
        }
    }
}
