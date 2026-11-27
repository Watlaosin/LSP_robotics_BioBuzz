package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.config.RobotConfig;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveInputProcessor;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.feeder.Feeder;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Angle_adjuster;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.feeder.SnapServo;


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

    private SnapServo snapServo;

    private double currentPos = 0;
    private boolean isSnapLooping = false;
    private boolean lastLeftBumper = false;
    private ElapsedTime waitTimer = new ElapsedTime();

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
        snapServo = new SnapServo(robot);

        telemetry.addData(
                "Status",
                "Initialized"
        );

        telemetry.update();
    }

    @Override
    public void loop() {

        drive.drive(gamepad1);

        if (gamepad1.b){
            currentPos += 0.1;
        }
        if (gamepad1.y){
            angle_adjuster.setRawPosition(currentPos);
        }
        if (gamepad1.x){
            currentPos -= 0.1;
        }
        //press lb to start the loop, press again to stop okie?
        if (gamepad1.left_bumper && !lastLeftBumper) {
            isSnapLooping = !isSnapLooping;

            if (isSnapLooping) {
                waitTimer.reset();
            } else {
                snapServo.setRawPosition(0);
            }
        }
        lastLeftBumper = gamepad1.left_bumper;

        if (isSnapLooping) {
            snapServo.loopSnapServo();
        }

        // use a button for manual, plz don't use it when automatic mode is activated na
        if (gamepad1.a || (isSnapLooping && waitTimer.seconds() >= 0.4)) {
            intake.intake();
            feeder.setRPM_Both(300);
        } else {
            intake.stop();
            feeder.stop();
        }



        telemetry.addData("Shooter RPM","%.0f",shooter.getRPM());

        telemetry.addData("Servo Position : ","%.2f",currentPos);

        telemetry.update();
    }
}