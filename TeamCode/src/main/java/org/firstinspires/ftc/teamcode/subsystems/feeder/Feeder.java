package org.firstinspires.ftc.teamcode.subsystems.feeder;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Feeder {

    private static final double TICKS_PER_REV = 537.7;

    private final DcMotorEx FeederMotorTop;

    private  final DcMotorEx FeederMotorBottom;

    public Feeder(RobotHardware hardware) {

        FeederMotorTop = hardware.FeederMotorTop;
        FeederMotorBottom = hardware.FeederMotorBottom;

        FeederMotorTop.setDirection(
                DcMotorSimple.Direction.FORWARD
        );

        FeederMotorBottom.setDirection(
                DcMotorSimple.Direction.REVERSE
        );

        FeederMotorTop.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE
        );

        FeederMotorBottom.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE
        );

        FeederMotorTop.setMode(
                DcMotor.RunMode.RUN_USING_ENCODER
        );

        FeederMotorBottom.setMode(
                DcMotor.RunMode.RUN_USING_ENCODER
        );
    }

    public void setRPM_Top(double rpm) {
        double ticksPerSecond = rpm*TICKS_PER_REV/60;
        FeederMotorTop.setVelocity(ticksPerSecond);
    }

    public void setRPM_Bottom(double rpm) {
        double ticksPerSecond = rpm*TICKS_PER_REV/60;
        FeederMotorBottom.setVelocity(ticksPerSecond);
    }

    public void setRPM_Both(double rpm){
        double ticksPerSecond = rpm*TICKS_PER_REV/60;
        FeederMotorTop.setVelocity(ticksPerSecond);
        FeederMotorBottom.setVelocity(ticksPerSecond);
    }

    public double getRPM() {
        double tickPerSecond = FeederMotorTop.getVelocity();
        return Math.abs(tickPerSecond*60/TICKS_PER_REV);
    }

    public void stop() {
        FeederMotorTop.setPower(0.0);
        FeederMotorBottom.setPower(0.0);
    }
}