package org.firstinspires.ftc.teamcode.subsystems.helper.seqactions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.helper.SequenceAction;

/**
 * Single motor movement
 */
public final class MotorAction implements SequenceAction {
    public final DcMotor motor;
    public final double power;
    public final long durationMs;

    public MotorAction(DcMotor motor, double power, long durationMs) {
        this.motor = motor;
        this.power = power;
        this.durationMs = durationMs;
    }


    @Override
    public boolean execute(ElapsedTime time) {
        motor.setPower(Math.min(1, Math.max(-1, power)));
        if (time.milliseconds() >= durationMs) {
            motor.setPower(0);
            return true;
        }
        return false;
    }
}
