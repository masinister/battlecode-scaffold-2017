package boi.behavior;

import battlecode.common.*;
import boi.behavior.move.BehaviorMove;
import boi.behavior.move.TryMoveDirection;

import java.util.Random;

/**
 * Created by douwz on 1/23/2017.
 */
public class ClearTheArea extends Behavior {

    private float dir = 0;
    private double turnstep = 2.0;
    private int turncount = 0;
    private boolean chopping = false;
    private TreeInfo[] trees;
    private RobotInfo[] closeRobots, robots;
    private Random rnd = new Random();

    public ClearTheArea(RobotController controller) {
        super(controller);
        turnstep *= rnd.nextInt(3) + 1;
    }
    @Override
    public void step() throws GameActionException {
        chopping = false;
        trees = mController.senseNearbyTrees();
        robots = mController.senseNearbyRobots(-1, mController.getTeam().opponent());
        closeRobots = mController.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS - 1, mController.getTeam().opponent());
        if (trees.length > 0) {
            if (mController.canChop(trees[0].getID()))
                chopping = true;
        }
        if (closeRobots.length > 0 && !mController.hasAttacked()) {
            if (mController.canStrike()) {
                mController.strike();
                turnstep /= 4.0;
            }
        }
        else if (!chopping) {
            if (robots.length == 0) {
                while (!mController.canMove(new Direction(dir))) {
                    dir += Math.PI / turnstep;
                    turncount++;
                    if (turncount >= 1.0 * turnstep) {
                        turncount = 0;
                        turnstep *= -2.0;
                    }
                }
                if (mController.canMove(new Direction(dir)))
                    mController.move(new Direction(dir));
                dir -= Math.PI / (2.0 * turnstep);
            } else {
                if (mController.canMove(mController.getLocation().directionTo(robots[0].getLocation())))
                    mController.move(mController.getLocation().directionTo(robots[0].getLocation()));
            }
        } else {
            if (mController.canChop(trees[0].getID()))
                mController.chop(trees[0].getID());
            else chopping = false;
        }
//        Clock.yield();
    }

    @Override
    public boolean canStep() {
        return mController.canStrike() && !mController.hasMoved();
    }
}