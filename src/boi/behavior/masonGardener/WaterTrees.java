package boi.behavior.masonGardener;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.TreeInfo;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;
import boi.behavior.move.TryMoveDirection;
import boi.behavior.move.TryMoveRandomDirection;

/**
 * Created by mason on 1/23/2017.
 */
public class WaterTrees extends Behavior implements Repeatable {

    private Behavior tryMove = new TryMoveRandomDirection(mController,30,3);
    private TreeInfo[] trees=new TreeInfo[0];

    public WaterTrees(RobotController controller) {
        super(controller);
    }

    @Override
    public void step() throws GameActionException {
        trees = mController.senseNearbyTrees(-1,mController.getTeam());
        if(trees.length>0) {
            TreeInfo waterTree = trees[0];
            for (TreeInfo tree : trees) {
                if(tree.getHealth()<waterTree.getHealth()){
                    waterTree=tree;
                }
            }
            if(mController.canWater(waterTree.getID())) {
                mController.water(waterTree.getID());
            }
            else tryMove=new TryMoveDirection(mController, mController.getLocation().directionTo(waterTree.getLocation()),30,3);
        }
        else tryMove=new TryMoveRandomDirection(mController,30,2);

        if(tryMove.canStep())
            tryMove.step();
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean canStep() {
        return  (trees.length>0 && mController.canWater()) || !mController.hasMoved();
    }
}
