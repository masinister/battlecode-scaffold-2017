package boi.units;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import boi.behavior.*;

public class Archon extends Unit {

    public Archon(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {

        Multitask multi = new Multitask(mController);
        Repeat scram = new Repeat(mController, new TryMoveRandomDirection(mController, 20, 3), Repeat.FOREVER);
        multi.addTask(scram, 1);

        while (!multi.isDone()) {
            while (multi.canStep())
                multi.step();
            Clock.yield();
        }
    }
}
