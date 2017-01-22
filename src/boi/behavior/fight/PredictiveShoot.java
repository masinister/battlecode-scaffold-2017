package boi.behavior.fight;

import battlecode.common.*;
import boi.behavior.Behavior;

public strictfp class PredictiveShoot extends Behavior {

    private float bulletSpeed;
    private RobotInfo target;
    private MapLocation current,last,me;

    /**
     * Basic (possibly not working) predictive shooting
     */

    public PredictiveShoot(RobotController controller, RobotInfo targetRobot) {
        super(controller);
        target=targetRobot;
        me=mController.getLocation();
        current=target.getLocation();
        last=target.getLocation();
        bulletSpeed=mController.getType().bulletSpeed;
    }

    /**
     * The first shot is not predictive because it needs 2 turns to calculate velocity based on position
     */
    @Override
    public void step() throws GameActionException {
        me=mController.getLocation();
        last = new MapLocation(current.x,current.y);
        current = target.getLocation();

        //maybe i should just make a vector class
        float vx = current.x - last.x;
        float vy = current.y - last.y;
        float dx = current.x - me.x;
        float dy = current.y - me.y;
        float r = (float) Math.sqrt(dx*dx + dy*dy);
        float theta = (float) (Math.atan2(dy, dx) + Math.asin((dx * vy - dy * vx) / (bulletSpeed * r)));

        if(mController.canFireSingleShot()){
            System.out.println("pew ");
            mController.fireSingleShot(new Direction(theta));
        }
    }

    @Override
    public boolean canStep() {
        return !mController.hasAttacked();
    }

    @Override
    public boolean isDone() {
        return target.getHealth()==0;
    }
}
