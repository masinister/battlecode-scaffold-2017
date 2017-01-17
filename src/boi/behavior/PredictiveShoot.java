package boi.behavior;

import battlecode.common.*;
import boi.units.Unit;

/**
 * Created by mason on 1/13/2017.
 */
public strictfp class PredictiveShoot extends Behavior{

    private RobotInfo target;
    private MapLocation current,last,me;
    float bulletSpeed;

    /**
     * Basic (possibly not working) predictive shooting
     */

    public PredictiveShoot(Unit actor, RobotInfo targetRobot) {
        super(actor);
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
    public boolean next() throws Exception {
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
        return true;
    }

    @Override
    public boolean isDone() throws Exception {
        return target.getHealth()==0;
    }

    @Override
    public void destroy() throws Exception {
    }
}
