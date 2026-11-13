package org.firstinspires.ftc.teamcode.subsystems.shooter;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class color_sense {

    private final NormalizedColorSensor color_sensor;
    private double hue;

    public color_sense(RobotHardware hardware) {
        color_sensor = hardware.color_sensor;
    }


    public boolean cs(boolean light_on, float gain) {

        color_sensor.setGain(gain);

        if (color_sensor instanceof SwitchableLight) {
            SwitchableLight light = (SwitchableLight) color_sensor;
            light.enableLight(light_on);
        }

        NormalizedRGBA colors =
                color_sensor.getNormalizedColors();

        float[] hsvValues = new float[3];

        Color.colorToHSV(colors.toColor(), hsvValues);

        float hue = hsvValues[0];
        float saturation = hsvValues[1];
        float value = hsvValues[2];

        return hue >= 51 && hue <= 65
                && saturation >= 0.4f
                && value >= 0.1f;
    }


}


