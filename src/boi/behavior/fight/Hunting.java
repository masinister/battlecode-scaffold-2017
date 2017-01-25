package boi.behavior.fight;

import battlecode.common.*;
import boi.behavior.Behavior;

import java.util.Random;

/**
 * Created by douwz on 1/24/2017.
 */
public class Hunting extends Behavior {

    private float dir = 0;
    private RobotInfo[] robots;
    private Random rnd = new Random();

    public Hunting(RobotController rc) {
        super(rc);
    }

    @Override
    public void step() throws GameActionException {
        robots = mController.senseNearbyRobots(6, mController.getTeam().opponent());
        if (robots.length > 0 && mController.canFireSingleShot())
            mController.fireSingleShot(mController.getLocation().directionTo(robots[0].getLocation()));
        if (robots.length == 0) {
            while (!mController.canMove(new Direction(dir))) {
                dir = rnd.nextFloat() * 2F;
            }
            mController.move(new Direction(dir));
        } else {
             if (mController.canMove(mController.getLocation().directionTo(robots[0].getLocation())))
                mController.move(mController.getLocation().directionTo(robots[0].getLocation()));
         }
        Clock.yield();
    }
}
