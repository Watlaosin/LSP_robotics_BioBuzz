package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Shooter {

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
                DcMotor.RunMode.RUN_WITHOUT_ENCODER
        );
    }

    //this is set power
    /* ttt
    tttt
    */
    public void setPower(double power) {
        shooterMotor.setPower(
                Range.clip(power, -1.0, 1.0)
        );
    }

    public void stop() {
        shooterMotor.setPower(0.0);
    }
}