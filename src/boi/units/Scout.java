package boi.units;

import battlecode.common.RobotController;
import boi.behavior.Repeat;
import boi.behavior.move.Move;

public class Scout extends Unit {

    public Scout(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        Repeat scram = new Repeat<>(mController, new Move(mController, 20, 3, Move::randomly), Repeat.FOREVER);
        scram.finish();
    }

}
