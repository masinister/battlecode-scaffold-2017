package boi.behavior;

import boi.units.Unit;

/**
 * Created by douwz on 1/16/2017.
 */
public class Archon1 extends Behavior {

    float dir = (float) Math.PI / 4F;

    public Archon1(Unit u) {
        super(u);
    }
    @Override
    public boolean next() throws Exception {

        return false;
    }

    @Override
    public boolean isDone() throws Exception {
        return false;
    }

    @Override
    public void destroy() throws Exception {

    }
}
