package org.firstinspires.ftc.teamcode.subsystems.drive;

import org.firstinspires.ftc.teamcode.config.RobotConfig;

public class DriveInputProcessor {

    private final double saturation;
    private final double sensitivity;
    private final double deadZone;

    private final boolean invertX;
    private final boolean invertY;

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

    public double computeX(double x, double y, double range) {

        double r = coerceValue(
                Math.sqrt((x * x) + (y * y))
        );

        double a = Math.atan2(y, x);

        double value = computeModifiers(
                r,
                deadZone,
                saturation,
                sensitivity,
                range
        );

        double result = value * Math.cos(a);

        if (invertX) {
            result = -result;
        }

        return result;
    }

    public double computeY(double x, double y, double range) {

        double r = coerceValue(
                Math.sqrt((x * x) + (y * y))
        );

        double a = Math.atan2(y, x);

        double value = computeModifiers(
                r,
                deadZone,
                saturation,
                sensitivity,
                range
        );

        double result = value * Math.sin(a);

        if (invertY) {
            result = -result;
        }

        return result;
    }

    private double computeModifiers(
            double value,
            double deadZone,
            double saturation,
            double sensitivity,
            double range
    ) {

        if (deadZone > 0.0 || saturation < 1.0) {

            double edgeSpace =
                    (1.0 - saturation) + deadZone;

            if (edgeSpace < 1.0) {

                double multiplier =
                        1.0 / (1.0 - edgeSpace);

                value =
                        (value - deadZone) * multiplier;

                value = coerceValue(value);

            } else {
                value = Math.round(value);
            }
        }

        if (sensitivity != 0.0) {

            value =
                    value
                            + (
                            value
                                    - Math.sin(
                                    value * (Math.PI / 2.0)
                            )
                    )
                            * (sensitivity * 2.0);

            value = coerceValue(value);
        }

        if (range < 1.0) {
            value *= range;
        }

        return coerceValue(value);
    }

    private double coerceValue(double value) {
        return Math.max(
                0.0,
                Math.min(1.0, value)
        );
    }
}