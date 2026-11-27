
package org.firstinspires.ftc.teamcode.subsystems.shooter;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Angle_adjuster {

    // From the middle put negative values on the left side, positive values on the right side
    private static final double LEFT_DEG = -10.0;
    private static final double RIGHT_DEG = 170.0;

    // Calibrate these to the corresponding FTC servo positions
    private static final double MIN_POS = 0.0;
    private static final double MAX_POS = 1.0;

    private final Servo angleAdjuster;

    public Angle_adjuster(RobotHardware hardware) {
        angleAdjuster = hardware.AngleAdjuster;
    }

    public void setPosition(double degree) {
        // Prevent requesting an angle outside the limits
        degree = Range.clip(degree, LEFT_DEG, RIGHT_DEG);

        // Convert degrees into a fraction from 0 to 1
        double fraction = (degree - LEFT_DEG) / (RIGHT_DEG - LEFT_DEG);

        // Map that fraction to the calibrated servo range
        double position = MIN_POS + fraction * (MAX_POS - MIN_POS);

        angleAdjuster.setPosition(position);
    }

    public double getposition() {
        return angleAdjuster.getPosition();
    }

    public void setRawPosition(double position) {
        angleAdjuster.setPosition(Range.clip(position, 0.0, 1.0));
    }
}
