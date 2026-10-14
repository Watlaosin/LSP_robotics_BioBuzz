package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.RobotConfig;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveInputProcessor;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.feeder.Feeder;
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
            shooter.setRPM(4000);
            feeder.setRPM_Both(312);
        }
        else if (gamepad1.b){
            feeder.setRPM_Bottom(312);
            }
        else if (gamepad1.y){
            feeder.setRPM_Top(312);
        }
        else if (gamepad1.x){
            shooter.setRPM(6000);
        }
         else {
            shooter.stop();
            feeder.stop();
        }

        telemetry.addData("Shooter RPM","%.0f",shooter.getRPM()

        );

        telemetry.update();
    }
}