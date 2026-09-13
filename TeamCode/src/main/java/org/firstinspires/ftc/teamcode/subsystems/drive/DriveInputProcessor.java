package org.firstinspires.ftc.teamcode.subsystems.drive;

import org.firstinspires.ftc.teamcode.config.RobotConfig;

public class DriveInputProcessor {

    private static double saturation;
    private static double sensitivity;
    private static double deadZone = 0;

    private static boolean invertX = false;
    private static boolean invertY = false;

    public DriveInputProcessor(RobotConfig config) {

        saturation = config.getDouble(
                "Robot.Joystick_Saturation",
                1.0
        );

        sensitivity = config.getDouble(
                "Robot.Joystick_Sensitivity",
                0.5
        );

        deadZone = config.getDouble(
                "Robot.Joystick_deadZone",
                0.0
        );

        invertX = config.getBoolean(
                "Robot.Joystick_invertX",
                true
        );

        invertY = config.getBoolean(
                "Robot.Joystick_invertY",
                false
        );
    }

    public static double computeX(float X, float Y, float range) {
        double r = coerceValue(Math.sqrt((X * X) + (Y * Y)));
        double a = Math.atan2(Y, X);
        double value = computeModifiers(r, deadZone, saturation, sensitivity, range);

        double x = value * Math.cos(a);
        if (invertX) x = -x;
        return x;
    }

    public static double computeY(float X, float Y, float range) {
        double r = coerceValue(Math.sqrt((X * X) + (Y * Y)));
        double a = Math.atan2(Y, X);
        double value = computeModifiers(r, deadZone, saturation, sensitivity, range);

        double y = value * Math.sin(a);
        if (invertY) y = -y;
        return y;
    }

    private static double computeModifiers(double value, double deadZone, double saturation, double sensitivity, double range) {
        if (deadZone > 0.0 || saturation < 1.0) {
            double edgeSpace = (1 - saturation) + deadZone;
            if (edgeSpace < 1.0) {
                double multiplier = 1.0 / (1.0 - edgeSpace);
                value = (value - deadZone) * multiplier;
                value = coerceValue(value);
            } else {
                value = Math.round(value);
            }
        }

        if (sensitivity != 0.0) {
            value = value + ((value - Math.sin(value * (Math.PI / 2))) * (sensitivity * 2));
            value = coerceValue(value);
        }

        if (range < 1.0) {
            value = value * range;
        }

        return coerceValue(value);
    }

    private static double coerceValue(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}