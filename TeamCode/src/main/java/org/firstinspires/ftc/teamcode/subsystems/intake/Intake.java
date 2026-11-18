package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Intake {

    private final CRServo IntakeServo;

    public Intake(RobotHardware hardware) {
        IntakeServo = hardware.IntakeServo;
    }

    public void setPower(double power){
        IntakeServo.setPower(Range.clip(power, -1.0, 1.0));
    }

    public void intake(double power){
        setPower(1.0);
    }

    public void stop(double power){
        setPower(0.0);
    }

}
