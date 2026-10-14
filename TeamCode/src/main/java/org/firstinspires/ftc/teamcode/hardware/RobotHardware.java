package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.RobotConfig;

public class RobotHardware {

    public final DcMotorEx frontLeftMotor;
    public final DcMotorEx frontRightMotor;
    public final DcMotorEx rearLeftMotor;
    public final DcMotorEx rearRightMotor;

    public final DcMotorEx turretShooterMotor;

    public final DcMotorEx FeederMotorTop;

    public final DcMotorEx FeederMotorBottom;

    public RobotHardware(
            HardwareMap hardwareMap,
            RobotConfig config
    ) {

        frontLeftMotor = hardwareMap.get(
                DcMotorEx.class,
                config.getString(
                        "Robot.FRONT_LEFT_MOTOR_NAME",
                        "flm"
                )
        );

        frontRightMotor = hardwareMap.get(
                DcMotorEx.class,
                config.getString(
                        "Robot.FRONT_RIGHT_MOTOR_NAME",
                        "frm"
                )
        );

        rearLeftMotor = hardwareMap.get(
                DcMotorEx.class,
                config.getString(
                        "Robot.REAR_LEFT_MOTOR_NAME",
                        "rlm"
                )
        );

        rearRightMotor = hardwareMap.get(
                DcMotorEx.class,
                config.getString(
                        "Robot.REAR_RIGHT_MOTOR_NAME",
                        "rrm"
                )
        );


        turretShooterMotor = hardwareMap.get(
                DcMotorEx.class,
                config.getString(
                        "Robot.TURRET_SHOOTER_MOTOR_NAME",
                        "tsm"
                )
        );

        FeederMotorTop = hardwareMap.get(DcMotorEx.class,config.getString("robot.FEEDER_MOTOR_TOP_NAME","fmt"));

        FeederMotorBottom = hardwareMap.get(DcMotorEx.class,config.getString("robot.FEEDER_MOTOR_BOTTOM_NAME","fmb"));

    }
}