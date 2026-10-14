package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Shooter {

    private static final double TICKS_PER_REV = 28.0;

    private final DcMotorEx shooterMotor;

    public Shooter(RobotHardware hardware) {
        shooterMotor = hardware.turretShooterMotor;

        shooterMotor.setDirection(
                DcMotorSimple.Direction.FORWARD
        );

        shooterMotor.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.FLOAT

        );

        shooterMotor.setMode(
                DcMotor.RunMode.RUN_USING_ENCODER
        );
    }

    public void setRPM(double rpm) {
        double ticksPerSecond = rpm*TICKS_PER_REV/60;
        shooterMotor.setVelocity(ticksPerSecond);
    }

    public double getRPM() {
        double tickPerSecond = shooterMotor.getVelocity();
        return Math.abs(tickPerSecond*60/TICKS_PER_REV);
    }

    public void stop() {
        shooterMotor.setPower(0.0);
    }
}