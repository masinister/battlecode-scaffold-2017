package boi.units;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import boi.behavior.Behavior;
import boi.behavior.CircleGardener;

public class Gardener extends Unit {

    public Gardener(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
//        if (mController.readBroadcast(0) == 10) {
//            Behavior produce = new
//        }
        Behavior circleGardener = new CircleGardener(this);
        while (!circleGardener.isDone()){
            if(circleGardener.next())
                Clock.yield();
        }
        circleGardener.destroy();
    }
}
