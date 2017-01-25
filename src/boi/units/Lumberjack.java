package boi.units;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import boi.behavior.Behavior;
import boi.behavior.ClearTheArea;

public class Lumberjack extends Unit {

    public Lumberjack(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        Behavior clearArea = new ClearTheArea(mController);
        while (!clearArea.isDone()){
            while (clearArea.canStep())
                clearArea.step();
            Clock.yield();
        }
    }
}
