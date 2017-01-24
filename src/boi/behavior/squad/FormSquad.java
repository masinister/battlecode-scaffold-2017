package boi.behavior.squad;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeat;
import boi.behavior.Repeatable;
import boi.behavior.move.TryMoveDirection;
import boi.behavior.move.TryMoveRandomDirection;

import java.sql.SQLClientInfoException;

/**
 * Created by mason on 1/24/2017.
 */
public class FormSquad extends Behavior{

    private int SQUAD_LEADER_ID = 100;
    private int LEADER_POS_X = 101;
    private int LEADER_POS_Y = 102;
    private boolean leading;
    private TryMoveDirection tryMove = new TryMoveDirection(mController,new Direction(0),45,2);
    private MapLocation[] archons = mController.getInitialArchonLocations(mController.getTeam().opponent());

    public FormSquad(RobotController controller) {
        super(controller);
    }

    @Override
    public void step() throws GameActionException {
        int leaderID = mController.readBroadcast(SQUAD_LEADER_ID);
        RobotInfo[] friends = mController.senseNearbyRobots(-1,mController.getTeam());
        RobotInfo[] enemies = mController.senseNearbyRobots(-1,mController.getTeam().opponent());
        TreeInfo[] trees = mController.senseNearbyTrees();

        //If there is no leader, become one
        if(leaderID==0){
            leading=true;
            mController.broadcast(SQUAD_LEADER_ID,mController.getID());
        }
        //Follow the leader
        if(!leading){
            float x = mController.readBroadcastFloat(LEADER_POS_X);
            float y = mController.readBroadcastFloat(LEADER_POS_Y);
            if(!mController.canSenseRobot(leaderID)&&mController.getLocation().distanceTo(new MapLocation(x,y))<RobotType.SOLDIER.sensorRadius) {
                mController.broadcastFloat(SQUAD_LEADER_ID,mController.getID());
                leading=true;
            }
            tryMove = new TryMoveDirection(mController,mController.getLocation().directionTo(new MapLocation(x,y).add((float)(Math.random()*2*Math.PI),1)),90,2);
        }
        //Lead
        else {
            mController.setIndicatorDot(mController.getLocation(),0,0,0);
            mController.broadcastInt(SQUAD_LEADER_ID,mController.getID());
            mController.broadcastFloat(LEADER_POS_X,mController.getLocation().x);
            mController.broadcastFloat(LEADER_POS_Y,mController.getLocation().y);

            if(enemies.length>0){
                tryMove = new TryMoveDirection(mController,mController.getLocation().directionTo(enemies[0].getLocation()),45,2);
            }
            else if(friends.length>2){
                tryMove = new TryMoveDirection(mController,mController.getLocation().directionTo(archons[0]),20,3);
            }
            else if(trees.length>0) {
                tryMove = new TryMoveDirection(mController,trees[0].getLocation().directionTo(mController.getLocation()),45,2);
            }
            else {
                tryMove = new TryMoveDirection(mController,new Direction((float)(Math.random()*2*Math.PI)),45,2);
            }
        }
        if(tryMove.canStep())
            tryMove.step();
    }

    @Override
    public boolean canStep() {
        return !mController.hasMoved();
    }
}