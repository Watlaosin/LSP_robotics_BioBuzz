package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.RobotConfig;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveInputProcessor;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.feeder.Feeder;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Angle_adjuster;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;

@TeleOp(
        name = "TeleOp",
        group = "Iterative OpMode"
)
public class MainTeleOp extends OpMode {

    private RobotHardware robot;
    private MecanumDrive drive;

    private Shooter shooter;
    private Feeder feeder;

    private Intake intake;
    private Angle_adjuster angle_adjuster;

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
        feeder = new Feeder(robot);
        intake = new Intake(robot);
        angle_adjuster = new Angle_adjuster(robot);

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
            intake.intake();
        }
        else {
            intake.stop();
        }
        if (gamepad1.b){
            intake.setPower(1.0);
            }
        else {
            intake.stop();
        }
        if (gamepad1.y){
            angle_adjuster.setRawPosition(0.2);
        }
        if (gamepad1.x){
            angle_adjuster.setRawPosition(0.7);
        }

        telemetry.addData("Shooter RPM","%.0f",shooter.getRPM());

        telemetry.addData("Servo Position : ","%.2f",angle_adjuster.getposition());

        telemetry.update();
    }
}