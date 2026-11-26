package org.firstinspires.ftc.teamcode.subsystems.ball_color_detector;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;


public class BallColorDetector {

    public enum DetectedColor {
        RED,
        BLUE,
        YELLOW,
        UNKNOWN
    }

    private final NormalizedColorSensor colorSensor;

    public BallColorDetector(RobotHardware hardware, Telemetry telemetry) {
        colorSensor = hardware.colorSensor;
    }
    public DetectedColor getDetectedColor(RobotHardware hardware, Telemetry telemetry) {
        NormalizedRGBA colors = hardware.colorSensor.getNormalizedColors();
        hardware.colorSensor.setGain(3);

        float normalizedRed, normalizedGreen, normalizedBlue;

        normalizedRed = colors.red / colors.alpha;
        normalizedGreen = colors.green / colors.alpha;
        normalizedBlue = colors.blue / colors.alpha;

        telemetry.addData("normRed: ", normalizedRed);
        telemetry.addData("normGreen: ", normalizedGreen);
        telemetry.addData("normBlue: ", normalizedBlue);

        return DetectedColor.UNKNOWN;
    }
}
