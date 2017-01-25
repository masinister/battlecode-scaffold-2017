package boi.units;

import battlecode.common.*;

import java.util.Random;

public class Scout extends Unit {

    private float dir = 0, closestDistToEArchon = 1000;
    private int closestTreeI = 0;
    private TreeInfo[] trees;
    private RobotInfo[] robots, allRobots;
    private MapLocation[] eArchon;
    private Random rnd = new Random();

    public Scout(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws Exception {
        eArchon = mController.getInitialArchonLocations(mController.getTeam().opponent());
        while (true) {
            trees = mController.senseNearbyTrees();
            robots = mController.senseNearbyRobots(10, mController.getTeam().opponent());
            allRobots = mController.senseNearbyRobots(-1, mController.getTeam().opponent());
            if (trees.length == 0) {
                if (!mController.canMove(mController.getLocation().directionTo(eArchon[0]))) {
                    while (!mController.canMove(mController.getLocation().directionTo(eArchon[0])))
                        dir += Math.PI / 12.0;
                    mController.move(new Direction(dir));
                } else mController.move(mController.getLocation().directionTo(eArchon[0]));
            } else if (trees.length > 0) {
                if (robots.length == 0) {
                    closestDistToEArchon = 1000;
                    for (int i = 0; i < trees.length; i++) {
                        if (trees[i].getLocation().distanceTo(eArchon[0]) < closestDistToEArchon) {
                            closestDistToEArchon = trees[i].getLocation().distanceTo(eArchon[0]);
                            closestTreeI = i;
                        }
                    }
                    if (mController.canMove(mController.getLocation().directionTo(trees[closestTreeI].getLocation())))
                        mController.move(mController.getLocation().directionTo(trees[closestTreeI].getLocation()));
                } else {
                    float farthestFromEnemy = 0;
                    int farthestTreeI = 0;
                    for (int i = 0; i < trees.length; i++) {
                        if (trees[i].getLocation().distanceTo(robots[0].getLocation()) > farthestFromEnemy) {
                            farthestFromEnemy = trees[i].getLocation().distanceTo(robots[0].getLocation());
                            farthestTreeI = i;
                        }
                    }
                    if (mController.canMove(mController.getLocation().directionTo(trees[farthestTreeI].getLocation())))
                        mController.move(mController.getLocation().directionTo(trees[farthestTreeI].getLocation()));
                }
            }
            Clock.yield();
        }
    }
}