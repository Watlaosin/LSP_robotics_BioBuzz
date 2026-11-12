package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class color_sensor {

    private final NormalizedColorSensor color_sensor;

    public color_sensor(RobotHardware hardware) {
        color_sensor = hardware.color_sensor;
    }

    public boolean cs(boolean light_on, float gain){
        boolean pollen = false;
        color_sensor.setGain(gain);
        if (light_on) {
            if (color_sensor instanceof SwitchableLight){
                SwitchableLight light = (SwitchableLight)color_sensor;
                light.enableLight(!light.isLightOn());
            }
        }

        return pollen;

    }

}
