package boi.behavior.masonGardener;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.move.Move;

/**
 * Created by mason on 1/25/2017.
 */
public class GetAlone extends Behavior {

    private int triesLeft;

    public GetAlone(RobotController controller, int numTries) {
        super(controller);
        triesLeft=numTries;
    }

    @Override
    public void step() throws GameActionException {
        triesLeft--;
        RobotInfo robots[] = mController.senseNearbyRobots();
        if(robots.length>0) {
            MapLocation location = mController.getLocation().add(robots[0].getLocation().directionTo(mController.getLocation()));
            for (RobotInfo robot : robots) {
                if (robot.getType() == RobotType.LUMBERJACK) {
                    location = robot.getLocation();
                    break;
                }
            }
            Move move = new Move(mController, 30, 2, Move.to(location));
            if(move.canStep())
                move.step();
        }
        else {
            Move move = new Move(mController,45,2,Move::randomly);
            if (move.canStep())
                move.step();
        }
    }

    @Override
    public boolean canStep() {
        return !mController.hasMoved();
    }

    @Override
    public boolean isDone() {
        return triesLeft>0;
    }
}
