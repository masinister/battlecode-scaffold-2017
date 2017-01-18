package boi.units;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import boi.behavior.Behavior;
import boi.behavior.Scout1;

public class Scout extends Unit {

    public Scout(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        Behavior scout = new Scout1(mController);

        while (!scout.isDone()){
            if (scout.next())
                Clock.yield();
        }
        scout.destroy();
    }

}
