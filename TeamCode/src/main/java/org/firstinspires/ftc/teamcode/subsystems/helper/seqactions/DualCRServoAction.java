package org.firstinspires.ftc.teamcode.subsystems.helper.seqactions;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.helper.SequenceAction;

public final class DualCRServoAction implements SequenceAction {
    public final CRServo s1, s2;
    public final double p1, p2;
    public final long durationMs;

    public DualCRServoAction(CRServo m1, double p1, CRServo m2, double p2, long durationMs) {
        this.s1 = m1;
        this.p1 = p1;
        this.s2 = m2;
        this.p2 = p2;
        this.durationMs = durationMs;
    }

    @Override
    public boolean execute(ElapsedTime time) {
        s1.setPower(Math.min(1, Math.max(-1, p1)));
        s2.setPower(Math.min(1, Math.max(-1, p2)));

        if (time.milliseconds() >= durationMs) {
            s1.setPower(0);
            s2.setPower(0);
            return true;
        }
        return false;
    }
}
