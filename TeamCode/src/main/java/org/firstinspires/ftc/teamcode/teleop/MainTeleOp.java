package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.RobotConfig;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveInputProcessor;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.ball_color_detector.BallColorDetector;

@TeleOp(
        name = "TeleOp",
        group = "Iterative OpMode"
)
public class MainTeleOp extends OpMode {

    private RobotHardware robot;
    private MecanumDrive drive;

    private Shooter shooter;
    private BallColorDetector ballColorDetector;

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

        shooter = new Shooter(robot);
        ballColorDetector = new BallColorDetector(robot, telemetry);

        telemetry.addData(
                "Status",
                "Initialized"
        );

        telemetry.update();
    }

    @Override
    public void loop() {

        drive.drive(gamepad1);

        if (gamepad1.a) {
            shooter.setPower(1.0);
        } else {
            shooter.stop();
        }
        ballColorDetector.getDetectedColor(robot, telemetry);

        telemetry.addLine("Drive running this shit workkkkkkkssssss");

        telemetry.update();
    }
}