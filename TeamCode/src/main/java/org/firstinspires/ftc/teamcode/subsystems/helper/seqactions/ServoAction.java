package org.firstinspires.ftc.teamcode.subsystems.helper.seqactions;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.helper.SequenceAction;

public class ServoAction implements SequenceAction {
    public final Servo servo;
    public final double targetPos;
    public final long durationMs;
    public double startPos;
    public final boolean interpolate;

    public ServoAction(Servo servo, double targetPos, long durationMs) {
        this.servo = servo;
        this.targetPos = targetPos;
        this.durationMs = durationMs;
        this.interpolate = false;
    }

    public ServoAction(Servo servo, double targetPos, long durationMs, boolean interpolate) {
        this.servo = servo;
        this.targetPos = targetPos;
        this.durationMs = durationMs;
        this.interpolate = interpolate;
    }

    @Override
    public boolean execute(ElapsedTime time) {
        if (interpolate && durationMs > 0) {
            // interpolated movement
            double t = Math.min(1.0, time.milliseconds() / durationMs);
            double interpolatedPos = startPos + (targetPos - startPos) * t;
            //ServoUtil.safeSetPosition(servo, interpolatedPos);
            return t >= 1.0;
        } else {
            //ServoUtil.safeSetPosition(servo, targetPos);
            return time.milliseconds() >= durationMs;
        }
    }
}
