package boi.behavior.fight;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import boi.behavior.Behavior;

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
        float distToEnemy;
        if(enemies.length > 0) {
            distToEnemy = mController.getLocation().distanceTo(enemies[0].getLocation());
            if (distToEnemy < 2 && mController.canFirePentadShot())
                mController.firePentadShot(mController.getLocation().directionTo(enemies[0].getLocation()));
            else if (distToEnemy < 4 && mController.canFireTriadShot())
                mController.fireTriadShot(mController.getLocation().directionTo(enemies[0].getLocation()));
            else if (mController.canFireSingleShot())
                mController.fireSingleShot(mController.getLocation().directionTo(enemies[0].getLocation()));
        }
    }

    @Override
    public boolean canStep() {
        RobotInfo[] enemies = mController.senseNearbyRobots(-1,mController.getTeam().opponent());
        return enemies.length>0;
    }
}