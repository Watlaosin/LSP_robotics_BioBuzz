package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.RobotConfig;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveInputProcessor;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
@TeleOp(
        name = "TeleOp",
        group = "Iterative OpMode"
)
public class MainTeleOp extends OpMode {

    private RobotHardware robot;
    private MecanumDrive drive;

    @Override
    public void init() {

        RobotConfig config =
                new RobotConfig(
                        hardwareMap.appContext
                );

        robot =
                new RobotHardware(
                        hardwareMap,
                        config
                );

        DriveInputProcessor inputProcessor =
                new DriveInputProcessor(config);

        drive =
                new MecanumDrive(
                        robot,
                        inputProcessor
                );

        telemetry.addData(
                "Status",
                "Initialized"
        );

        telemetry.update();
    }

    @Override
    public void loop() {

        drive.drive(gamepad1);

    }
}