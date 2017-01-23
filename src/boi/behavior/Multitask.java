package boi.behavior;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.Consumer;

public class Multitask extends Behavior {

    private final TreeSet<Task> mTasks;

    public Multitask(RobotController controller) {
        super(controller);
        mTasks = new TreeSet<>(Comparator.comparingInt(Task::getPriority));
    }

    public <B extends Behavior> Multitask addTask(B b, int priority, Consumer<B> listener) {
        mTasks.add(new Task<>(b, priority, listener));
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void step() throws GameActionException {
        Task t = mTasks.first();
        while (t != null) {
            Behavior b = t.mBehavior;
            if (b.isDone()) {
                mTasks.remove(t);
                if (t.mListener != null)
                    // b must be the correct type, since it was taken
                    // from the Task
                    t.mListener.accept(b);
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

    private static class Task<B extends Behavior> {

        private final B mBehavior;
        private final int mPriority;
        private final Consumer<B> mListener;

        private Task(B b, int priority, Consumer<B> listener) {
            mBehavior = b;
            mPriority = priority;
            mListener = listener;
        }

        private int getPriority() {
            return mPriority;
        }
    }
}
