package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class MecanumDrive {

    private final RobotHardware hardware;
    private final DriveInputProcessor inputProcessor;

    public MecanumDrive(
            RobotHardware hardware,
            DriveInputProcessor inputProcessor
    ) {

        this.hardware = hardware;
        this.inputProcessor = inputProcessor;
    }

    public void drive(Gamepad gamepad) {

        double x = inputProcessor.computeX(
                gamepad.left_stick_x,
                gamepad.left_stick_y,
                1.0
        );

        double y = inputProcessor.computeY(
                gamepad.left_stick_x,
                gamepad.left_stick_y,
                1.0
        );

        // Rotation only needs right-stick X.
        double turn = inputProcessor.computeX(
                gamepad.right_stick_x,
                0.0,
                1.0
        );

        // Reduce turning speed.
        turn *= 0.5;

        double frontLeftPower =
                (-y + x) + turn;

        double frontRightPower =
                (y + x) + turn;

        double rearLeftPower =
                (-y - x) + turn;

        double rearRightPower =
                (y - x) + turn;

        double max = Math.max(
                1.0,
                Math.abs(frontLeftPower)
        );

        max = Math.max(
                max,
                Math.abs(frontRightPower)
        );

        max = Math.max(
                max,
                Math.abs(rearLeftPower)
        );

        max = Math.max(
                max,
                Math.abs(rearRightPower)
        );

        frontLeftPower /= max;
        frontRightPower /= max;
        rearLeftPower /= max;
        rearRightPower /= max;

        hardware.frontLeftMotor.setPower(frontLeftPower);
        hardware.frontRightMotor.setPower(frontRightPower);
        hardware.rearLeftMotor.setPower(rearLeftPower);
        hardware.rearRightMotor.setPower(rearRightPower);
    }
}