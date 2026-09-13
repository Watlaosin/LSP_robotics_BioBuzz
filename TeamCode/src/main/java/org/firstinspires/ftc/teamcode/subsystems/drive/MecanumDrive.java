package org.firstinspires.ftc.teamcode.subsystems.drive;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class MecanumDrive {

    private final RobotHardware hardware;

    public MecanumDrive(RobotHardware hardware) {
        this.hardware = hardware;
    }


    public void drive(Gamepad gamepad) {
        double computedX_l = DriveInputProcessor.computeX(gamepad.left_stick_x, gamepad.left_stick_y, 1);
        double computedY_l = DriveInputProcessor.computeY(gamepad.left_stick_x, gamepad.left_stick_y, 1);
        double computedX_r = DriveInputProcessor.computeX(gamepad.right_stick_x, gamepad.right_stick_y, 1);

        // Calculate motor powers
        double frontLeftPower = (-computedY_l + computedX_l) + (0.5 * computedX_r);
        double frontRightPower = (computedY_l + computedX_l) + (0.5 * computedX_r);
        double backLeftPower = (-computedY_l - computedX_l) + (0.5 * computedX_r);
        double backRightPower = (computedY_l - computedX_l) + (0.5 * computedX_r);

        // Normalize so values stay in [-1, 1]
        double max = Math.max(1.0, Math.abs(frontLeftPower));
        max = Math.max(max, Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        frontLeftPower /= max;
        frontRightPower /= max;
        backLeftPower /= max;
        backRightPower /= max;

        // Apply to hardware
        hardware.frontLeftMotor.setPower(frontLeftPower);
        hardware.frontRightMotor.setPower(frontRightPower);
        hardware.rearLeftMotor.setPower(backLeftPower);
        hardware.rearRightMotor.setPower(backRightPower);
    }

}
