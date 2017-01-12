package boi.Units;

import battlecode.common.Clock;
import battlecode.common.RobotController;

/**
 * Created by mason on 1/12/2017.
 */

public class Scout {

    public static RobotController RC;

    public static void run(RobotController robotController){
        RC=robotController;
        while(true){
            try{
                run();
                Clock.yield(); //Wait for next turn
            } catch (Exception e){
                System.out.println(RC.getType() + " [ID " + RC.getID() + "]is having a bad time");
                e.printStackTrace();
            }
        }
    }

    private static void run(){

    }

}
