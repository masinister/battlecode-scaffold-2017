package boi.behavior;

import battlecode.common.*;
import boi.behavior.move.Move;

import java.util.Random;

/**
 * Created by douwz on 1/23/2017.
 */
public class ClearTheArea extends Behavior {

    private float dir = 0, initDir = 0;
    private double toRads = 15.0;
    private int treeI = 0, chopCount;
    private boolean chopping = false;
    private TreeInfo[] trees;
    private RobotInfo[] closeRobots, allrobots;
    private Random rnd = new Random();

    public ClearTheArea(RobotController controller) throws GameActionException {
        super(controller);
        dir = mController.readBroadcastFloat(0);
        initDir = dir;
    }
    @Override
    public void step() throws GameActionException {
        chopping = false;
        trees = mController.senseNearbyTrees();
        allrobots = mController.senseNearbyRobots(-1, mController.getTeam().opponent());
        closeRobots = mController.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS - 1, mController.getTeam().opponent());
//        for (int i = 0; i < trees.length; i++) {
//            if (trees[i].getTeam() != mController.getTeam()) {
//                treeI = i;
//                break;
//            }
//        }
        if (trees.length > 0) {
            if (trees.length > 5)
                mController.broadcast(2000, mController.getRoundNum());
            if (mController.canChop(trees[0].getID()) && trees[0].getTeam() != mController.getTeam())
                chopping = true;
        }
        if (closeRobots.length > 0 && !mController.hasAttacked()) {
            if (mController.canStrike()) {
                mController.strike();
            }
        }
        else if (!chopping) {
            if (allrobots.length == 0) {
                initDir = dir;
                while (!mController.canMove(new Direction(dir))) {
                    dir += Math.PI / toRads;
                    if (dir == initDir + (float) (2F * Math.PI)) {
                        dir = initDir;
                        break;
                    }
                }
                if (mController.canMove(new Direction(dir))) {
                    mController.move(new Direction(dir));
                    dir += Math.PI / toRads;
                }
                if (dir >= 2.0 * Math.PI) {
                    dir = 0;
                    toRads += 30.0;
                }
            } else {
                if (mController.canMove(mController.getLocation().directionTo(allrobots[0].getLocation())))
                    mController.move(mController.getLocation().directionTo(allrobots[0].getLocation()));
            }
        } else {
            if (mController.canChop(trees[0].getID()))
                mController.chop(trees[0].getID());
            else chopping = false;
        }
    }

    @Override
    public boolean canStep() {
        return mController.canStrike() && !mController.hasMoved();
    }
}