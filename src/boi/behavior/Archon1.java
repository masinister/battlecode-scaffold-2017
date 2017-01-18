package boi.behavior;

import battlecode.common.Direction;
import boi.units.Unit;

/**
 * Created by mason on 1/17/2017.
 */
public class Archon1 extends Behavior {

    public Archon1(Unit actor) {
        super(actor);
    }

    @Override
    public boolean next() throws Exception {
        Behavior randomMove = new TryMoveDirection(unit, Direction.getWest(),90,2);
        return randomMove.next();
    }

    @Override
    public boolean isDone() throws Exception {
        return false;
    }

    @Override
    public void destroy() throws Exception {

    }
}
