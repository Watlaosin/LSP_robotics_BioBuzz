package org.firstinspires.ftc.teamcode.subsystems.helper.seqactions;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.helper.SequenceAction;

public class Action implements SequenceAction {
    public final long durationMs;
    public final Runnable action;

    public Action(long durationMs, Runnable action) {
        this.durationMs = durationMs;
        this.action = action;
    }

    @Override
    public boolean execute(ElapsedTime time) {
        action.run();
        return time.milliseconds() >= durationMs;
    }
}
