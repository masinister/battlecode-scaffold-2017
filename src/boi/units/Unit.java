package boi.units;

import battlecode.common.RobotController;

public abstract class Unit {

    protected final RobotController mController;

    protected Unit(RobotController rc) {
        mController = rc;
    }

    public void lifetime() throws Exception {
        while (true) ;
    }

    public void destroy() throws Exception {}
}
