package boi.units;

import battlecode.common.*;
import boi.behavior.move.Move;

public class Tank extends Unit {

    private RobotInfo[] robots;
    private Direction dir;

    public Tank(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        while (true) {
            robots = mController.senseNearbyRobots(-1, mController.getTeam().opponent());
            if (robots.length > 0 && !mController.hasAttacked()) {
                if (mController.canFireTriadShot()) {
                    mController.firePentadShot(mController.getLocation().directionTo(robots[0].getLocation()));
                }
                MapLocation location = robots[0].getLocation();
                Move move = new Move(mController, 20, 5, Move.to(location));
                while (!move.isDone()) {
                    while (move.canStep())
                        move.step();
                }
            } else {
                MapLocation location = mController.getInitialArchonLocations(mController.getTeam().opponent())[0];
                Move move = new Move(mController, 20, 5, Move.to(location));
                while (!move.isDone()) {
                    while (move.canStep())
                        move.step();
                }
            }
            Clock.yield();
        }
    }
}
