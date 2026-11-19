package org.firstinspires.ftc.teamcode.subsystems.helper.seqactions;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.helper.SequenceAction;

/**
 * Waits the specified milliseconds
 */
public final class Delay implements SequenceAction {
    public final long durationMs;

    public Delay(long durationMs) {
        this.durationMs = durationMs;
    }

    @Override
    public boolean execute(ElapsedTime time) {
        return time.milliseconds() >= durationMs;
    }
}