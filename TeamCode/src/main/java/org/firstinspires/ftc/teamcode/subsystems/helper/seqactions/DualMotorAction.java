package org.firstinspires.ftc.teamcode.subsystems.helper.seqactions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.helper.SequenceAction;

public final class DualMotorAction implements SequenceAction {
    public final DcMotor m1, m2;
    public final double p1, p2;
    public final long durationMs;

    public DualMotorAction(DcMotor m1, double p1, DcMotor m2, double p2, long durationMs) {
        this.m1 = m1;
        this.p1 = p1;
        this.m2 = m2;
        this.p2 = p2;
        this.durationMs = durationMs;
    }

    @Override
    public boolean execute(ElapsedTime time) {
        m1.setPower(Math.min(1, Math.max(-1, p1)));
        m2.setPower(Math.min(1, Math.max(-1, p2)));

        if (time.milliseconds() >= durationMs) {
            m1.setPower(0);
            m2.setPower(0);
            return true;
        }
        return false;
    }
}