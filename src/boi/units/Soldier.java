package boi.units;

import battlecode.common.*;

public class Soldier extends Unit {

    public Soldier(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        while (true) {
            Clock.yield();
        }
    }
}
