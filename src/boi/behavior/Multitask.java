package boi.behavior;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import java.util.Comparator;
import java.util.TreeSet;

public class Multitask extends Behavior {

    private final TreeSet<Task> mTasks;

    public Multitask(RobotController controller) {
        super(controller);
        mTasks = new TreeSet<>(Comparator.comparingInt(Task::getPriority));
    }

    public Multitask addTask(Behavior b, int priority) {
        mTasks.add(new Task(b, priority));
        return this;
    }

    @Override
    public void step() throws GameActionException {
        Task t = mTasks.first();
        while (t != null) {
            final Behavior b = t.mBehavior;
            if (b.isDone()) {
                mTasks.remove(t);
                t = mTasks.higher(t);
            } else if (b.canStep()) {
                b.step();
                break;
            } else t = mTasks.higher(t);
        }
    }

    @Override
    public boolean canStep() {
        for (Task t : mTasks)
            if (t.mBehavior.canStep())
                return true;
        return false;
    }

    @Override
    public boolean isDone() {
        return mTasks.isEmpty();
    }

    private static class Task {

        private final Behavior mBehavior;
        private final int mPriority;

        private Task(Behavior b, int priority) {
            mBehavior = b;
            mPriority = priority;
        }

        private int getPriority() {
            return mPriority;
        }
    }
}
