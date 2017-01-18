package boi.behavior;

import battlecode.common.Direction;
import boi.units.Unit;

/**
 * Created by mason on 1/17/2017.
 */
public class Scout1 extends Behavior {

    public Scout1(Unit actor) {
        super(actor);
    }

    @Override
    public boolean next() throws Exception {
        Behavior randomMove = new TryMoveDirection(unit, new Direction((float) (Math.random()*2*Math.PI)),90,2);
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
