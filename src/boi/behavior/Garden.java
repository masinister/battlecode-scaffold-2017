package boi.behavior;

import battlecode.common.Direction;
import battlecode.common.RobotInfo;
import battlecode.common.Team;
import battlecode.common.TreeInfo;
import boi.units.Unit;

/**
 * Created by douwz on 1/17/2017.
 */
public class Garden extends Behavior {

    public Garden(Unit u) {
        super(u);
    }
    @Override
    public boolean next() throws Exception {
//        TreeInfo[] trees = mController.senseNearbyTrees();
//        if (trees.length > 0) {
//            if (mController.canFireSingleShot()) {
//                mController.fireSingleShot(mController.getLocation().directionTo(robots[0].location));
//            }
//        }
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
