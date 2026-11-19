package org.firstinspires.ftc.teamcode.subsystems.helper;

import java.util.ArrayDeque;
import java.util.Queue;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.helper.seqactions.*;


/**
 * For queueing and executing actions
 *
 * <p>Add actions in start() and call step() repeatedly in loop()</p>
 * <p>Able to be run in a thread</p>
 * <p>Actions can be made by implementing the SequenceAction interface and making an overload for add</p>
 */
public class Sequencer implements Runnable {
    ElapsedTime timer = new ElapsedTime();
    Queue<SequenceAction> actionQueue = new ArrayDeque<>();
    SequenceAction currentAction = null;
    public int actionCounter = 0;

    /**
     * Adds a Delay Action to Queue
     * <p>A Non-blocking wait within the Sequencer</p>
     * @param durationMs How long to wait
     */
    public void add(long durationMs) {
        actionQueue.add(new Delay(durationMs));
    }

    /**
     * General fallback if action has no overload
     * @param action A SeqAction class
     * @see #add(long)
     */
    public void add(SequenceAction action) {
        actionQueue.add(action);
    }

    /**
     * Call repeatedly inside loop()
     */
    public void step() {
        if (currentAction == null) { // not executing an action
            currentAction = actionQueue.poll();
            if (currentAction == null) return; // Queue is empty
            if (currentAction instanceof ServoAction && ((ServoAction) currentAction).interpolate) {
                ((ServoAction) currentAction).startPos = ((ServoAction) currentAction).servo.getPosition();
            }

            timer.reset();
        }

        if (currentAction.execute(timer)) {
            currentAction = null;
            actionCounter++;
        }
    }

    @Override
    public void run() {
        while (!this.sequenceFinished()) {
            this.step();
        }
    }

    public boolean sequenceFinished () {
        return currentAction == null && actionQueue.isEmpty();
    }

    public void clearSeq () {
        actionQueue.clear();
        currentAction = null;
    }
}
