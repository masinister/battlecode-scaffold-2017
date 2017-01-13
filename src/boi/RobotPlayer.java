package boi;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import boi.units.*;

@SuppressWarnings("unused")
public strictfp class RobotPlayer {

    public static void run(RobotController rc) throws GameActionException {
        Unit thisUnit;
        switch (rc.getType()) {
            case ARCHON:
                thisUnit = new Archon(rc);
                break;
            case GARDENER:
                thisUnit = new Gardener(rc);
                break;
            case SOLDIER:
                thisUnit = new Soldier(rc);
                break;
            case LUMBERJACK:
                thisUnit = new Lumberjack(rc);
                break;
            case SCOUT:
                thisUnit = new Scout(rc);
                break;
            case TANK:
                thisUnit = new Tank(rc);
                break;
            default:
                throw new IllegalArgumentException("Unknown robot type: " + rc.getType());
        }

        try {
            thisUnit.lifetime();
        } catch (Exception e) {
            System.err.println("Error during lifetime");
            e.printStackTrace();
        } finally {
            try {
                thisUnit.destroy();
            } catch (Exception e) {
                System.err.println("Error during destruction");
                e.printStackTrace();
            }
        }
    }
}
