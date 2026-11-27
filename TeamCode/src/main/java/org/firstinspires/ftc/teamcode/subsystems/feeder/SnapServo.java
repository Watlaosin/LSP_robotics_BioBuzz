package org.firstinspires.ftc.teamcode.subsystems.feeder;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class SnapServo {

    // From the middle put negative values on the left side, positive values on the right side
    private static final double LEFT_DEG = 83.0;
    private static final double RIGHT_DEG = -18.0;

    // Calibrate these to the corresponding FTC servo positions
    private static final double MIN_POS = 0.0;
    private static final double MAX_POS = 1.0;

    private ElapsedTime snapTimer = new ElapsedTime();
    private boolean isSnapExtended = false;
    private final double SNAP_DELAY_SECONDS = 0.4;
    private boolean isSnapLooping = false;
    private boolean lastLeftBumper = false;
    private final Servo snapServo;

    public SnapServo(RobotHardware hardware) {
        snapServo = hardware.SnapServo;
    }

    public void setPosition(double degree) {
        // Prevent requesting an angle outside the limits
        degree = Range.clip(degree, LEFT_DEG, RIGHT_DEG);

        // Convert degrees into a fraction from 0 to 1
        double fraction = (degree - LEFT_DEG) / (RIGHT_DEG - LEFT_DEG);

        // Map that fraction to the calibrated servo range
        double position = MIN_POS + fraction * (MAX_POS - MIN_POS);

        snapServo.setPosition(position);
    }

    public double getposition() {
        return snapServo.getPosition();
    }

    public void setRawPosition(double position) {
        snapServo.setPosition(Range.clip(position, 0.0, 1.0));
    }
    public void loopSnapServo() {
        if (snapTimer.seconds() >= SNAP_DELAY_SECONDS) {
            if (isSnapExtended) {
                setRawPosition(0);
                isSnapExtended = false;
            } else {
                setRawPosition(1);
                isSnapExtended = true;
            }
            snapTimer.reset();
        }
    }

}

