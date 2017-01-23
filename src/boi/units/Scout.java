package boi.units;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import boi.behavior.Repeat;
import boi.behavior.move.TryMoveRandomDirection;

public class Scout extends Unit {

    public Scout(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        Repeat scram = new Repeat<>(mController, new TryMoveRandomDirection(mController, 20, 3), Repeat.FOREVER);

        while (!scram.isDone()) {
            scram.step();
            Clock.yield();
        }
    }

}
