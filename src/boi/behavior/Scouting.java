package boi.behavior;

import battlecode.common.Direction;
import battlecode.common.RobotInfo;
import battlecode.common.Team;
import boi.units.Unit;

/**
 * Created by douwz on 1/16/2017.
 */
public class Scouting extends Behavior {

    float dir = (float) Math.PI / 4F;

    public Scouting(Unit u) {
        super(u);
    }
    @Override
    public boolean next() throws Exception {
        if (mController.canMove(new Direction(dir)))
            mController.move(new Direction(dir));
        else dir += (float) Math.PI / 4F;

        Team enemy = mController.getTeam().opponent();
        RobotInfo[] robots = mController.senseNearbyRobots(-1, enemy);
        if (robots.length > 0) {
            if (mController.canFireSingleShot()) {
                mController.fireSingleShot(mController.getLocation().directionTo(robots[0].location));
            }
        }
        return true;
    }

    @Override
    public boolean isDone() throws Exception {
        return false;
    }

    @Override
    public void destroy() throws Exception {

    }
}
