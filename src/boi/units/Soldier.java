package boi.units;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import boi.behavior.Behavior;
import boi.behavior.Multitask;
import boi.behavior.Repeat;
import boi.behavior.fight.SimpleShoot;
import boi.behavior.Squad.FormSquad;

public class Soldier extends Unit {

    public Soldier(RobotController rc) {
        super(rc);
    }

    @Override
    public void lifetime() throws GameActionException {
        Behavior squad = new FormSquad(mController);
        Behavior shoot = new SimpleShoot(mController);
        Multitask soldier = new Multitask(mController);
        soldier.addTask(squad,2,null);
        soldier.addTask(shoot,1,null);

        while (!soldier.isDone()){
            while(soldier.canStep())
                soldier.step();

            Clock.yield();
        }
    }
}