package boi.behavior.build;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;

/**
 * Created by mason on 1/24/2017.
 */
public class CircleGarden extends Behavior {

    public CircleGarden(RobotController controller) {
        super(controller);
    }

    @Override
    public void step() throws GameActionException {
        for (int i = 0; i < 5; i++) {
            Direction d = new Direction((float)(i*Math.PI/3d));
            if(mController.canWater(mController.getLocation().add(d)))
                mController.water(mController.getLocation().add(d));
            else if(mController.canPlantTree(d))
                mController.plantTree(d);
        }
    }

    @Override
    public boolean canStep() {
        return mController.hasTreeBuildRequirements() || mController.canWater();
    }
}
