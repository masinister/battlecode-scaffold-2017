package boi.behavior.move;

import battlecode.common.*;
import boi.behavior.Behavior;
import boi.behavior.Repeatable;

import java.util.function.Function;

/**
 * A <tt>Behavior</tt> that causes its actor to move in a manner
 * determined by the provided <tt>Function</tt>, which generates an
 * intended <tt>Direction</tt>. The actual step of this behavior is
 * an attempt to move roughly in this direction, if the actor can
 * move during the current turn and the behavior hasn't already
 * tried to move previously in the turn.
 */
public class Move extends Behavior implements Repeatable {

    /**
     * A method reference that causes a <tt>Move</tt> behavior to
     * generate a random direction and attempt to move in it. Note
     * that this will cause the actor to move ONLY once before the
     * behavior considers itself complete. For continuous random
     * movement, wrap the <tt>Move</tt> in a
     * {@link boi.behavior.Repeat} behavior.
     *
     * @param move the <tt>Move</tt> that is requesting a direction
     *
     * @return A random direction if the <tt>Move</tt> has yet to
     * successfully move, otherwise <tt>null</tt>
     * @see boi.behavior.Repeat
     * @see Move#mDirectionFunction
     */
    public static Direction randomly(Move move) {
        if (move.getLastMove() == null) // Only generate a new direction if we haven't moved yet
            return new Direction((float) (Math.random() * 2 * Math.PI));
        else return null; // Otherwise we are dome moving
    }

    /**
     * Creates a <tt>Function</tt> lambda that causes a <tt>Move</tt>
     * to travel in the direction of the given {@link MapLocation}.
     * The actor will try as direct of a path as possible, and so it
     * is possible that it will become stuck on obstacles.
     *
     * @param target the location to travel towards
     *
     * @return A <tt>Function</tt> that can be passed into a
     * <tt>Move</tt> behavior that will cause the actor to
     * move towards the target.
     * @see Move#mDirectionFunction
     */
    public static Function<Move, Direction> to(final MapLocation target) {
        // Todo: Actually go around obstacles
        return move -> move.mController.getLocation().directionTo(target);
    }

    /**
     * The offset of consecutive movement trials, in degrees.
     */
    private final float mOffset;
    /**
     * The number of movement trials to attempt per side of the
     * intended direction.
     */
    private final int mChecks;
    /**
     * A <tt>Function</tt> that produces the next intended movement
     * <tt>Direction</tt> for the behavior.
     * <br><br>
     * The <tt>Function</tt> accepts an instance of <tt>Move</tt>
     * (namely the instance that was given the <tt>Function</tt> in
     * the constructor) so that it may access its previous intended
     * <tt>Direction</tt> and its last actual <tt>Direction</tt>.
     * <br><br>
     * The <tt>Function</tt> is expected to return the next
     * <tt>Direction</tt> that the actor should try to move in.
     *
     * @see Move#getLastTry()
     * @see Move#getLastMove()
     * @see #Move(RobotController, float, int, Function)
     */
    private final Function<Move, Direction> mDirectionFunction;

    /**
     * The <tt>Direction</tt> we tried to move in the last step.
     *
     * @see Move#getLastTry()
     */
    private Direction mTry;
    /**
     * The actual <tt>Direction</tt> we moved in last step.
     *
     * @see Move#getLastMove()
     */
    private Direction mLast;
    /**
     * A flag that marks the behavior as complete once
     * {@link #mDirectionFunction} returns <tt>null</tt>.
     */
    private boolean mDone;
    /**
     * A counter that prevents the behavior from trying to move more
     * than once per turn, since entities only change positions in
     * between turns. Therefore it is redundant to try to move in the
     * same turn if we tried already.
     */
    private int mRound;

    /**
     * Constructs a new <tt>Move</tt> behavior that will attempt to
     * have the given actor move in the <tt>Direction</tt> provided
     * by <tt>dirFunction</tt>. The behavior will try to move in this
     * direction, and if the path is blocked, will attempt to move
     * in <tt>offset</tt> degree intervals on each side of the
     * intended <tt>Direction</tt>.
     *
     * @param controller  the actor's <tt>RobotController</tt>
     * @param offset      the movement trial offset, in degrees
     * @param checks      the number of trials per side
     * @param dirFunction the <tt>Function</tt> the provides
     *                    the intended direction to move.
     */
    public Move(RobotController controller, float offset, int checks, Function<Move, Direction> dirFunction) {
        super(controller);
        mDirectionFunction = dirFunction;
        mOffset = offset;
        mChecks = checks;

        mTry = mLast = null;
        mDone = false;
        mRound = -1;
    }

    @Override
    public void step() throws GameActionException {
        // First, try intended direction
        mTry = mDirectionFunction.apply(this);
        // Set the current round
        mRound = mController.getRoundNum();
        if (mTry != null) {
            if (!mController.canMove(mTry)) {
                // Now try a bunch of similar angles
                int currentCheck = 1;
                mLast = null; // In case we can't find one
                while (currentCheck <= mChecks) {
                    // Try the offset of the left side
                    Direction tried = mTry.rotateLeftDegrees(mOffset * currentCheck);
                    if (mController.canMove(tried)) {
                        mController.move(mLast = tried);
                        break;
                    }
                    // Try the offset on the right side
                    tried = mTry.rotateRightDegrees(mOffset * currentCheck);
                    if (mController.canMove(tried)) {
                        mController.move(mLast = tried);
                        break;
                    }
                    // No move performed, try slightly further
                    ++currentCheck;
                }
            } else mController.move(mLast = mTry); // We are free to move where we wanted
        } else mDone = true; // Function returned null, so we are done
    }

    @Override
    public boolean canStep() {
        return !(mDone || mController.hasMoved()) && mController.getRoundNum() > mRound;
    }

    @Override
    public boolean isDone() {
        return mDone;
    }

    @Override
    public void reset() {
        mDone = false;
        mLast = null;
    }

    /**
     * @return The last <tt>Direction</tt> that the actor moved in
     * due to this behavior, or <tt>null</tt> if the actor was unable
     * to move.
     */
    public Direction getLastMove() {
        return mLast;
    }

    /**
     * @return The last intended <tt>Direction</tt> for the actor to
     * travel in.
     */
    public Direction getLastTry() {
        return mTry;
    }
}
