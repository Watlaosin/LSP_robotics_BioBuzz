package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;

@TeleOp(name = "teleop", group = "Iterative OpMode")
public class MainTeleOp extends OpMode {

    private RobotHardware robot;
    private MecanumDrive md;

    @Override
    public void init() {

        robot = new RobotHardware(hardwareMap);
        md = new MecanumDrive(robot);

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {

        telemetry.addLine("drive check");

        md.drive(gamepad1);

        telemetry.update();
    }
}