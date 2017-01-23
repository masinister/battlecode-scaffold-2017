package boi.units;

import battlecode.common.*;
import boi.behavior.Multitask;
import boi.behavior.Repeat;
import boi.behavior.build.Spawn;
import boi.behavior.move.TryMoveRandomDirection;

public class Archon extends Unit {

    public Archon(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {

        final Multitask multi = new Multitask(mController);
        Repeat scram = new Repeat<>(mController, new TryMoveRandomDirection(mController, 20, 3), Repeat.FOREVER);
        Repeat spawn = new Repeat<>(mController, new Spawn(mController, Direction.getNorth(), RobotType.GARDENER), 4);
        multi.addTask(spawn, 1, repeat -> System.out.println("Completed: " + repeat));
        multi.addTask(scram, 2, null);

        while (!multi.isDone()) {
            while (multi.canStep())
                multi.step();
            Clock.yield();
        }
    }
}
