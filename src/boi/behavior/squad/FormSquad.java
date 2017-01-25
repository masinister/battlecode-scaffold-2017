package boi.behavior.squad;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeat;
import boi.behavior.Repeatable;
import boi.behavior.move.Move;

import java.sql.SQLClientInfoException;

/**
 * Created by mason on 1/24/2017.
 */
public class FormSquad extends Behavior{

    private int SQUAD_LEADER_ID = 100;
    private int LEADER_POS_X = 101;
    private int LEADER_POS_Y = 102;
    private boolean leading;
    private Move tryMove = new Move(mController,45,2, Move::randomly);
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
            tryMove = new Move(mController,30,3, Move.to(new MapLocation(x,y)));
        }
        //Lead
        else {
            mController.setIndicatorDot(mController.getLocation(),0,0,0);
            mController.broadcastInt(SQUAD_LEADER_ID,mController.getID());
            mController.broadcastFloat(LEADER_POS_X,mController.getLocation().x);
            mController.broadcastFloat(LEADER_POS_Y,mController.getLocation().y);

            if(enemies.length>0){
                tryMove = new Move(mController,30,3,Move.to(enemies[0].getLocation()));
            }
            else if(friends.length>2){
                tryMove = new Move(mController,30,3, Move.to(archons[0]));
            }
            else if(trees.length>0) {
                tryMove = new Move(mController,30,3, Move.to(trees[0].getLocation()));
            }
            else {
                tryMove = new Move(mController,30,3,Move::randomly);
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