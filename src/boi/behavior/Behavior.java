package boi.behavior;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

/**
 * A class for implementing modular behavioral code for a given
 * {@link RobotController} to perform.
 * <br><br>
 * Subclasses break down high-level behaviors into atomic steps,
 * which can be run one at a time and polled for completion.
 *
 * @author Alec Bernardi
 */
public abstract class Behavior {

    protected final RobotController mController;

    protected Behavior(RobotController controller) {
        mController = controller;
    }

    /**
     * Performs the next atomic operation in performing the behavior.
     * <br><br>
     * Callers of this method should first call {@link #canStep()} in
     * order to allow subclasses to avoid checking themselves. This
     * reduces redundancy in checking, which reduces bytecode
     * expenditure.
     *
     * @throws GameActionException when
     * @see #canStep()
     */
    public abstract void step() throws GameActionException;

    /**
     * The default implementation assumes that the next step may be
     * taken as long as the behavior has not completed.
     * <br><br>
     * Subclasses should be sure to encompass this condition as well
     * in their own implementations of this method if it needs to be
     * overridden.
     *
     * @return True if a call to {@link #step()} would be successful.
     * @see #isDone()
     * @see #step()
     */
    public boolean canStep() {
        return !isDone();
    }

    /**
     * This method polls if the behavior is complete and requires no
     * further calls to {@link #step()}. Subclasses should assume
     * that {@link #step()} will not be called if this method returns
     * <tt>true</tt>. Further, if this method returns <tt>true</tt>,
     * {@link #canStep()} should return <tt>false</tt>.
     *
     * @return True if {@link #step()} should not be called again.
     * @see #step()
     */
    public boolean isDone() {
        return false;
    }
}
