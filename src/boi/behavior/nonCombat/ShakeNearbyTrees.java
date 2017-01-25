package boi.behavior.nonCombat;

import battlecode.common.*;
import boi.behavior.move.*;
import boi.behavior.*;

public class ShakeNearbyTrees extends Behavior {


    public ShakeNearbyTrees(RobotController controller) {
        super(controller);
    }
    @Override
    public void step() throws GameActionException {
    TreeInfo[] nearbyTrees = mController.senseNearbyTrees(-1);
            if (nearbyTrees.length > 1){
                for (int i = 0;  i < (nearbyTrees.length); i++){
                    MapLocation tree = nearbyTrees[i].getLocation();
                    while(!mController.canShake(nearbyTrees[i].getID())) {
                        Behavior moveToTree = new BehaviorMove(mController, tree.x, tree.y);
                        moveToTree.step();
                    }
                    mController.shake((nearbyTrees[i]).getID());

                }

            }

            else {
                    Behavior randomStep = new TryMoveRandomDirection(mController, 20, 3);
                    randomStep.step();
            }

        }
    }

