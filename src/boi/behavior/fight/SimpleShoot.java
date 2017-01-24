package boi.behavior.fight;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;

import java.util.Map;

/**
 * Created by mason on 1/24/2017.
 */

public class SimpleShoot extends Behavior{

    //TODO make smarter

    public SimpleShoot(RobotController controller) {
        super(controller);
    }

    @Override
    public void step() throws GameActionException {
        RobotInfo[] enemies = mController.senseNearbyRobots(-1,mController.getTeam().opponent());
        if(enemies.length>0 && mController.canFirePentadShot()){
            mController.firePentadShot(mController.getLocation().directionTo(enemies[0].getLocation()));
        }
    }

    @Override
    public boolean canStep() {
        RobotInfo[] enemies = mController.senseNearbyRobots(-1,mController.getTeam().opponent());
        return enemies.length>0;
    }
}

